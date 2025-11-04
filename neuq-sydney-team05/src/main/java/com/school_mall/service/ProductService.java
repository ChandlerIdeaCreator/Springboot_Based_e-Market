package com.school_mall.service;

import com.school_mall.entity.Product;
import com.school_mall.repository.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Product service implementation
 */
@Service
public class ProductService extends BaseServiceImpl<Product, ProductMapper> {
    
    @Autowired
    private ProductMapper productMapper;
    
    /**
     * Get products by shop type
     */
    public List<Product> getByShopType(Integer shopType) {
        return getAll().stream()
                .filter(product -> shopType.equals(product.getShopType()))
                .toList();
    }
    
    /**
     * Get products by quality check status
     */
    public List<Product> getByQualityCheck(Integer qualityCheck) {
        return getAll().stream()
                .filter(product -> qualityCheck.equals(product.getQualityCheck()))
                .toList();
    }
    
    /**
     * Search products by name
     */
    public List<Product> searchByName(String name) {
        return getAll().stream()
                .filter(product -> product.getProductName().contains(name))
                .toList();
    }
} 