package com.ct.dataprovider.repository;

import com.ct.entitycommon.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

}
