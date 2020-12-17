package com.ct.dataprovider.data.reader;

import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;
import com.ct.dataprovider.data.model.CSVCoronavirusDataItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CSVCoronavirusDataFileReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVCoronavirusDataFileReader.class);

    private final String fileName;

    public CSVCoronavirusDataFileReader(@Value("${app.data.provider.file.csv}") String fileName) {
        this.fileName = fileName;
    }

    // test
    public List<CSVCoronavirusDataItem> readCSVCoronavirusData() {
        List<CSVCoronavirusDataItem> coronavirusDataItems = new ArrayList<>();
        try (FileReader fileReader = new FileReader(fileName)) {
            CSVReaderHeaderAware csvReader = new CSVReaderHeaderAware(fileReader);
            Map<String, String> valuesToHeaders;
            while ((valuesToHeaders = csvReader.readMap()) != null) {
                coronavirusDataItems.add(
                        CSVCoronavirusDataItem.constructCoronavirusDataItem(valuesToHeaders));
            }
        } catch (IOException | CsvValidationException e) {
            LOGGER.error("Error while reading [{}] file: [{}]", fileName, e.getMessage());
        }
        LOGGER.info("Finished read data from [{}] file, size [{}]", fileName, coronavirusDataItems.size());

        return coronavirusDataItems;
    }

    public String getFileName() {
        return fileName;
    }
}
