package com.ct.datasync.provider;

import com.ct.datasync.store.CoronavirusEntityData;

public interface EntityDataProvider {

    CoronavirusEntityData getData();
    String getInfo();
}
