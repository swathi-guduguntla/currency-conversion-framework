package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.ConversionRequest;
import java.io.InputStream;
import java.util.*;
public class JsonDataReader implements TestDataReader {
    public List<ConversionRequest> read(String filePath) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream(filePath);

        return Arrays.asList(
                mapper.readValue(is, ConversionRequest[].class)
        );
    }
}
