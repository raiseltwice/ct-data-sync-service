package com.ct.dataprovider.data.reader;

import com.ct.dataprovider.data.model.CSVCoronavirusDataItem;
import com.opencsv.CSVReaderHeaderAware;
import com.opencsv.exceptions.CsvValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class CSVCoronavirusDataFileReader {

    private final String fileName;

    public CSVCoronavirusDataFileReader(@Value("${app.data.provider.file.csv}") String fileName) {
        this.fileName = fileName;
    }

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
            log.error("Error while reading [{}] file: [{}]", fileName, e.getMessage());
        }
        log.info("Finished read data from [{}] file, size [{}]", fileName, coronavirusDataItems.size());

        return coronavirusDataItems;
    }

    public String getFileName() {
        return fileName;
    }
}
