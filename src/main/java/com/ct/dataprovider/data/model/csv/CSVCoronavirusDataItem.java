package com.ct.dataprovider.data.model.csv;

import com.ct.dataprovider.data.provider.file.csv.utils.CSVHeaders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class CSVCoronavirusDataItem {

    private String state;
    private String country;
    private Double latitude;
    private Double longitude;
    private Map<LocalDate, Integer> casesPerDate;

    public CSVCoronavirusDataItem(String state, String country,
                                  Double latitude, Double longitude,
                                  Map<LocalDate, Integer> casesPerDate) {
        this.state = state;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.casesPerDate = casesPerDate;
    }

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
                                DateTimeFormatter.ofPattern("M/d/yy", Locale.ENGLISH)),
                        entry -> Integer.valueOf(entry.getValue())
                ));

        return new CSVCoronavirusDataItem(stateName, countryName, latitude, longitude,  casesPerDate);
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Map<LocalDate, Integer> getCasesPerDate() {
        return casesPerDate;
    }

    public void setCasesPerDate(Map<LocalDate, Integer> casesPerDate) {
        this.casesPerDate = casesPerDate;
    }
}
