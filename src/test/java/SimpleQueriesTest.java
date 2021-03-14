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
import java.util.LinkedList;
import java.util.List;

class SimpleQueriesTest {

    @BeforeAll
    public static void migrate() {
        Flyway flyway = Flyway.configure().dataSource("jdbc:postgresql://localhost:5432/postgres",
                "postgres", "giro1300708").load();

        flyway.migrate();
    }

    @Test
    public void tryToConnect() throws SQLException {
        assertTrue(SimpleQuery.connection());
    }

    @Test
    public void experience5Query() throws IOException, SQLException {
        ArrayList<ArrayList<String >> answer = new ArrayList<>(Arrays.asList(
                new ArrayList<String>(Arrays.asList(new String[]{
                        "1", "Suchareva", "Natalia", "Victorovna", "F", "Belarus", "1975", "294567890", "222160, Zhodino, 8 marta, 16", "7", "1000", "1"})),
                new ArrayList<String>(Arrays.asList(new String[]{
                        "2", "Buyvidovich", "Vasiliy", "Michaylovich", "M", "Belarus", "1963", "292849045", "222162, Zhodino, Gagarina 4, 45", "16", "1500", "3"})),
                new ArrayList<String>(Arrays.asList(new String[]{
                        "4", "Petrov", "Ivan", "Alexeevish", "M", "Ukraine", "1978", "445699857", "222161, Zhodino, pr Pushkina 17, 76", "35", "2000", "2"}))));
        ArrayList<RowRetiree> rowsRetirees =  new ArrayList<>();
        for (int i = 0; i < answer.size(); i++) {
            rowsRetirees.add(new RowRetiree(answer.get(i)));
        }


        assertEquals(new SimpleQuery(rowsRetirees), SimpleQuery.getRetireesExperienceMore5Y());
    }
    @Test
    public void allAbridgedTable() throws IOException, SQLException {
        ArrayList<ArrayList<String >> answer = new ArrayList<>(Arrays.asList(
                new ArrayList<String>(Arrays.asList(new String[]{"1", "Suchareva", "Natalia", "7", "teachers higher category"})),
                new ArrayList<String>(Arrays.asList(new String[]{"2", "Buyvidovich", "Vasiliy", "16", "doctor"})),
                new ArrayList<String>(Arrays.asList(new String[]{"3", "Bruzhas", "Irina", "2", "nurse"})),
                new ArrayList<String>(Arrays.asList(new String[]{"4", "Petrov", "Ivan", "35", "nurse"})),
                new ArrayList<String>(Arrays.asList(new String[]{"5", "Oreshko", "Ihar", "2", "teachers higher category"}))));

        List<RowAbridgedRetiree> rowsRetirees =  new ArrayList<>();
        for (int i = 0; i < answer.size(); i++) {
            rowsRetirees.add(new RowAbridgedRetiree(answer.get(i)));
        }
        assertEquals(new SimpleQuery(rowsRetirees), SimpleQuery.allAbridgedTable());

    }
    @Test
    public void countExperience2Query() throws IOException, SQLException {
        int answer = 2;
        int result = SimpleQuery.countExperienceMore2Y().number_result;
        assertEquals(answer, result);

    }
    @Test
    public void sumExperience10Query() throws IOException, SQLException {
        int answer = 3500;
        int result = SimpleQuery.sumExperienceMore10Y().number_result;
        assertEquals(answer, result);
    }
    @Test
    public void maxRetirementQuery() throws IOException, SQLException {
        int answer = 2000;
        int result = SimpleQuery.maxRetirement().number_result;
        assertEquals(answer, result);

    }
    @Test
    public void minRetirementQuery() throws IOException, SQLException {
        int answer = 500;
        int result = SimpleQuery.minRetirement().number_result;
        assertEquals(answer, result);

    }
    @Test
    public void teacherQuery() throws IOException, SQLException {
        ArrayList<ArrayList<String >> answer = new ArrayList<>(Arrays.asList(
                new ArrayList<String>(Arrays.asList(new String[]{
                        "1", "Suchareva", "Natalia", "Victorovna", "F", "Belarus", "1975", "294567890", "222160, Zhodino, 8 marta, 16",
                        "7", "1000", "1", "teachers higher category"})),
                new ArrayList<String>(Arrays.asList(new String[]{
                        "5", "Oreshko", "Ihar", "Heorgievich", "M", "Belarus", "1967", "294567200", "222160, Zhodino, Stalina 15, 2",
                        "2", "500", "1", "teachers higher category"}))));
        LinkedList<RowRetireeWithPosition> rowsRetirees =  new LinkedList<>();
        for (int i = 0; i < answer.size(); i++) {
            rowsRetirees.add(new RowRetireeWithPosition(answer.get(i)));
        }
        assertEquals(new SimpleQuery(rowsRetirees), SimpleQuery.teachersHigherCategory());

    }

}