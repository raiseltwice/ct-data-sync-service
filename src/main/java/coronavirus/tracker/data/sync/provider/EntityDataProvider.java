package coronavirus.tracker.data.sync.provider;

import coronavirus.tracker.data.sync.store.CoronavirusEntityData;

public interface EntityDataProvider {

    CoronavirusEntityData getData();
    String getInfo();
}
