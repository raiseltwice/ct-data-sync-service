package com.ct.datasync.provider;

import com.ct.datasync.dao.CoronavirusEntityData;

public interface EntityDataProvider {

    CoronavirusEntityData getData();
    String getInfo();
}
