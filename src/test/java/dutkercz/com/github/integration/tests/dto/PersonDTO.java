package dutkercz.com.github.integration.tests.dto;

import dutkercz.com.github.models.PersonGenderEnum;
import jakarta.persistence.Enumerated;

import java.io.Serializable;
import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;

public class PersonDTO implements Serializable {

    private final static long serialVersionUID = 1L;


    private Long id;

    private String firstName;

    private String lastName;

    private String address;

    @Enumerated(STRING)
    private PersonGenderEnum gender;

    private Boolean enabled = true;

    public PersonDTO() {
    }

    public PersonDTO(Long id, String firstName, String lastName, String address, PersonGenderEnum gender) {
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        PersonDTO personDTO = (PersonDTO) o;
        return Objects.equals(getId(), personDTO.getId()) && Objects.equals(getFirstName(), personDTO.getFirstName()) && Objects.equals(getLastName(), personDTO.getLastName()) && Objects.equals(getAddress(), personDTO.getAddress()) && getGender() == personDTO.getGender() && Objects.equals(getEnabled(), personDTO.getEnabled());
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(getId());
        result = 31 * result + Objects.hashCode(getFirstName());
        result = 31 * result + Objects.hashCode(getLastName());
        result = 31 * result + Objects.hashCode(getAddress());
        result = 31 * result + Objects.hashCode(getGender());
        result = 31 * result + Objects.hashCode(getEnabled());
        return result;
    }
}
