package com.ct.datasync.store;

import com.ct.entitycommon.entity.Country;
import com.ct.entitycommon.entity.CountryCasesPerDate;
import com.ct.entitycommon.entity.State;
import com.ct.entitycommon.entity.StateCasesPerDate;
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
