package com.ct.dataprovider.db.repository;

import com.ct.entitycommon.entity.CasesPerState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CasesPerStateRepository extends JpaRepository<CasesPerState, Long> {

}
