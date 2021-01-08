package coronavirus.tracker.data.sync.store;

public interface DataStore {

    void reInsertData(CoronavirusEntityData coronavirusDataItems);
    void saveAll(CoronavirusEntityData coronavirusEntityData);
    void deleteAll();
}
