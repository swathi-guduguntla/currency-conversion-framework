package utils;
public class DataReaderFactory {
    public static TestDataReader getReader(String fileName) {

        if (fileName.endsWith(".json")) {
            return new JsonDataReader();
        } else if (fileName.endsWith(".csv")) {
            return new CsvDataReader();
        } else if (fileName.endsWith(".yaml") || fileName.endsWith(".yml")) {
            return new YamlDataReader();
        }

        throw new RuntimeException("Unsupported file type: " + fileName);
    }
}
