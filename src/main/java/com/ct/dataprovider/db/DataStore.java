package com.ct.dataprovider.db;

public interface DataStore {

    void reInsertData(CoronavirusEntityData coronavirusDataItems);
    void storeDataAsBatch(CoronavirusEntityData coronavirusEntityData);
    void eraseData();
}
