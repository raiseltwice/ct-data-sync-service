package com.ct.datasync.dao;

public interface DataStore {

    void reInsertData(CoronavirusEntityData coronavirusDataItems);
    void saveAll(CoronavirusEntityData coronavirusEntityData);
    void deleteAll();
}
