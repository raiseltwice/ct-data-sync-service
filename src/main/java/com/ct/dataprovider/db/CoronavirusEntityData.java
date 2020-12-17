package com.ct.dataprovider.db;

import com.ct.entitycommon.entity.CasesPerCountry;
import com.ct.entitycommon.entity.CasesPerState;
import com.ct.entitycommon.entity.Country;
import com.ct.entitycommon.entity.State;

import java.util.List;

public class CoronavirusEntityData {

    private final List<Country> countries;
    private final List<State> states;
    private final List<CasesPerCountry> casesPerCountry;
    private final List<CasesPerState> casesPerState;

    public CoronavirusEntityData(List<Country> countries,
                                 List<State> states,
                                 List<CasesPerCountry> casesPerCountry,
                                 List<CasesPerState> casesPerState) {
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

    public List<CasesPerCountry> getCasesPerCountry() {
        return casesPerCountry;
    }

    public List<CasesPerState> getCasesPerState() {
        return casesPerState;
    }
}
