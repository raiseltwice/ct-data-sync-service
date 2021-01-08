package coronavirus.tracker.data.sync.provider;

import coronavirus.tracker.data.sync.reader.model.CSVCoronavirusDataItem;
import coronavirus.tracker.data.sync.utils.CSVToEntityUtils;
import coronavirus.tracker.data.sync.reader.CSVCoronavirusDataFileReader;
import coronavirus.tracker.data.sync.store.CoronavirusEntityData;
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
