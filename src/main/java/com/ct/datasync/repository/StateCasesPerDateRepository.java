package com.ct.datasync.repository;

import com.ct.entitycommon.entity.StateCasesPerDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StateCasesPerDateRepository extends JpaRepository<StateCasesPerDate, Long> {

}
