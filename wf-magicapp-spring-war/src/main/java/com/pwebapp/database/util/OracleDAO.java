package com.pwebapp.database.util;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface for calling Stored procedure and Stored function
 * 
 * @author : Pravin Prabhulkar
 * @version : V1.0
 * @since : 2015-27-01
 */
public interface OracleDAO {

    /**
     * Executes a SQL Command that has an OUT parameter String.
     * 
     * @param sqlString
     *            Name of Stored procedure
     * @param sqlValues
     *            Parameter as Array Object, otherwise pass null
     * @Return The output String
     * @exception SQLException
     *                Thrown when any database or jdbc related error occurs
     */
    public String executeProcString(String sqlString, Object[] sqlValues) throws SQLException;

    /**
     * Executes a SQL Command that has an OUT parameter INTEGER.
     *
     * @param sqlString
     *            Name of Stored procedure
     * @param sqlValues
     *            Parameter as Array Object, otherwise pass null
     * @Return The output Integer
     * @exception SQLException
     *                Thrown when any database or jdbc related error occurs
     */
    public Integer executeProcInteger(String sqlString, Object[] sqlValues) throws SQLException;

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
    public int executeUpdate(String sqlString, Object[] sqlValues) throws SQLException;

    /**
     * Executes a SQL Function that has an return parameter RersultSet.
     *
     * @param sqlString
     *            Name of Stored function
     * @param sqlValues
     *            Parameter as Array Object, otherwise pass null
     * @Return The output ResultSet
     * @exception SQLException
     *                Thrown when any database or JDBC related error occurs
     */
    public ResultSet executeFunction(String sqlString, Object[] sqlValues) throws SQLException;

}
