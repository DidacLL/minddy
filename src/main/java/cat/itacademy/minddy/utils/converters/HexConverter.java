package cat.itacademy.minddy.utils.converters;


import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter()
public class HexConverter implements AttributeConverter<String, Integer> {

    // TODO: 09/07/2023 Verify that it works well for searches as it may, it seems that we only need to use parentID for searches
    @Override
    public Integer convertToDatabaseColumn(String hexa) {
        return Integer.parseInt(hexa,16);
    }

    @Override
    public String convertToEntityAttribute(Integer val) {
        return Integer.toHexString(val);
    }
}
