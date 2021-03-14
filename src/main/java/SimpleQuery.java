import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SimpleQuery {
    static String url = "jdbc:postgresql://localhost:5432/postgres";

    static String user = "postgres";
    static String pwd  = "giro1300708";
    static Connection connection;

    int number_result;
    ArrayList<RowRetiree> tableRetiree;
    List<RowAbridgedRetiree> tableAbridgedRetiree;
    LinkedList<RowRetireeWithPosition> tableRetireeWithPosition;

    public SimpleQuery(int result){
        this.number_result = result;
    }

    public SimpleQuery(ArrayList<RowRetiree> arrayList){
        this.tableRetiree = arrayList;
    }

    public SimpleQuery(LinkedList<RowRetireeWithPosition> arrayList){
        this.tableRetireeWithPosition = arrayList;
    }

    public SimpleQuery(List<RowAbridgedRetiree> arrayAbridgedList){
        this.tableAbridgedRetiree = arrayAbridgedList;
    }

    static boolean connection() throws SQLException {
        connection = DriverManager.getConnection(url, user, pwd);
        if (connection == null)
            return false;
        return true;
    }

    private static ResultSet getResultFromQuery(String query) throws SQLException {
        connection();
        PreparedStatement st = connection.prepareStatement(query);
        return st.executeQuery();
    }

    public static SimpleQuery getRetireesExperienceMore5Y() throws SQLException {
        ResultSet resultSet = getResultFromQuery("select * \n" +
                "from retiree\n" +
                "where retirement_experience > 5;");
        ArrayList<RowRetiree> rows = new ArrayList<>();
        while (resultSet.next()){
            ArrayList<String> values = new ArrayList<>();
            // The column count starts from 1
            for (int i = 1; i <= 12; i++ ) {
                values.add(resultSet.getString(i));
            }
            rows.add(new RowRetiree(values));
        }
        return new SimpleQuery(rows);

    }

    public static SimpleQuery allAbridgedTable() throws SQLException {
        ResultSet resultSet = getResultFromQuery("select retiree.id, surname, name, retirement_experience, job.job_position\n" +
                "from retiree\n" +
                "         left join job\n" +
                "                   on retiree.job_id = job.id;");
        List<RowAbridgedRetiree> rows = new ArrayList<>();
        while (resultSet.next()){
            ArrayList<String> values = new ArrayList<>();
            // The column count starts from 1
            for (int i = 1; i <= 5; i++ ) {
                values.add(resultSet.getString(i));
            }
            rows.add(new RowAbridgedRetiree(values));
        }
        return new SimpleQuery(rows);

    }

    public static SimpleQuery countExperienceMore2Y() throws SQLException {
        ResultSet resultSet = getResultFromQuery("select count(*)\n" +
                "from retiree\n" +
                "where retirement_experience=2;");
        resultSet.next();
        return new SimpleQuery(Integer.parseInt(resultSet.getString(1)));
    }

    public static SimpleQuery sumExperienceMore10Y() throws SQLException {
        ResultSet resultSet = getResultFromQuery("select sum (retirement)\n" +
                "from retiree\n" +
                "where retirement_experience > 10;");
        resultSet.next();
        return new SimpleQuery(Integer.parseInt(resultSet.getString(1)));
    }

    public static SimpleQuery maxRetirement() throws SQLException {
        ResultSet resultSet = getResultFromQuery("select max(retirement)\n" +
                "from retiree;");
        resultSet.next();
        return new SimpleQuery(Integer.parseInt(resultSet.getString(1)));
    }

    public static SimpleQuery minRetirement() throws SQLException {
        ResultSet resultSet = getResultFromQuery("select min(retirement)\n" +
                "from retiree;");
        resultSet.next();
        return new SimpleQuery(Integer.parseInt(resultSet.getString(1)));
    }

    public static SimpleQuery teachersHigherCategory() throws SQLException {
        ResultSet resultSet = getResultFromQuery("select retiree.*, job.job_position\n" +
                "from retiree inner join job\n" +
                "on retiree.job_id = job.id\n" +
                "where job_position = 'teachers higher category';");
        LinkedList<RowRetireeWithPosition> rows = new LinkedList<>();
        while (resultSet.next()){
            ArrayList<String> values = new ArrayList<>();
            // The column count starts from 1
            for (int i = 1; i <= 13; i++ ) {
                values.add(resultSet.getString(i));
            }
            rows.add(new RowRetireeWithPosition(values));
        }
        return new SimpleQuery(rows);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleQuery)) return false;
        SimpleQuery that = (SimpleQuery) o;
        return number_result == that.number_result &&
                Objects.equals(tableRetiree, that.tableRetiree) &&
                Objects.equals(tableAbridgedRetiree, that.tableAbridgedRetiree) &&
                Objects.equals(tableRetireeWithPosition, that.tableRetireeWithPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number_result, tableRetiree, tableAbridgedRetiree, tableRetireeWithPosition);
    }
}

