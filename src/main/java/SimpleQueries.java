import java.sql.*;
import java.util.ArrayList;

public class SimpleQueries {
    static String url = "jdbc:postgresql://localhost:5432/postgres";

    static String user = "postgres";
    static String pwd  = "giro1300708";
    static Connection connection;

    public static boolean connection() throws SQLException {
        connection = DriverManager.getConnection(url, user, pwd);
        if (connection == null)
            return false;
        return true;
    }
    public static ArrayList<String> getQuery(String query) throws SQLException {
        connection();
        PreparedStatement st = connection.prepareStatement(query);
        ResultSet rs = st.executeQuery();
        ArrayList<String> rows = display(rs);
        rs.close();
        return rows;
    }


    private static ArrayList<String> display(ResultSet rs) throws SQLException {
        ArrayList<String> rows = new ArrayList<>();
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();

        String answer = "";
        while (rs.next()){
            // The column count starts from 1
            for (int i = 1; i <= columnCount; i++ ) {
                answer += rs.getString(i) + "\t";
            }
            rows.add(answer);
            answer = "";
        }
        return rows;
    }


}
