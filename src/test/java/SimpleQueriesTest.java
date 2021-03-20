import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.flywaydb.core.Flyway;
import queryManagers.SimpleQueries;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class SimpleQueriesTest {

    @BeforeAll
    public static void migrate() {
        Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://localhost:5432/postgres",
                "postgres", "password").load();

        flyway.migrate();
    }

    @Test
    public void queryExperiencedRetirees() throws SQLException {
        String sql = """
                select *
                from retiree
                where retirement_experience > 5;
                """;

        List<Object[]> rows = SimpleQueries.executeQuery(sql);
        List<String> ans = new LinkedList<>();

        for (Object[] row : rows) {
            String rowStr = "";
            for (int i = 0; i < row.length; i++) {
                rowStr += row[i];
                if (i != row.length - 1)
                    rowStr += "\t";
            }
            ans.add(rowStr);
        }

        List<String> correctAns = new LinkedList<>();
        correctAns.add("1\tSuchareva\tNatalia\tVictorovna\tF\tBelarus\t1975\t" +
                "294567890\t222160, Zhodino, 8 marta, 16" +
                "\t7\t1000.0\t1");
        correctAns.add("2\tBuyvidovich\tVasiliy\tMichaylovich\tM\tBelarus\t" +
                "1963\t292849045\t" +
                "222162, Zhodino, Gagarina 4, 45\t16\t1500.0\t3");
        correctAns.add("4\tPetrov\tIvan\tAlexeevish\tM\tUkraine\t1978\t445699857\t" +
                "222161, Zhodino, pr Pushkina 17, 76" +
                "\t35\t2000.0\t2");
        assertEquals(correctAns, ans);
    }


    @Test
    public void queryJobPositions() throws SQLException {
        String sql = """
                select retiree.id, surname, name, retirement_experience, job.job_position
                from retiree
                         left join job
                                   on retiree.job_id = job.id;
                """;

        List<String> correctAns = new LinkedList<>();
        correctAns.add("1\tSuchareva\tNatalia\t7\tteachers higher category");
        correctAns.add("2\tBuyvidovich\tVasiliy\t16\tdoctor");
        correctAns.add("3\tBruzhas\tIrina\t2\tnurse");
        correctAns.add("4\tPetrov\tIvan\t35\tnurse");
        correctAns.add("5\tOreshko\tIhar\t2\tteachers higher category");
        assertEquals(correctAns, getAnswer(sql));
    }

    @Test
    public void queryCount() throws SQLException{
        String sql = """
                select count(*)
                from retiree
                where retirement_experience=2;
                """;
        List<String> ans = getAnswer(sql);
        assertEquals(new LinkedList<>(Collections.singletonList("2")),ans);
    }

    @Test
    public void querySum() throws SQLException{
        String sql = """
                select sum (retirement)
                from retiree
                where retirement_experience > 10;
                """;
        List<String> ans = getAnswer(sql);
        assertEquals(new LinkedList<>(Collections.singletonList("3500.0")),ans);
    }

    @Test
    public void queryMax() throws SQLException{
        String sql = """
                select max(retirement)
                from retiree;
                """;
        List<String> ans = getAnswer(sql);
        assertEquals(new LinkedList<>(Collections.singletonList("2000.0")),ans);
    }

    @Test
    public void queryMin() throws SQLException{
        String sql = """
                select min(retirement)
                from retiree;
                """;
        List<String> ans = getAnswer(sql);
        assertEquals(new LinkedList<>(Collections.singletonList("500.0")),ans);
    }

    @Test
    public void queryTeachers() throws SQLException {
        String sql = """
                select retiree.*, job.job_position
                from retiree inner join job
                                        on retiree.job_id = job.id
                where job_position = 'teachers higher category';
                """;

        List<String> correctAns = new LinkedList<>();
        correctAns.add("1\tSuchareva\tNatalia\tVictorovna\tF\tBelarus\t1975\t294567890\t" +
                "222160, Zhodino, 8 marta, 16\t7\t1000.0\t1\tteachers higher category");
        correctAns.add("5\tOreshko\tIhar\tHeorgievich\tM\tBelarus\t1967\t294567200\t222160," +
                " Zhodino, Stalina 15, 2\t2\t500.0\t1\tteachers higher category");
        assertEquals(correctAns, getAnswer(sql));
    }

    public static List<String> getAnswer(String sql) throws SQLException {
        List<Object[]> rows = SimpleQueries.executeQuery(sql);
        List<String> ans = new LinkedList<>();

        for (Object[] row : rows) {
            String rowStr = "";
            for (int i = 0; i < row.length; i++) {
                rowStr += row[i];
                if (i != row.length - 1)
                    rowStr += "\t";
            }
            ans.add(rowStr);
        }

        return ans;
    }



}
