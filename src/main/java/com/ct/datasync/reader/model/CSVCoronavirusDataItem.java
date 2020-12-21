package com.ct.datasync.reader.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@Builder
public class CSVCoronavirusDataItem {

    private String stateName;
    private String countryName;
    private Double latitude;
    private Double longitude;
    private Map<LocalDate, Integer> casesPerDate;
}
