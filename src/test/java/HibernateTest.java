import ORM.ORM;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import tables.RetireeEntity;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class HibernateTest {

    static ORM orm;

    @BeforeAll
    public static void migrate() {
        orm = new ORM();
    }

    @Test
    public void experience5Query() throws SQLException {
        List<RetireeEntity> ans = orm.listRetExp();
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

        List<String> strAns = new LinkedList<>();

        for (RetireeEntity r : ans) {
            strAns.add(r.toString());
            System.out.println(r.toString());
        }

        assertEquals(correctAns, strAns);
    }

    @Test
    public void allQuery() {
        List<Object[]> ans = orm.leftJoinQuery();
        List<String> strAns = new LinkedList<>();

        for (Object[] arr : ans) {
            String str = "";
            for (int i = 0; i < arr.length; i++) {
                str += arr[i];
                if (i != arr.length - 1)
                    str += "\t";
            }

            strAns.add(str);
        }

        List<String> correctAns = new LinkedList<>();
        correctAns.add("1\tSuchareva\tNatalia\t7\tteachers higher category");
        correctAns.add("2\tBuyvidovich\tVasiliy\t16\tdoctor");
        correctAns.add("3\tBruzhas\tIrina\t2\tnurse");
        correctAns.add("4\tPetrov\tIvan\t35\tnurse");
        correctAns.add("5\tOreshko\tIhar\t2\tteachers higher category");

        assertEquals(correctAns, strAns);
    }

    @Test
    public void countExperience2Query() throws IOException, SQLException {
        long ans = orm.countRetirees();
        assertEquals(2, ans);
    }

    @Test
    public void sumExperience10Query() throws IOException, SQLException {
        double ans = orm.sumRetirees();
        assertEquals(3500.0, ans);
    }

    @Test
    public void maxRetirementQuery() throws IOException, SQLException {
        double ans = orm.maxRetirement();
        assertEquals(2000.0, ans);

    }

    @Test
    public void minRetirementQuery() throws IOException, SQLException {
        double ans = orm.maxRetirement();
        assertEquals(2000.0, ans);

    }

    @Test
    public void teacherQuery() throws IOException, SQLException {
        List<Object[]> ans = orm.innerJoinQuery();

        List<String> strAns = new LinkedList<>();

        for (Object[] arr : ans) {
            String str = "";
            for (int i = 0; i < arr.length; i++) {
                str += arr[i];
                if (i != arr.length - 1)
                    str += "\t";
            }

            strAns.add(str);
        }
        ArrayList<String> corrAns = new ArrayList<>();
        corrAns.add("1\tSuchareva\tNatalia\tVictorovna\tF\tBelarus\t1975\t294567890\t" +
                "222160, Zhodino, 8 marta, 16\t7\t1000.0\t1\tteachers higher category");
        corrAns.add("5\tOreshko\tIhar\tHeorgievich\tM\tBelarus\t1967\t294567200\t222160," +
                " Zhodino, Stalina 15, 2\t2\t500.0\t1\tteachers higher category");

        assertEquals(corrAns, strAns);

    }

}
