package utils;

import models.ConversionRequest;

import java.io.*;
import java.util.*;

public class CsvDataReader implements TestDataReader {

    public List<ConversionRequest> read(String filePath) throws Exception {

        List<ConversionRequest> list = new ArrayList<>();

        InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream(filePath);

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        String line;
        reader.readLine();

        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");

            ConversionRequest req = new ConversionRequest();
            req.setFrom(data[0]);
            req.setTo(data[1]);
            req.setAmount(Double.parseDouble(data[2]));

            list.add(req);
        }

        return list;
    }
}
