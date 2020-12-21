package com.ct.dataprovider.provider;

import com.ct.dataprovider.dao.CoronavirusEntityData;

public interface EntityDataProvider {

    CoronavirusEntityData getData();
    String getInfo();
}
