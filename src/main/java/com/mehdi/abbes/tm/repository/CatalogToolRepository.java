package com.mehdi.abbes.tm.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.mehdi.abbes.tm.domain.CatalogTool;

@Repository
public interface CatalogToolRepository extends
		PagingAndSortingRepository<CatalogTool, String> {

	@Override
	List<CatalogTool> findAll();

}
