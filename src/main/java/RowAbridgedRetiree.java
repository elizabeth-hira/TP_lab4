import java.util.ArrayList;
import java.util.Objects;

class RowAbridgedRetiree {
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

    public RowAbridgedRetiree(ArrayList<String> strings) {
        this.id = Integer.parseInt(strings.get(0));
        this.surname = strings.get(1);
        this.name = strings.get(2);
        this.retirement_experience = Integer.parseInt(strings.get(3));
        this.job_position = strings.get(4);
    }
}
