package com.ct.dataprovider.db;

import com.ct.dataprovider.db.repository.CasesPerCountryRepository;
import com.ct.dataprovider.db.repository.CasesPerStateRepository;
import com.ct.dataprovider.db.repository.CountryRepository;
import com.ct.dataprovider.db.repository.StateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CoronavirusDataStore implements DataStore {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoronavirusDataStore.class);


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
        eraseData();
        storeDataAsBatch(coronavirusEntityData);
    }

    @Override
    public void storeDataAsBatch(CoronavirusEntityData coronavirusEntityData) {
        LOGGER.info("Saving coronavirus data...");
        countryRepository.saveAll(coronavirusEntityData.getCountries());
        stateRepository.saveAll(coronavirusEntityData.getStates());
        casesPerCountryRepository.saveAll(coronavirusEntityData.getCasesPerCountry());
        casesPerStateRepository.saveAll(coronavirusEntityData.getCasesPerState());
        LOGGER.info("Finished saving data");
    }

    @Override
    public void eraseData() {
        LOGGER.info("Erasing all related data...");
        casesPerCountryRepository.deleteAllInBatch();
        casesPerStateRepository.deleteAllInBatch();
        stateRepository.deleteAllInBatch();
        countryRepository.deleteAllInBatch();
        LOGGER.info("Finished erasing the data...");
    }
}
