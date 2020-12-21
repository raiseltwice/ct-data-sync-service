package com.ct.dataprovider.dao;

import com.ct.dataprovider.dao.CoronavirusEntityData;

public interface DataStore {

    void reInsertData(CoronavirusEntityData coronavirusDataItems);
    void saveAll(CoronavirusEntityData coronavirusEntityData);
    void deleteAll();
}
