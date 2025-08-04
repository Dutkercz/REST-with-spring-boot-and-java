package dutkercz.com.github.data.dto;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dutkercz.com.github.models.PersonGenderEnum;
import dutkercz.com.github.serializer.EnumGenderSerializer;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import static jakarta.persistence.EnumType.STRING;

//altera a ordem de visualização do JSON nas respostas (GET)
@JsonPropertyOrder({"id", "first_name", "last_name", "address", "personGenderEnum"})
@JsonFilter("PersonFilter")//adiciona o filtro personalizado do ObjectMapperConfig
public class PersonDTO implements Serializable {

    private final static long serialVersionUID = 1L;


    private Long id;

    @JsonProperty("first_name")//altera o nome na visualização do JSON (GET)
    private String firstName;

    @JsonProperty("last_name")
    @JsonInclude(JsonInclude.Include.NON_NULL) // apenas renderiza quando não estiver NULO
    private String lastName;

    @JsonInclude(JsonInclude.Include.NON_EMPTY) //apenas renderiza quando não estiver VAZIO (EXMPLO "")
    private String address;

    @Enumerated(STRING)
    // @JsonIgnore  Suprime a visualização do campo na resposta do JSON
    @JsonSerialize(using = EnumGenderSerializer.class)
    private PersonGenderEnum gender;


    private String sensitiveData;

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

    public String getSensitiveData() {
        return sensitiveData;
    }

    public void setSensitiveData(String sensitiveData) {
        this.sensitiveData = sensitiveData;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PersonDTO personDTO = (PersonDTO) o;
        return Objects.equals(id, personDTO.id) && Objects.equals(firstName, personDTO.firstName) && Objects.equals(lastName, personDTO.lastName) && Objects.equals(address, personDTO.address) && gender == personDTO.gender && Objects.equals(sensitiveData, personDTO.sensitiveData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, address, gender, sensitiveData);
    }
}
