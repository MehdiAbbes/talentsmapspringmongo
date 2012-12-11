package com.mehdi.abbes.tm.repository;

import com.mehdi.abbes.tm.domain.SkillDocument;
import java.math.BigInteger;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends PagingAndSortingRepository<SkillDocument, BigInteger> {

    List<com.mehdi.abbes.tm.domain.SkillDocument> findAll();
}
