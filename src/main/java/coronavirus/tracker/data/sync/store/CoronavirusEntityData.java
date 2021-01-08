package coronavirus.tracker.data.sync.store;

import coronavirus.tracker.entitycommon.entity.Country;
import coronavirus.tracker.entitycommon.entity.CountryCasesPerDate;
import coronavirus.tracker.entitycommon.entity.State;
import coronavirus.tracker.entitycommon.entity.StateCasesPerDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CoronavirusEntityData {

    private final List<Country> countries;
    private final List<State> states;
    private final List<CountryCasesPerDate> casesPerCountry;
    private final List<StateCasesPerDate> casesPerState;
}
