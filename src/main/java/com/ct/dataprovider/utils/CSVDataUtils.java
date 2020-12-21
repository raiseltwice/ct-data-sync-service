package com.ct.dataprovider.utils;

import com.ct.dataprovider.reader.model.CSVCoronavirusDataItem;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

// utils for csv data on its own
public class CSVDataUtils {

    private static final String DATE_PATTERN = "M/d/yy";

    private CSVDataUtils() {}

    public static CSVCoronavirusDataItem constructCoronavirusDataItem(Map<String, String> valuesToHeaders) {
        String stateName = valuesToHeaders.get(CSVHeaders.STATE_HEADER_NAME);
        valuesToHeaders.remove(CSVHeaders.STATE_HEADER_NAME);
        String countryName = valuesToHeaders.get(CSVHeaders.COUNTRY_HEADER_NAME);
        valuesToHeaders.remove(CSVHeaders.COUNTRY_HEADER_NAME);
        Double latitude = Double.valueOf(valuesToHeaders.get(CSVHeaders.LATITUDE_HEADER_NAME));
        valuesToHeaders.remove(CSVHeaders.LATITUDE_HEADER_NAME);
        Double longitude = Double.valueOf(valuesToHeaders.get(CSVHeaders.LONGITUDE_HEADER_NAME));
        valuesToHeaders.remove(CSVHeaders.LONGITUDE_HEADER_NAME);

        Map<LocalDate, Integer> casesPerDate = valuesToHeaders.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> LocalDate.parse(entry.getKey(),
                                DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.ENGLISH)),
                        entry -> Integer.valueOf(entry.getValue())
                ));

        return CSVCoronavirusDataItem.builder()
                .country(countryName)
                .state(stateName)
                .latitude(latitude)
                .longitude(longitude)
                .casesPerDate(casesPerDate)
                .build();
    }
}
