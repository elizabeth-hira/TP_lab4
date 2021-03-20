package queryManagers;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleQueries {
    static String url = "jdbc:postgresql://localhost:5432/postgres";

    static String user = "postgres";
    static String pwd = "password";

    public static List<Object[]> executeQuery(String query) throws SQLException {
        ResultSet rs = null;
        try (Connection connection = DriverManager.getConnection(url, user, pwd)) {
            if (connection == null)
                return null;
            Statement st = connection.createStatement();
            rs = st.executeQuery(query);
            return processData(rs);
        }
        finally{
            if(rs!=null)
                rs.close();
        }
    }

    public static List<Object[]> processData(ResultSet rs) throws SQLException {

        ResultSetMetaData rsMetaData = rs.getMetaData();
        int columnsNumber = rsMetaData.getColumnCount();

        List<Object[]> rows  = new ArrayList<>();
        while (rs.next()) {
            Object[] row = new Object[columnsNumber];
            for (int i = 1; i <= columnsNumber; i++)
                row[i-1] = rs.getObject(i);
            rows.add(row);
        }
        return rows;
    }
}