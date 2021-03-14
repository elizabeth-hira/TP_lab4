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

class RowRetiree{

    int id;
    String surname;
    String name;
    String patronymic;
    String gender;
    String nationality;
    int birth_year;
    int phone;
    String address;
    int retirement_experience;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RowRetiree)) return false;
        RowRetiree that = (RowRetiree) o;
        return id == that.id &&
                birth_year == that.birth_year &&
                phone == that.phone &&
                retirement_experience == that.retirement_experience &&
                retirement == that.retirement &&
                job_id == that.job_id &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(name, that.name) &&
                Objects.equals(patronymic, that.patronymic) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(nationality, that.nationality) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surname, name, patronymic, gender, nationality, birth_year, phone, address, retirement_experience, retirement, job_id);
    }

    int retirement;
    int job_id;

    public RowRetiree(ArrayList<String> strings){
        this.id = Integer.parseInt(strings.get(0));
        this.surname = strings.get(1);
        this.name = strings.get(2);
        this.patronymic = strings.get(3);
        this.gender = strings.get(4);
        this.nationality = strings.get(5);
        this.birth_year = Integer.parseInt(strings.get(6));
        this.phone = Integer.parseInt(strings.get(7));
        this.address = strings.get(8);
        this.retirement_experience = Integer.parseInt(strings.get(9));
        this.retirement = Integer.parseInt(strings.get(10));
        this.job_id = Integer.parseInt(strings.get(11));
    }
}
class RowRetireeWithPosition extends RowRetiree{

    String job_position;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RowRetireeWithPosition)) return false;
        if (!super.equals(o)) return false;
        RowRetireeWithPosition that = (RowRetireeWithPosition) o;
        return job_position.equals(that.job_position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), job_position);
    }

    public RowRetireeWithPosition(ArrayList<String> strings) {
        super(strings);
        this.job_position = strings.get(12);
    }
}

class RowAbridgedRetiree{
    int id;
    String surname;
    String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RowAbridgedRetiree)) return false;
        RowAbridgedRetiree that = (RowAbridgedRetiree) o;
        return id == that.id &&
                retirement_experience == that.retirement_experience &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(name, that.name) &&
                Objects.equals(job_position, that.job_position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, surname, name, retirement_experience, job_position);
    }

    int retirement_experience;
    String job_position;

    public RowAbridgedRetiree(ArrayList<String> strings){
        this.id = Integer.parseInt(strings.get(0));
        this.surname = strings.get(1);
        this.name = strings.get(2);
        this.retirement_experience = Integer.parseInt(strings.get(3));
        this.job_position = strings.get(4);
    }
}
