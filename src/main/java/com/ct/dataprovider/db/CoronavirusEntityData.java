package com.ct.dataprovider.db;

import com.ct.entitycommon.entity.Country;
import com.ct.entitycommon.entity.CountryCasesPerDate;
import com.ct.entitycommon.entity.State;
import com.ct.entitycommon.entity.StateCasesPerDate;

import java.util.List;

public class CoronavirusEntityData {

    private final List<Country> countries;
    private final List<State> states;
    private final List<CountryCasesPerDate> casesPerCountry;
    private final List<StateCasesPerDate> casesPerState;

    public CoronavirusEntityData(List<Country> countries,
                                 List<State> states,
                                 List<CountryCasesPerDate> casesPerCountry,
                                 List<StateCasesPerDate> casesPerState) {
        this.countries = countries;
        this.states = states;
        this.casesPerCountry = casesPerCountry;
        this.casesPerState = casesPerState;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public List<State> getStates() {
        return states;
    }

    public List<CountryCasesPerDate> getCasesPerCountry() {
        return casesPerCountry;
    }

    public List<StateCasesPerDate> getCasesPerState() {
        return casesPerState;
    }
}
