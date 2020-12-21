package com.ct.datasync.dao;

import com.ct.datasync.repository.CasesPerCountryRepository;
import com.ct.datasync.repository.CasesPerStateRepository;
import com.ct.datasync.repository.CountryRepository;
import com.ct.datasync.repository.StateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
public class CoronavirusDataStore implements DataStore {

    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final CasesPerCountryRepository casesPerCountryRepository;
    private final CasesPerStateRepository casesPerStateRepository;

    public CoronavirusDataStore(CountryRepository countryRepository,
                                StateRepository stateRepository,
                                CasesPerCountryRepository casesPerCountryRepository,
                                CasesPerStateRepository casesPerStateRepository) {
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.casesPerCountryRepository = casesPerCountryRepository;
        this.casesPerStateRepository = casesPerStateRepository;
    }

    @Override
    @Transactional
    public void reInsertData(CoronavirusEntityData coronavirusEntityData) {
        deleteAll();
        saveAll(coronavirusEntityData);
    }

    @Override
    public void saveAll(CoronavirusEntityData coronavirusEntityData) {
        log.info("Saving coronavirus data...");
        countryRepository.saveAll(coronavirusEntityData.getCountries());
        stateRepository.saveAll(coronavirusEntityData.getStates());
        casesPerCountryRepository.saveAll(coronavirusEntityData.getCasesPerCountry());
        casesPerStateRepository.saveAll(coronavirusEntityData.getCasesPerState());
        log.info("Finished saving data");
    }

    @Override
    public void deleteAll() {
        log.info("Erasing all related data...");
        casesPerCountryRepository.deleteAllInBatch();
        casesPerStateRepository.deleteAllInBatch();
        stateRepository.deleteAllInBatch();
        countryRepository.deleteAllInBatch();
        log.info("Finished erasing the data...");
    }
}
