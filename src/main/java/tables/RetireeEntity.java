package tables;

import javax.persistence.*;
import java.util.Objects;


@Entity
@Table(name = "retiree", schema = "public", catalog = "postgres")
public class RetireeEntity {
  private Long id;
  private String surname;
  private String name;
  private String patronymic;
  private String gender;
  private String nationality;
  private Integer birthYear;
  private Long phone;
  private String address;
  private Integer retirementExperience;
  private Double retirement;
  private Long jobId;



  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Basic
  @Column(name = "surname", nullable = false, length = -1)
  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  @Basic
  @Column(name = "name", nullable = false, length = -1)
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Basic
  @Column(name = "patronymic", nullable = true, length = -1)
  public String getPatronymic() {
    return patronymic;
  }

  public void setPatronymic(String patronymic) {
    this.patronymic = patronymic;
  }

  @Basic
  @Column(name = "gender", nullable = true, length = -1)
  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  @Basic
  @Column(name = "nationality", nullable = true, length = -1)
  public String getNationality() {
    return nationality;
  }

  public void setNationality(String nationality) {
    this.nationality = nationality;
  }

  @Basic
  @Column(name = "birth_year", nullable = true)
  public Integer getBirthYear() {
    return birthYear;
  }

  public void setBirthYear(Integer birthYear) {
    this.birthYear = birthYear;
  }

  @Basic
  @Column(name = "phone", nullable = true)
  public Long getPhone() {
    return phone;
  }

  public void setPhone(Long phone) {
    this.phone = phone;
  }

  @Basic
  @Column(name = "address", nullable = true, length = -1)
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Basic
  @Column(name = "retirement_experience", nullable = true)
  public Integer getRetirementExperience() {
    return retirementExperience;
  }

  public void setRetirementExperience(Integer retirementExperience) {
    this.retirementExperience = retirementExperience;
  }

  @Basic
  @Column(name = "retirement", nullable = true, precision = 0)
  public Double getRetirement() {
    return retirement;
  }

  public void setRetirement(Double retirement) {
    this.retirement = retirement;
  }

  @Basic
  @Column(name = "job_id")
  public Long getJobId() {
    return jobId;
  }

  public void setJobId(Long jobId) {
    this.jobId = jobId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    RetireeEntity that = (RetireeEntity) o;

    if (!Objects.equals(id, that.id)) return false;
    if (!Objects.equals(surname, that.surname)) return false;
    if (!Objects.equals(name, that.name)) return false;
    if (!Objects.equals(patronymic, that.patronymic)) return false;
    if (!Objects.equals(gender, that.gender)) return false;
    if (!Objects.equals(nationality, that.nationality)) return false;
    if (!Objects.equals(birthYear, that.birthYear)) return false;
    if (!Objects.equals(phone, that.phone)) return false;
    if (!Objects.equals(address, that.address)) return false;
    if (!Objects.equals(retirementExperience, that.retirementExperience))
      return false;
    if (!Objects.equals(retirement, that.retirement)) return false;
    return Objects.equals(jobId, that.jobId);
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (surname != null ? surname.hashCode() : 0);
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
    result = 31 * result + (gender != null ? gender.hashCode() : 0);
    result = 31 * result + (nationality != null ? nationality.hashCode() : 0);
    result = 31 * result + (birthYear != null ? birthYear.hashCode() : 0);
    result = 31 * result + (phone != null ? phone.hashCode() : 0);
    result = 31 * result + (address != null ? address.hashCode() : 0);
    result = 31 * result + (retirementExperience != null ? retirementExperience.hashCode() : 0);
    result = 31 * result + (retirement != null ? retirement.hashCode() : 0);
    result = 31 * result + (jobId != null ? jobId.hashCode() : 0);
    return result;
  }

  public String toString(){
    return id+"\t"+surname+'\t'+name+'\t'+patronymic+'\t'+gender+'\t'+nationality+'\t'+birthYear+'\t'+phone+'\t'
            +address+'\t'+retirementExperience+'\t'+retirement+'\t'+jobId;
  }






}
