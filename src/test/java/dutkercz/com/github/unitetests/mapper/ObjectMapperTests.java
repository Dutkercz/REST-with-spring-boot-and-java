package dutkercz.com.github.unitetests.mapper;


import dutkercz.com.github.data.dto.PersonDTO;
import dutkercz.com.github.models.Person;
import dutkercz.com.github.unitetests.mapper.mock.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static dutkercz.com.github.mapper.ObjectMapper.parseListObjects;
import static dutkercz.com.github.mapper.ObjectMapper.parseObject;
import static dutkercz.com.github.models.PersonGenderEnum.FEMALE;
import static dutkercz.com.github.models.PersonGenderEnum.MALE;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ObjectMapperTests {
    MockPerson inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockPerson();
    }

    @Test
    public void parseEntityToDTOTest() {
        dutkercz.com.github.data.dto.PersonDTO output = parseObject(inputObject.mockEntity(), PersonDTO.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("First Name Test0", output.getFirstName());
        assertEquals("Last Name Test0", output.getLastName());
        assertEquals("Address Test0", output.getAddress());
        assertEquals(MALE, output.getPersonGenderEnum());
    }

    @Test
    public void parseEntityListToDTOListTest() {
        List<PersonDTO> outputList = parseListObjects(inputObject.mockEntityList(), PersonDTO.class);
        PersonDTO outputZero = outputList.get(0);

        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("First Name Test0", outputZero.getFirstName());
        assertEquals("Last Name Test0", outputZero.getLastName());
        assertEquals("Address Test0", outputZero.getAddress());
        assertEquals( MALE, outputZero.getPersonGenderEnum());

        PersonDTO outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("First Name Test7", outputSeven.getFirstName());
        assertEquals("Last Name Test7", outputSeven.getLastName());
        assertEquals("Address Test7", outputSeven.getAddress());
        assertEquals(FEMALE, outputSeven.getPersonGenderEnum());

        PersonDTO outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("First Name Test12", outputTwelve.getFirstName());
        assertEquals("Last Name Test12", outputTwelve.getLastName());
        assertEquals("Address Test12", outputTwelve.getAddress());
        assertEquals(MALE, outputTwelve.getPersonGenderEnum());
    }

    @Test
    public void parseDTOToEntityTest() {
        Person output = parseObject(inputObject.mockDTO(), Person.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("First Name Test0", output.getFirstName());
        assertEquals("Last Name Test0", output.getLastName());
        assertEquals("Address Test0", output.getAddress());
        assertEquals(MALE, output.getPersonGenderEnum());
    }

    @Test
    public void parserDTOListToEntityListTest() {
        List<Person> outputList = parseListObjects(inputObject.mockDTOList(), Person.class);
        Person outputZero = outputList.get(0);

        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("First Name Test0", outputZero.getFirstName());
        assertEquals("Last Name Test0", outputZero.getLastName());
        assertEquals("Address Test0", outputZero.getAddress());
        assertEquals(MALE, outputZero.getPersonGenderEnum());

        Person outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("First Name Test7", outputSeven.getFirstName());
        assertEquals("Last Name Test7", outputSeven.getLastName());
        assertEquals("Address Test7", outputSeven.getAddress());
        assertEquals(MALE, outputSeven.getPersonGenderEnum());

        Person outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("First Name Test12", outputTwelve.getFirstName());
        assertEquals("Last Name Test12", outputTwelve.getLastName());
        assertEquals("Address Test12", outputTwelve.getAddress());
        assertEquals(MALE, outputTwelve.getPersonGenderEnum());
    }
}