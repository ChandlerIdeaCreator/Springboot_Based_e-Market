package com.school_mall.service;

import com.school_mall.entity.ProductStyle;
import com.school_mall.repository.ProductStyleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Product style service for complex business operations
 */
@Service
public class ProductStyleService extends BaseServiceImpl<ProductStyle, ProductStyleMapper> {
    
    @Autowired
    private ProductStyleMapper styleMapper;
    
    /**
     * Get styles by product ID
     */
    public List<ProductStyle> getByProductId(Long productId) {
        return styleMapper.selectByProductId(productId);
    }
    
    /**
     * Search styles by name
     */
    public List<ProductStyle> searchByStyleName(String styleName) {
        return styleMapper.selectByStyleName(styleName);
    }
    
    /**
     * Get specific style for product
     */
    public ProductStyle getProductStyle(Long productId, String styleName) {
        return styleMapper.selectByProductIdAndStyleName(productId, styleName);
    }
    
    /**
     * Get style count for product
     */
    public int getStyleCount(Long productId) {
        return styleMapper.countByProductId(productId);
    }
    
    /**
     * Batch create styles for product
     */
    public List<ProductStyle> batchCreateStyles(List<ProductStyle> styles) {
        if (styles != null && !styles.isEmpty()) {
            styleMapper.batchInsert(styles);
        }
        return styles;
    }
    
    /**
     * Check if style exists for product
     */
    public boolean styleExists(Long productId, String styleName) {
        return getProductStyle(productId, styleName) != null;
    }
    
    /**
     * Add style with validation
     */
    public ProductStyle addStyle(ProductStyle style) {
        if (styleExists(style.getProductId(), style.getStyleName())) {
            throw new RuntimeException("Style already exists for this product");
        }
        return insert(style);
    }
    
    /**
     * Update or create style
     */
    public ProductStyle saveStyle(Long productId, String styleName) {
        ProductStyle existing = getProductStyle(productId, styleName);
        if (existing != null) {
            return existing;
        }
        
        ProductStyle newStyle = new ProductStyle();
        newStyle.setProductId(productId);
        newStyle.setStyleName(styleName);
        return insert(newStyle);
    }
    
    /**
     * Delete all styles for product
     */
    public boolean deleteByProductId(Long productId) {
        return styleMapper.deleteByProductId(productId) > 0;
    }
    
    /**
     * Delete specific style for product
     */
    public boolean deleteProductStyle(Long productId, String styleName) {
        return styleMapper.deleteByProductIdAndStyleName(productId, styleName) > 0;
    }
    
    /**
     * Get available styles as string list
     */
    public List<String> getAvailableStyleNames(Long productId) {
        return getByProductId(productId).stream()
                .map(ProductStyle::getStyleName)
                .toList();
    }
} 