package dutkercz.com.github.models;

import dutkercz.com.github.data.dto.PersonDTO;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;

@Entity
public class Person implements Serializable {

    private final static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String firstName;

    @Column(nullable = false, length = 80)
    private String lastName;

    @Column(nullable = false)
    private String address;

    @Enumerated(STRING)@Column(nullable = false, length = 10)
    private PersonGenderEnum gender;

    public Person() {
    }

    public Person(Long id, String firstName, String lastName, String address, PersonGenderEnum gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public PersonGenderEnum getGender() {
        return gender;
    }

    public void setGender(PersonGenderEnum gender) {
        this.gender = gender;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Person person)) return false;

        return Objects.equals(id, person.id) && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(address, person.address);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(firstName);
        result = 31 * result + Objects.hashCode(lastName);
        result = 31 * result + Objects.hashCode(address);
        return result;
    }

    public void update(PersonDTO person) {
        this.id = person.getId();
        if (person.getFirstName() != null && !person.getFirstName().isBlank()){
            this.firstName = person.getFirstName();
        }
        if (person.getLastName() != null && !person.getLastName().isBlank()){
            this.lastName = person.getLastName();
        }
        if (person.getAddress() != null && !person.getAddress().isBlank()){
            this.address = person.getAddress();
        }
        if (person.getGender() != null){
            this.gender = person.getGender();
        }

    }
}
