package dutkercz.com.github.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import dutkercz.com.github.models.PersonGenderEnum;

import java.io.IOException;

public class EnumGenderSerializer extends JsonSerializer<PersonGenderEnum> {

    @Override
    public void serialize(PersonGenderEnum gender, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeString(
                String.valueOf(gender).equals("MALE") ? "M" :
                        String.valueOf(gender).equals("FEMALE") ? "F" : "N/I"
        );
    }
}
