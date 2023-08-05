package com.msa.catalogservice.service;

import com.msa.catalogservice.entity.CatalogEntity;
import com.msa.catalogservice.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository catalogRepository;
    @Override
    public Iterable<CatalogEntity> getAllCatalog() {
        return catalogRepository.findAll();
    }
}
