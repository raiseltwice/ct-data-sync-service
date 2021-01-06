package com.ct.datasync.service;

public interface DataStore {

    void reInsertData(CoronavirusEntityData coronavirusDataItems);
    void saveAll(CoronavirusEntityData coronavirusEntityData);
    void deleteAll();
}
