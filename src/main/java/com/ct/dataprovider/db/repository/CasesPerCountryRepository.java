package com.ct.dataprovider.db.repository;

import com.ct.entitycommon.entity.CasesPerCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CasesPerCountryRepository extends JpaRepository<CasesPerCountry, Long> {

}
