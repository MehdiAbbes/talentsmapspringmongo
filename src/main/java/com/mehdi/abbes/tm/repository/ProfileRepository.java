package com.mehdi.abbes.tm.repository;

import com.mehdi.abbes.tm.domain.ProfileDocument;
import java.math.BigInteger;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends PagingAndSortingRepository<ProfileDocument, BigInteger> {

    List<com.mehdi.abbes.tm.domain.ProfileDocument> findAll();
}
