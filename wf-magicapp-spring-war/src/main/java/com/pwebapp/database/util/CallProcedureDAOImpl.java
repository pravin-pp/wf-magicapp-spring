package com.pwebapp.database.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class CallProcedureDAOImpl extends StoredProcedure {

    private String sql = "WEBAPP_TEST_FUNCT";

    public CallProcedureDAOImpl() {
        setSql(sql);
    }

    public void setupProcedureCmd(JdbcTemplate jdbc, String[] in, String[] out) {
        setJdbcTemplate(jdbc);
        if ((in != null) && (in.length > 0)) {
            setupInputParameter(in);
        }
        if ((out != null) && (out.length > 0)) {
            setupOutputParameter(out);
        }
        compile();
    }

    private void setupInputParameter(String[] inParam) {
        for (String in : inParam) {
            declareParameter(new SqlParameter(in, oracle.jdbc.OracleTypes.OTHER));
        }
    }

    private void setupOutputParameter(String[] outParam) {
        for (String out : outParam) {
            declareParameter(new SqlOutParameter(out, oracle.jdbc.OracleTypes.CURSOR));
        }
    }

}
