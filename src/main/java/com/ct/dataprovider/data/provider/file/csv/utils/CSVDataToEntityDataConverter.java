package com.ct.dataprovider.data.provider.file.csv.utils;

import com.ct.dataprovider.data.model.csv.CSVCoronavirusDataItem;
import com.ct.dataprovider.db.CoronavirusEntityData;
import com.ct.entitycommon.entity.Country;
import com.ct.entitycommon.entity.CountryCasesPerDate;
import com.ct.entitycommon.entity.State;
import com.ct.entitycommon.entity.StateCasesPerDate;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CSVDataToEntityDataConverter {

    private CSVDataToEntityDataConverter() {}

    public static CoronavirusEntityData convertToEntityData(List<CSVCoronavirusDataItem> coronavirusDataItems) {
        log.info("Constructing coronavirus entity data...");
        List<Country> countries = new ArrayList<>();
        List<State> states = new ArrayList<>();
        List<CountryCasesPerDate> allCountryCasesPerDate = new ArrayList<>();
        List<StateCasesPerDate> allStateCasesPerDate = new ArrayList<>();

        for (CSVCoronavirusDataItem csvCoronavirusDataItem : coronavirusDataItems) {
            Country country = constructOrGetCountry(csvCoronavirusDataItem, countries);
            State state = CSVToEntityUtils.constructState(csvCoronavirusDataItem, country);
            if (state != null) {
                states.add(state);
                List<StateCasesPerDate> stateCasesPerDate =
                        CSVToEntityUtils.constructCasesPerState(csvCoronavirusDataItem, state);
                allStateCasesPerDate.addAll(stateCasesPerDate);
            } else {
                List<CountryCasesPerDate> countryCasesPerDate =
                        CSVToEntityUtils.constructCasesPerCountry(csvCoronavirusDataItem, country);
                allCountryCasesPerDate.addAll(countryCasesPerDate);
            }
        }

        allCountryCasesPerDate.addAll(constructCountryCasesPerDateByStateCasesPerDate(allStateCasesPerDate));

        log.info("Finished constructing coronavirus entity data, " +
                "entity stats: " +
                "countries [{}], states [{}], cases per country [{}], cases per state [{}] ...",
                countries.size(), states.size(), allCountryCasesPerDate.size(), allStateCasesPerDate.size());

        return new CoronavirusEntityData(countries, states, allCountryCasesPerDate, allStateCasesPerDate);
    }

    // some countries don't have statistics of cases per date of whole country but do of their states.
    // derives the statistics of cases per date of country from cases per state data
    private static List<CountryCasesPerDate> constructCountryCasesPerDateByStateCasesPerDate(
            List<StateCasesPerDate> allStateCasesPerDate
    ) {
        Map<Country, Map<LocalDate, Integer>> countryCasesPerDate = new HashMap<>();

        allStateCasesPerDate.forEach(stateCasesPerDate -> {
            Country country = stateCasesPerDate.getState().getCountry();
            countryCasesPerDate.putIfAbsent(country, new HashMap<>());
            Map<LocalDate, Integer> casesPerDate = countryCasesPerDate.get(country);
            casesPerDate.merge(stateCasesPerDate.getDate(), stateCasesPerDate.getNumberOfCases(), Integer::sum);
        });
        List<CountryCasesPerDate> countryCasesPerDateByStateCasesPerDate = new ArrayList<>();
        countryCasesPerDate.forEach((country, casesPerDate) ->
                casesPerDate.forEach((date, cases) ->
                        countryCasesPerDateByStateCasesPerDate.add(new CountryCasesPerDate(country, date, cases))));

        return countryCasesPerDateByStateCasesPerDate;
    }

    // initial data item duplicates country for each state
    private static Country constructOrGetCountry(CSVCoronavirusDataItem coronavirusDataItem, List<Country> countries) {
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
