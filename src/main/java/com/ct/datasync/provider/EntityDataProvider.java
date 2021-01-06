package com.ct.datasync.provider;

import com.ct.datasync.service.CoronavirusEntityData;

public interface EntityDataProvider {

    CoronavirusEntityData getData();
    String getInfo();
}
