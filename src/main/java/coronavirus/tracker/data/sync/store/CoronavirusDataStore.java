package coronavirus.tracker.data.sync.store;

import coronavirus.tracker.data.sync.repository.CountryCasesPerDateRepository;
import coronavirus.tracker.data.sync.repository.StateCasesPerDateRepository;
import coronavirus.tracker.data.sync.repository.CountryRepository;
import coronavirus.tracker.data.sync.repository.StateRepository;
import coronavirus.tracker.entitycommon.entity.Country;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

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
        System.out.println(coronavirusEntityData.getCountries().stream().map(Country::getCountryName).sorted().collect(Collectors.joining(",")));
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
