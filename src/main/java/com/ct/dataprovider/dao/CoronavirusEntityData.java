package com.ct.dataprovider.dao;

import com.ct.entitycommon.entity.Country;
import com.ct.entitycommon.entity.CountryCasesPerDate;
import com.ct.entitycommon.entity.State;
import com.ct.entitycommon.entity.StateCasesPerDate;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
@AllArgsConstructor
public class CoronavirusEntityData {

    private final Set<Country> countries;
    private final Set<State> states;
    private final List<CountryCasesPerDate> casesPerCountry;
    private final List<StateCasesPerDate> casesPerState;
}
