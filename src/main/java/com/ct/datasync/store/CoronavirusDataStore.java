package com.ct.datasync.store;

import com.ct.datasync.repository.CountryCasesPerDateRepository;
import com.ct.datasync.repository.StateCasesPerDateRepository;
import com.ct.datasync.repository.CountryRepository;
import com.ct.datasync.repository.StateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@Transactional
public class CoronavirusDataStore implements DataStore {

    private final CountryRepository countryRepository;
    private final StateRepository stateRepository;
    private final CountryCasesPerDateRepository countryCasesPerDateRepository;
    private final StateCasesPerDateRepository stateCasesPerDateRepository;

    public CoronavirusDataStore(CountryRepository countryRepository,
                                StateRepository stateRepository,
                                CountryCasesPerDateRepository countryCasesPerDateRepository,
                                StateCasesPerDateRepository stateCasesPerDateRepository) {
        this.countryRepository = countryRepository;
        this.stateRepository = stateRepository;
        this.countryCasesPerDateRepository = countryCasesPerDateRepository;
        this.stateCasesPerDateRepository = stateCasesPerDateRepository;
    }

    @Override
    public void reInsertData(CoronavirusEntityData coronavirusEntityData) {
        deleteAll();
        saveAll(coronavirusEntityData);
    }

    @Override
    public void saveAll(CoronavirusEntityData coronavirusEntityData) {
        log.info("Saving coronavirus data...");
        countryRepository.saveAll(coronavirusEntityData.getCountries());
        stateRepository.saveAll(coronavirusEntityData.getStates());
        countryCasesPerDateRepository.saveAll(coronavirusEntityData.getCasesPerCountry());
        stateCasesPerDateRepository.saveAll(coronavirusEntityData.getCasesPerState());
        log.info("Finished saving data");
    }

    @Override
    public void deleteAll() {
        log.info("Erasing all related data...");
        countryCasesPerDateRepository.deleteAllInBatch();
        stateCasesPerDateRepository.deleteAllInBatch();
        stateRepository.deleteAllInBatch();
        countryRepository.deleteAllInBatch();
        log.info("Finished erasing the data...");
    }
}
