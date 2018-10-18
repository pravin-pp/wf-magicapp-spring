package com.pwebapp.database.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import oracle.jdbc.OracleTypes;

/**
 * Implementation class to call Stored Procedure and Stored Function
 * 
 * @author : Pravin Prabhulkar
 * @version : v1.0
 * @since : 2015-27-01
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class OracleDAOImpl implements OracleDAO, Serializable {

    private static final long serialVersionUID = 2594717927262610103L;

    private String sqlString = null;

    @SuppressWarnings("unused")
    private Object[] sqlValues = null;

    private CallableStatement callableStatement = null;
    private Connection connection = null;

    @Autowired
    private DataSource dataSource;

    private static final Logger logger = LogManager.getLogger(OracleDAOImpl.class);

    @PostConstruct
    void getConnection() {
        try {
            this.connection = dataSource.getConnection();
        } catch (SQLException sqlEx) {
            logger.fatal("Error in Creating Connection", sqlEx);
            this.connection = null;
        }
    }

    @PreDestroy
    void closeConnection() {
        try {
            this.callableStatement.close();
            this.connection.close();
        } catch (SQLException sqlEx) {
            logger.fatal("Error in Closing Connection : ", sqlEx);
            this.connection = null;
        }
    }

    /**
     * Executes a SQL Command that has an OUT parameter String.
     * 
     * @param sqlString
     *            Name of Stored procedure
     * @param sqlValues
     *            Parameter as Array Object, otherwise pass null
     * @Return The output string
     * @exception SQLException
     *                Thrown when any database or jdbc related error occurs
     */
    @Override
    public String executeProcString(String sqlString, Object[] sqlValues) throws SQLException {
        try {
            setupCommand(sqlString, sqlValues);
            String procDef = "{ call " + this.sqlString + " }";
            prepareCallStmt(0, procDef, sqlValues);
            this.callableStatement.registerOutParameter(1, OracleTypes.VARCHAR);
            logger.info("Calling Procedure Begin: " + procDef + " : Values " + Arrays.toString(sqlValues));
            this.callableStatement.execute();
            logger.info("Calling Procedure End: " + procDef);
            return this.callableStatement.getString(1);
        } catch (SQLException sqlEx) {
            logger.fatal("Error in Calling SQL String Procedure", sqlEx);
            return "-1";
        }
    }

    /**
     * Executes a SQL Command that has an OUT parameter INTEGER.
     *
     * @param sqlString
     *            Name of Stored procedure
     * @param sqlValues
     *            Parameter as Array Object, otherwise pass null
     * @Return Integer
     * @exception SQLException
     *                Thrown when any database or jdbc related error occurs
     */
    @Override
    public Integer executeProcInteger(String sqlString, Object[] sqlValues) throws SQLException {

        try {
            setupCommand(sqlString, sqlValues);
            String procDef = "{ call " + this.sqlString + " }";
            prepareCallStmt(0, procDef, sqlValues);
            this.callableStatement.registerOutParameter(1, OracleTypes.INTEGER);
            logger.info("Calling Procedure Begin: " + procDef + " : Values " + Arrays.toString(sqlValues));
            this.callableStatement.execute();
            logger.info("Calling Procedure End: " + procDef);
            return callableStatement.getInt(1);
        } catch (SQLException sqlEx) {
            logger.fatal("Error in Calling SQL Integer Procedure", sqlEx);
            return -1;
        }
    }

    /**
     * Executes SQL that does not return anything ( procedure ) .
     *
     * @param sqlString
     *            Name of Stored procedure
     * @param sqlValues
     *            Parameter as Array Object, otherwise pass null
     * @Return The number of rows affected by the function
     * @exception SQLException
     *                Thrown when any database or jdbc related error occurs
     */
    @Override
    public int executeUpdate(String sqlString, Object[] sqlValues) throws SQLException {
        try {
            int nums = 0;
            setupCommand(sqlString, sqlValues);
            String functionDef = "{ call " + sqlString + " }";
            prepareCallStmt(0, functionDef, sqlValues);
            logger.info("Calling Procedure Begin: " + functionDef + " : Values " + Arrays.toString(sqlValues));
            nums = callableStatement.executeUpdate();
            logger.info("Calling Procedure End: " + functionDef);
            return nums;
        } catch (SQLException sqlEx) {
            logger.fatal("Error in Calling Sql procedure Update : ", sqlEx);
            return -1;
        }
    }

    /**
     * Executes a SQL Function that has an return parameter RersultSet.
     *
     * @param sqlString
     *            Name of Stored Function
     * @param sqlValues
     *            Parameter as Array Object, otherwise pass null
     * @Return The output ResultSet
     * @exception SQLException
     *                Thrown when any database or JDBC related error occurs
     */
    @Override
    public ResultSet executeFunction(String sqlString, Object[] sqlValues) throws SQLException {
        try {
            setupCommand(sqlString, sqlValues);
            String functionDef = " begin ? := " + this.sqlString + "; end;";

            if (sqlValues == null) {
                this.callableStatement = connection.prepareCall(functionDef);
            } else {
                functionPrepareCallStmt(1, functionDef, sqlValues);
            }

            this.callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            this.callableStatement.execute();
            return (ResultSet) this.callableStatement.getObject(1);
            /*
             * ResultSet rs = (ResultSet) this.callableStatement.getObject(1);
             * while(rs.next()){ System.out.println(rs.getString("uname")); }
             * return rs;
             */
        } catch (SQLException sqlEx) {
            logger.fatal("Error in Function Calling", sqlEx);
            return null;
        }
    }

    /**
     * To generate the SQL command for the Function Name / Procedure Name and
     * Parameter values which passed
     * 
     * @param sqlString
     * @param sqlValues
     */
    private void setupCommand(String sqlString, Object[] sqlValues) {
        // Dynamically generate the '?' characters for the function definition
        if (sqlValues == null) {

            this.sqlString = sqlString;
            this.sqlValues = sqlValues;

        } else if (sqlValues.length > 0) {

            char[] functionParms = new char[(sqlValues.length * 2) - 1];
            int i = 0;
            while (i < functionParms.length) {
                functionParms[i++] = '?';
                if (i < functionParms.length) {
                    functionParms[i++] = ',';
                }

            }
            // Set up the SQL command
            this.sqlString = sqlString + "(" + new String(functionParms) + ")";
            this.sqlValues = sqlValues;
        } else {
            this.sqlString = sqlString + "()"; // If no values are passed
                                               // (SqlString, "")
            this.sqlValues = sqlValues;
        }
    }

    /**
     * Prepare the callable statement
     * 
     * @param paramOffset
     *            Offset in command parameters to start at
     * @param procDef
     *            SQL to prepare
     * @param Value
     *            Values to prepare function with
     * @exception SQLException
     *                Thrown when any database or jdbc related error occurs
     */
    private void prepareCallStmt(int paramOffset, String procDef, Object[] fuctionValues) throws SQLException {

        // Prepare function statement
        int i = 1;
        this.callableStatement = connection.prepareCall(procDef);

        // Loop through function and set all values, starting at the offset
        for (Object value : fuctionValues) {
            if (value != null) {
                if (value instanceof InputStream) {
                    try {
                        this.callableStatement.setBinaryStream(i + 1 + paramOffset, (InputStream) value,
                                ((InputStream) value).available());
                    } catch (IOException e) {
                        logger.fatal("Error in PrepareCall Statement : Binary Stream", e);
                    }
                } else {
                    this.callableStatement.setObject(i + 1 + paramOffset, value);
                }
            } else {
                this.callableStatement.setNull(i + 1 + paramOffset, OracleTypes.VARCHAR);
            }
            i++;
        }
    }

    /**
     * Prepare the callable statement for Function call
     *
     * @param paramOffset
     *            Offset in command parameters to start at
     * @param functionDef
     *            SQL to prepare
     * @param functionValues
     *            Values to prepare function with
     * @exception SQLException
     *                Thrown when any database or jdbc related error occurs
     */
    private void functionPrepareCallStmt(int paramOffset, String functionDef, Object[] functionValues)
            throws SQLException {
        int i = 0;
        this.callableStatement = connection.prepareCall(functionDef);
        // Loop through function and set all values, starting at the offset
        for (Object value : functionValues) {
            if (value != null) {
                if (value instanceof InputStream) {
                    try {
                        this.callableStatement.setBinaryStream(i + 1 + paramOffset, (InputStream) value,
                                ((InputStream) value).available());
                    } catch (IOException e) {
                        logger.fatal("Error in function prepareCallSmt : Binary Stream", e);
                    }
                } else {
                    this.callableStatement.setObject(i + 1 + paramOffset, value);
                }
            } else {
                this.callableStatement.setNull(i + 1 + paramOffset, OracleTypes.VARCHAR);
            }
            i++;
        }
    }

}
