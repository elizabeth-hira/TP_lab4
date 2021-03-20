import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import queryManagers.HibernateQuery;
import tables.RetireeEntity;
import java.util.LinkedList;
import java.util.List;



public class HibernateQueryTest {

    static HibernateQuery hibernateQuery;

    @BeforeAll
    public static void migrate() {
        hibernateQuery = new HibernateQuery();
    }

    @Test
    public void queryExperiencedRetirees(){
        String query = "from RetireeEntity where retirementExperience > 5";
        List<RetireeEntity> rows = hibernateQuery.getList(query);
        List<String> ans = new LinkedList<>();
        for (RetireeEntity r : rows)
            ans.add(r.toString());
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
    public void queryJobPositions(){
        String query = """
                select r.id, r.surname, r.name, r.retirementExperience, j.jobPosition
                    from RetireeEntity r
                        left join JobEntity j
                            on r.jobId =j.id
                """;
        List<Object[]> rows = hibernateQuery.getList(query);

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
        correctAns.add("1\tSuchareva\tNatalia\t7\tteachers higher category");
        correctAns.add("2\tBuyvidovich\tVasiliy\t16\tdoctor");
        correctAns.add("3\tBruzhas\tIrina\t2\tnurse");
        correctAns.add("4\tPetrov\tIvan\t35\tnurse");
        correctAns.add("5\tOreshko\tIhar\t2\tteachers higher category");
        assertEquals(correctAns, ans);
    }

    @Test
    public void queryCount(){
        String query = "select count(*) from RetireeEntity where retirementExperience = 2";
        long result = (long)hibernateQuery.getSingleResult(query);
        assertEquals(2,result);
    }

    @Test
    public void querySum(){
        String query = "select sum(retirement) from RetireeEntity where retirementExperience > 10";
        double result = (double)hibernateQuery.getSingleResult(query);
        assertEquals(3500,result);
    }

    @Test
    public void queryMax(){
        String query = "select max(retirement) from RetireeEntity";
        double result = (double)hibernateQuery.getSingleResult(query);
        assertEquals(2000,result);
    }

    @Test
    public void queryMin(){
        String query = "select min(retirement) from RetireeEntity";
        double result = (double)hibernateQuery.getSingleResult(query);
        assertEquals(500,result);
    }

    @Test
    public void queryTeachers(){
        String query = """
                select r, j.jobPosition
                    from RetireeEntity r
                        inner join JobEntity j
                            on r.jobId =j.id
                            where j.jobPosition = 'teachers higher category'
                """;
        List<Object[]> rows = hibernateQuery.getList(query);

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
        correctAns.add("1\tSuchareva\tNatalia\tVictorovna\tF\tBelarus\t1975\t294567890\t" +
                "222160, Zhodino, 8 marta, 16\t7\t1000.0\t1\tteachers higher category");
        correctAns.add("5\tOreshko\tIhar\tHeorgievich\tM\tBelarus\t1967\t294567200\t222160," +
                " Zhodino, Stalina 15, 2\t2\t500.0\t1\tteachers higher category");
        assertEquals(correctAns, ans);
    }
}
