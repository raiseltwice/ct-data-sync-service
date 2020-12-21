package com.ct.dataprovider.utils;

import com.ct.dataprovider.dao.CoronavirusEntityData;
import com.ct.dataprovider.reader.model.CSVCoronavirusDataItem;
import com.ct.entitycommon.entity.Country;
import com.ct.entitycommon.entity.CountryCasesPerDate;
import com.ct.entitycommon.entity.State;
import com.ct.entitycommon.entity.StateCasesPerDate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class CSVToEntityUtils {

    private CSVToEntityUtils() {}

    public static CoronavirusEntityData constructEntityData(List<CSVCoronavirusDataItem> coronavirusDataItems) {
        log.info("Constructing coronavirus entity data...");
        List<Country> countries = new ArrayList<>();
        List<State> states = new ArrayList<>();
        List<CountryCasesPerDate> allCountryCasesPerDate = new ArrayList<>();
        List<StateCasesPerDate> allStateCasesPerDate = new ArrayList<>();

        for (CSVCoronavirusDataItem csvCoronavirusDataItem : coronavirusDataItems) {
            Country country = getOrConstructCountry(csvCoronavirusDataItem, countries);
            countries.add(country);
            State state = constructState(csvCoronavirusDataItem, country);
            if (state != null) {
                states.add(state);
                List<StateCasesPerDate> stateCasesPerDate =
                        constructCasesPerState(csvCoronavirusDataItem, state);
                allStateCasesPerDate.addAll(stateCasesPerDate);
            } else {
                List<CountryCasesPerDate> countryCasesPerDate =
                        constructCasesPerCountry(csvCoronavirusDataItem, country);
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

    // initial data item duplicates country for each state
    // gets rid of country duplicates
    private static Country getOrConstructCountry(CSVCoronavirusDataItem coronavirusDataItem, List<Country> countries) {
        Country country = null;
        if (countries.isEmpty()) {
            country = constructCountry(coronavirusDataItem);
        } else {
            for (Country countryItem : countries) {
                if (countryItem.getCountryName().equals(coronavirusDataItem.getCountryName())) {
                    country = countryItem;
                } else {
                    country = constructCountry(coronavirusDataItem);
                }
            }
        }
        return country;
    }

    public static List<StateCasesPerDate> constructCasesPerState(CSVCoronavirusDataItem coronavirusDataItem,
                                                                 State state) {
        return coronavirusDataItem.getCasesPerDate().entrySet().stream()
                .map(entry -> new StateCasesPerDate(state, entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public static List<CountryCasesPerDate> constructCasesPerCountry(CSVCoronavirusDataItem coronavirusDataItem,
                                                                     Country country) {
        return coronavirusDataItem.getCasesPerDate().entrySet().stream()
                .map(entry -> new CountryCasesPerDate(country, entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public static Country constructCountry(CSVCoronavirusDataItem coronavirusDataItem) {
        if (StringUtils.isBlank(coronavirusDataItem.getStateName())) {
            return new Country(
                    coronavirusDataItem.getCountryName(),
                    coronavirusDataItem.getLatitude(),
                    coronavirusDataItem.getLongitude());
        } else {
            return new Country(coronavirusDataItem.getCountryName(), null, null);
        }
    }

    public static State constructState(CSVCoronavirusDataItem coronavirusDataItem, Country country) {
        State state = null;
        if (!StringUtils.isBlank(coronavirusDataItem.getStateName())) {
            state = new State(
                    coronavirusDataItem.getStateName(),
                    coronavirusDataItem.getLatitude(),
                    coronavirusDataItem.getLongitude(),
                    country);
        }
        return state;
    }

    // some countries don't have statistics of cases per date of whole country but do of their states.
    // derives the statistics of cases per date of country from cases per date of state data
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
}
