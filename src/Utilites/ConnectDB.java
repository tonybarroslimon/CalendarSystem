package Utilites;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * ConnectDB class declaration
 */
public class ConnectDB {

    private static final String serverName = "wgudb.ucertify.com";
    private static final String port = "3306";
    private static final String databaseName = "WJ05n16";
    private static final String userName = "U05n16";
    private static final String password = "53688553542";
    private static final String databaseURL = "jdbc:mysql://" + serverName + ":" + port + "/" + databaseName;
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    public static Connection conn;

    /**
     * Establishes the connection with the database
     * @return conn
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection makeConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        conn = DriverManager.getConnection(databaseURL, userName, password);
        System.out.println("Connection successful!");
        return conn;
    }

    /**
     * Closes the db connection
     * @throws SQLException
     */
    public static void closeConnection() throws SQLException {
        conn.close();
        System.out.println("Connection closed!");
    }

}
