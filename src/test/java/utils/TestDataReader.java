package utils;

import models.ConversionRequest;

import java.util.List;

public interface TestDataReader {
    List<ConversionRequest> read(String filePath) throws Exception;
}
