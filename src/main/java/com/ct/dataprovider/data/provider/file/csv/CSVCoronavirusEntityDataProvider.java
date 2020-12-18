package com.ct.dataprovider.data.provider.file.csv;

import com.ct.dataprovider.data.model.CSVCoronavirusDataItem;
import com.ct.dataprovider.data.provider.EntityDataProvider;
import com.ct.dataprovider.data.provider.file.csv.utils.CSVDataToEntityDataConverter;
import com.ct.dataprovider.data.reader.CSVCoronavirusDataFileReader;
import com.ct.dataprovider.db.CoronavirusEntityData;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile("csv")
public class CSVCoronavirusEntityDataProvider implements EntityDataProvider {

    private final CSVCoronavirusDataFileReader reader;

    public CSVCoronavirusEntityDataProvider(CSVCoronavirusDataFileReader reader) {
        this.reader = reader;
    }

    @Override
    public CoronavirusEntityData getCoronavirusEntityData() {
        List<CSVCoronavirusDataItem> coronavirusDataItems = reader.readCSVCoronavirusData();
        return CSVDataToEntityDataConverter.convertToEntityData(coronavirusDataItems);
    }

    @Override
    public String getInfo() {
        return "File [" + reader.getFileName() + "]";
    }
}
