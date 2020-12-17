package com.ct.dataprovider.data.provider;

import com.ct.dataprovider.db.CoronavirusEntityData;

public interface EntityDataProvider {

    CoronavirusEntityData getCoronavirusEntityData();
    String getInfo();
}
