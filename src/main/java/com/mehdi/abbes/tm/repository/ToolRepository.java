package com.mehdi.abbes.tm.repository;

import com.mehdi.abbes.tm.domain.ToolDocument;
import java.math.BigInteger;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepository extends PagingAndSortingRepository<ToolDocument, BigInteger> {

    List<com.mehdi.abbes.tm.domain.ToolDocument> findAll();
}
