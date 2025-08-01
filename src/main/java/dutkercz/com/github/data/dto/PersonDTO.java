package dutkercz.com.github.data.dto;

import dutkercz.com.github.models.PersonGenderEnum;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;

public class PersonDTO implements Serializable {

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
    private PersonGenderEnum personGenderEnum;

    public PersonDTO() {
    }

    public PersonDTO(Long id, String firstName, String lastName, String address, PersonGenderEnum personGenderEnum) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.personGenderEnum = personGenderEnum;
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

    public PersonGenderEnum getPersonGenderEnum() {
        return personGenderEnum;
    }

    public void setPersonGenderEnum(PersonGenderEnum personGenderEnum) {
        this.personGenderEnum = personGenderEnum;
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof PersonDTO personDTO)) return false;

        return Objects.equals(id, personDTO.id) && Objects.equals(firstName, personDTO.firstName) && Objects.equals(lastName, personDTO.lastName) && Objects.equals(address, personDTO.address);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(id);
        result = 31 * result + Objects.hashCode(firstName);
        result = 31 * result + Objects.hashCode(lastName);
        result = 31 * result + Objects.hashCode(address);
        return result;
    }

    public void update(PersonDTO personDTO) {
        this.id = personDTO.getId();
        if (personDTO.getFirstName() != null && !personDTO.getFirstName().isBlank()){
            this.firstName = personDTO.getFirstName();
        }
        if (personDTO.getLastName() != null && !personDTO.getLastName().isBlank()){
            this.lastName = personDTO.getLastName();
        }
        if (personDTO.getAddress() != null && !personDTO.getAddress().isBlank()){
            this.address = personDTO.getAddress();
        }
        if (personDTO.getPersonGenderEnum() != null){
            this.personGenderEnum = personDTO.getPersonGenderEnum();
        }

    }
}
