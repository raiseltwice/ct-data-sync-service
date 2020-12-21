package com.ct.dataprovider.repository;

import com.ct.entitycommon.entity.StateCasesPerDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CasesPerStateRepository extends JpaRepository<StateCasesPerDate, Long> {

}
