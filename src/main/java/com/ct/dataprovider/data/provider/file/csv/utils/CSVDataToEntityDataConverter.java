package com.ct.dataprovider.data.provider.file.csv.utils;

import com.ct.dataprovider.data.model.CSVCoronavirusDataItem;
import com.ct.dataprovider.db.CoronavirusEntityData;
import com.ct.entitycommon.entity.CasesPerCountry;
import com.ct.entitycommon.entity.CasesPerState;
import com.ct.entitycommon.entity.Country;
import com.ct.entitycommon.entity.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CSVDataToEntityDataConverter {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVDataToEntityDataConverter.class);

    private CSVDataToEntityDataConverter() {}

    public static CoronavirusEntityData convertToEntityData(List<CSVCoronavirusDataItem> coronavirusDataItems) {
        LOGGER.info("Constructing coronavirus entity data...");
        List<Country> countries = new ArrayList<>();
        List<State> states = new ArrayList<>();
        List<CasesPerCountry> allCasesPerCountry = new ArrayList<>();
        List<CasesPerState> allCasesPerState = new ArrayList<>();

        for (CSVCoronavirusDataItem csvCoronavirusDataItem : coronavirusDataItems) {
            Country country = getCountry(csvCoronavirusDataItem, countries);
            State state = CSVToEntityUtils.constructState(csvCoronavirusDataItem, country);
            if (state != null) {
                states.add(state);
                List<CasesPerState> casesPerState =
                        CSVToEntityUtils.constructCasesPerState(csvCoronavirusDataItem, state);
                allCasesPerState.addAll(casesPerState);
            } else {
                List<CasesPerCountry> casesPerCountry =
                        CSVToEntityUtils.constructCasesPerCountry(csvCoronavirusDataItem, country);
                allCasesPerCountry.addAll(casesPerCountry);
            }
        }

        allCasesPerCountry.addAll(constructCasesPerCountryByCasesPerStates(allCasesPerState));

        LOGGER.info("Finished constructing coronavirus entity data, " +
                "entity stats: " +
                "countries [{}], states [{}], cases per country [{}], cases per state [{}] ...",
                countries.size(), states.size(), allCasesPerCountry.size(), allCasesPerState.size());

        return new CoronavirusEntityData(countries, states, allCasesPerCountry, allCasesPerState);
    }

    // some countries don't have statistics of cases per date of whole country but do of their states.
    // derives the statistics of cases per date of country from cases per state data
    private static List<CasesPerCountry> constructCasesPerCountryByCasesPerStates(List<CasesPerState> allCasesPerDateOfState) {
        Map<Country, Map<LocalDate, Integer>> casesPerDateOfCountry = new HashMap<>();

        allCasesPerDateOfState.forEach(casesPerDateOfState -> {
            Country country = casesPerDateOfState.getState().getCountry();
            casesPerDateOfCountry.putIfAbsent(country, new HashMap<>());
            Map<LocalDate, Integer> casesPerDate = casesPerDateOfCountry.get(country);
            casesPerDate.merge(casesPerDateOfState.getDate(), casesPerDateOfState.getNumberOfCases(), Integer::sum);
        });
        List<CasesPerCountry> casesPerCountry = new ArrayList<>();
        casesPerDateOfCountry.forEach((country, casesPerDate) ->
                casesPerDate.forEach((date, cases) ->
                        casesPerCountry.add(new CasesPerCountry(country, date, cases))));

        return casesPerCountry;
    }

    // initial data item duplicates country for each state
    private static Country getCountry(CSVCoronavirusDataItem coronavirusDataItem, List<Country> countries) {
        Country country = CSVToEntityUtils.constructCountry(coronavirusDataItem);
        int index;
        // the same as contains, but reuse index
        if ((index = countries.indexOf(country)) == -1) {
            countries.add(country);
        } else {
            country = countries.get(index);
        }
        return country;
    }
}
