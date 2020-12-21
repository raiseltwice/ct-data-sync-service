package com.ct.datasync.repository;

import com.ct.entitycommon.entity.CountryCasesPerDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CasesPerCountryRepository extends JpaRepository<CountryCasesPerDate, Long> {

}
