package com.ct.datasync.provider;

import com.ct.datasync.reader.model.CSVCoronavirusDataItem;
import com.ct.datasync.utils.CSVToEntityUtils;
import com.ct.datasync.reader.CSVCoronavirusDataFileReader;
import com.ct.datasync.dao.CoronavirusEntityData;
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
