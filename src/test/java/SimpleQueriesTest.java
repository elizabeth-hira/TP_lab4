import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.flywaydb.core.Flyway;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.management.BufferPoolMXBean;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

class SimpleQueriesTest {
    static ArrayList<String> queries;

    static {
        try {
            queries = getQueries();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeAll
    public static void migrate() {
        Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://localhost:5432/postgres",
                "postgres", "giro1300708").load();

        flyway.migrate();
    }

    @Test
    public void tryToConnect() throws SQLException {
        assertTrue(SimpleQueries.connection());
    }

    public static ArrayList<String> getQueries() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(
                "src/main/resources/db/migration/V1_1_0__queries.sql"));
        ArrayList<String> list = new ArrayList<>();
        String str;
        String request = "";
        while ((str = reader.readLine()) != null){
            if (str.contains("select") && request != ""){
                list.add(request);
                request = str + "\n";
            }
            else
                request += str + "\n";
        }
        list.add(request);
        return list;
    }

    @Test
    public void experience5Query() throws IOException, SQLException {
        ArrayList<String> answer = new ArrayList<>();
        answer.add("1\tSuchareva\tNatalia\tVictorovna\tF\tBelarus\t1975\t294567890\t222160, Zhodino, 8 marta, 16\t7\t1000\t1\t");
        answer.add("2\tBuyvidovich\tVasiliy\tMichaylovich\tM\tBelarus\t1963\t292849045\t222162, Zhodino, Gagarina 4, 45\t16\t1500\t3\t");
        answer.add("4\tPetrov\tIvan\tAlexeevish\tM\tUkraine\t1978\t445699857\t222161, Zhodino, pr Pushkina 17, 76\t35\t2000\t2\t");
        ArrayList<String> result = SimpleQueries.getQuery(queries.get(0));
        assertEquals(answer, result);
    }
    @Test
    public void allQuery() throws IOException, SQLException {
        ArrayList<String> answer = new ArrayList<>();
        answer.add("1\tSuchareva\tNatalia\t7\tteachers higher category\t");
        answer.add("2\tBuyvidovich\tVasiliy\t16\tdoctor\t");
        answer.add("3\tBruzhas\tIrina\t2\tnurse\t");
        answer.add("4\tPetrov\tIvan\t35\tnurse\t");
        answer.add("5\tOreshko\tIhar\t2\tteachers higher category\t");
        ArrayList<String> result = SimpleQueries.getQuery(queries.get(1));
        assertEquals(answer, result);

    }
    @Test
    public void countExperience2Query() throws IOException, SQLException {
        ArrayList<String> answer = new ArrayList<>();
        answer.add("2\t");
        ArrayList<String> result = SimpleQueries.getQuery(queries.get(2));
        assertEquals(answer, result);

    }
    @Test
    public void sumExperience10Query() throws IOException, SQLException {
        ArrayList<String> answer = new ArrayList<>();
        answer.add("3500\t");
        ArrayList<String> result = SimpleQueries.getQuery(queries.get(3));
        assertEquals(answer, result);
    }
    @Test
    public void maxRetirementQuery() throws IOException, SQLException {
        ArrayList<String> answer = new ArrayList<>();
        answer.add("2000\t");
        ArrayList<String> result = SimpleQueries.getQuery(queries.get(4));
        assertEquals(answer, result);

    }
    @Test
    public void minRetirementQuery() throws IOException, SQLException {
        ArrayList<String> answer = new ArrayList<>();
        answer.add("500\t");
        ArrayList<String> result = SimpleQueries.getQuery(queries.get(5));
        assertEquals(answer, result);

    }
    @Test
    public void teacherQuery() throws IOException, SQLException {
        ArrayList<String> answer = new ArrayList<>();
        answer.add("1\tSuchareva\tNatalia\tVictorovna\tF\tBelarus\t1975\t294567890\t222160, Zhodino, 8 marta, 16\t7\t1000\t1\tteachers higher category\t");
        answer.add("5\tOreshko\tIhar\tHeorgievich\tM\tBelarus\t1967\t294567200\t222160, Zhodino, Stalina 15, 2\t2\t500\t1\tteachers higher category\t");
        ArrayList<String> result = SimpleQueries.getQuery(queries.get(6));
        assertEquals(answer, result);

    }

}