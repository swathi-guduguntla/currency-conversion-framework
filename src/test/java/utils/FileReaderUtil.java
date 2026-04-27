package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.ConversionRequest;
import java.io.InputStream;
import java.util.*;
public class FileReaderUtil {
    public static List<ConversionRequest> readJson(String fileName) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

        InputStream is = FileReaderUtil.class
                .getClassLoader()
                .getResourceAsStream(fileName);

        if (is == null) {
            throw new RuntimeException("File not found in resources: " + fileName);
        }

        return Arrays.asList(
                mapper.readValue(is, ConversionRequest[].class)
        );
    }
}
