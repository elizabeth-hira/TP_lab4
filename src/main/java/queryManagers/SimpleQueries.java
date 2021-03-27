package queryManagers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleQueries {
    static String url = "jdbc:postgresql://localhost:5432/postgres";

    static String user = "postgres";
    static String pwd = "password";

    Connection connection;

    public SimpleQueries() throws SQLException {
        connection = DriverManager.getConnection(url, user, pwd);
    }

    public List<String[]> executeQuery(String query) throws SQLException {
        ResultSet rs;
        Statement st = connection.createStatement();
        rs = st.executeQuery(query);
        List<String[]> queriedData =  processData(rs);
        rs.close();
        return queriedData;
    }

    public List<String[]> processData(ResultSet rs) throws SQLException {
        ResultSetMetaData rsMetaData = rs.getMetaData();
        int columnsNumber = rsMetaData.getColumnCount();
        List<String[]> rows = new ArrayList<>();
        while (rs.next()) {
            String[] row = new String[columnsNumber];
            for (int i = 1; i <= columnsNumber; i++)
                row[i - 1] = rs.getString(i);
            rows.add(row);
        }
        return rows;
    }

    public void releaseResources() throws SQLException {
        connection.close();
    }
}