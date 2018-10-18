package com.pwebapp.security.userdetails;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

public class WebAppUserDetailsServiceImpl extends JdbcDaoImpl {

    private static final String usersByUsernameQuery = "SELECT USER_ID, USER_CIF_NO, USER_LOGIN, USER_PASSWORD, "
            + "USER_ROLE_ID,USER_GIVEN_NAME,USER_EMAIL,USER_MOBILE," + "USER_LOCKED_STATUS, USER_DISABLE_STATUS "
            + "FROM WEBAPP_USER_TAB " + "WHERE USER_LOGIN = ?";

    private static final String authoritiesByUsernameQuery = "SELECT ROLE_NAME " + "FROM WEBAPP_ROLE_TAB "
            + "WHERE ROLE_ID IN " + "(SELECT USER_ROLE_ID FROM WEBAPP_USER_TAB WHERE USER_LOGIN = ? )";

    private static final String rolePrefix = "ROLE_";

    public WebAppUserDetailsServiceImpl() {
        super.setUsersByUsernameQuery(usersByUsernameQuery);
        super.setAuthoritiesByUsernameQuery(authoritiesByUsernameQuery);
        super.setEnableAuthorities(true);
        super.setEnableGroups(false);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetails> users = loadUsersByUsername(username);

        if (users.size() == 0) {
            logger.debug("Query returned no results for user '" + username + "'");

            throw new UsernameNotFoundException(
                    messages.getMessage("JdbcDaoImpl.notFound", new Object[] { username }, "Username {0} not found"));
        }

        UserDetails user = users.get(0); // contains no GrantedAuthority[]

        Set<GrantedAuthority> dbAuthsSet = new HashSet<>();

        if (super.getEnableAuthorities()) {
            dbAuthsSet.addAll(loadUserAuthorities(user.getUsername()));
        }

        if (super.getEnableGroups()) {
            dbAuthsSet.addAll(loadGroupAuthorities(user.getUsername()));
        }

        List<GrantedAuthority> dbAuths = new ArrayList<>(dbAuthsSet);

        addCustomAuthorities(user.getUsername(), dbAuths);

        if (dbAuths.size() == 0) {
            logger.debug("User '" + username + "' has no authorities and will be treated as 'not found'");

            throw new UsernameNotFoundException(messages.getMessage("JdbcDaoImpl.noAuthority",
                    new Object[] { username }, "User {0} has no GrantedAuthority"));
        }

        return createUserDetails(username, user, dbAuths);
    }

    @Override
    protected List<UserDetails> loadUsersByUsername(String username) {
        return getJdbcTemplate().query(usersByUsernameQuery, new String[] { username }, new RowMapper<UserDetails>() {
            @Override
            public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                String username = rs.getString("USER_LOGIN");
                String password = rs.getString("USER_PASSWORD");
                boolean enabled = false;
                if (rs.getInt("USER_DISABLE_STATUS") == 0) {
                    enabled = true;
                }
                String givenName = rs.getString("USER_GIVEN_NAME");
                String email = rs.getString("USER_EMAIL");
                String mobileNo = rs.getString("USER_MOBILE");
                return new WebAppUser(username, password, givenName, email, mobileNo, enabled, true, true, true,
                        AuthorityUtils.NO_AUTHORITIES);
            }

        });
    }

    @Override
    protected List<GrantedAuthority> loadUserAuthorities(String username) {
        return getJdbcTemplate().query(authoritiesByUsernameQuery, new String[] { username },
                new RowMapper<GrantedAuthority>() {
                    @Override
                    public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
                        String roleName = rolePrefix + rs.getString("ROLE_NAME");
                        return new SimpleGrantedAuthority(roleName);
                    }
                });
    }

    @Override
    protected UserDetails createUserDetails(String username, UserDetails userFromUserQuery,
            List<GrantedAuthority> combinedAuthorities) {
        WebAppUser user = (WebAppUser) userFromUserQuery;
        return new WebAppUser(user.getUsername(), user.getPassword(), user.getGivenName(), user.getEmail(),
                user.getMobileNo(), user.isEnabled(), true, true, true, combinedAuthorities);
    }

}
