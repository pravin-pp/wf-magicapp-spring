package com.pwebapp.database.procedure;

import java.sql.Date;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class PSysDate extends StoredProcedure {

    private static final String SQLQUERY = "P_GET_SYSDATE";

    public PSysDate(DataSource dataSource) {
        setDataSource(dataSource);
        setSql(SQLQUERY);
        declareParameter(new SqlOutParameter("DB_DATE", Types.DATE));
        compile();
    }

    public Date execute() {
        Map<String, Object> results = execute(new HashMap<String, Object>());
        return (Date) results.get("DB_DATE");
    }

}
