package pl.info.czerniak.weekop.util;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionProvider {

    private static DataSource dataSource;

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    public static DataSource getDataSource() {
        if(dataSource == null){
            try {
                Context iniContext = new InitialContext();
                Context envContext = (Context) iniContext.lookup("java:comp/env");

                dataSource = (DataSource) envContext.lookup("jdbc/weekop");
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        return dataSource;
    }
}
