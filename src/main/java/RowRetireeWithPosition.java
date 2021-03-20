import java.util.ArrayList;
import java.util.Objects;

class RowRetireeWithPosition extends RowRetiree {

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
