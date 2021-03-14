import java.util.ArrayList;
import java.util.Objects;

class RowRetiree {

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

    public RowRetiree(ArrayList<String> strings) {
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
