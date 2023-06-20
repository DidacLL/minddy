package cat.itacademy.minddy.utils;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter()
public class HexConverter implements AttributeConverter<String, Integer> {


    @Override
    public Integer convertToDatabaseColumn(String hexa) {
        return Integer.parseInt(hexa,16);
    }

    @Override
    public String convertToEntityAttribute(Integer val) {
        return Integer.toHexString(val);
    }
}
