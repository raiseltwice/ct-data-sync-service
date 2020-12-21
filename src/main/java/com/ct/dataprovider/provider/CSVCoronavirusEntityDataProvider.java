package com.ct.dataprovider.provider;

import com.ct.dataprovider.reader.model.CSVCoronavirusDataItem;
import com.ct.dataprovider.utils.CSVToEntityUtils;
import com.ct.dataprovider.reader.CSVCoronavirusDataFileReader;
import com.ct.dataprovider.dao.CoronavirusEntityData;
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
    public CoronavirusEntityData getData() {
        List<CSVCoronavirusDataItem> coronavirusDataItems = reader.readData();
        return CSVToEntityUtils.constructEntityData(coronavirusDataItems);
    }

    @Override
    public String getInfo() {
        return "File [" + reader.getFileName() + "]";
    }
}
