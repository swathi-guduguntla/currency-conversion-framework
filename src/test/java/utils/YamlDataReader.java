package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import models.ConversionRequest;

import java.io.InputStream;
import java.util.*;

public class YamlDataReader implements TestDataReader {

    public List<ConversionRequest> read(String filePath) throws Exception {

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream(filePath);

        return Arrays.asList(
                mapper.readValue(is, ConversionRequest[].class)
        );
    }
}
