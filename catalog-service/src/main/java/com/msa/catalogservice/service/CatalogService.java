package com.msa.catalogservice.service;

import com.msa.catalogservice.entity.CatalogEntity;
import org.springframework.stereotype.Service;

public interface CatalogService {
    Iterable<CatalogEntity> getAllCatalog();
}
