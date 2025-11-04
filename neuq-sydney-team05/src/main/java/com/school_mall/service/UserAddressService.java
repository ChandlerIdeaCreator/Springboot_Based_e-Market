package com.school_mall.service;

import com.school_mall.entity.UserAddress;
import com.school_mall.repository.UserAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User address service for complex business operations
 */
@Service
public class UserAddressService extends BaseServiceImpl<UserAddress, UserAddressMapper> {
    
    @Autowired
    private UserAddressMapper addressMapper;
    
    /**
     * Get addresses by user ID
     */
    public List<UserAddress> getByUserId(Long userId) {
        return addressMapper.selectByUserId(userId);
    }
    
    /**
     * Get default address for user
     */
    public UserAddress getDefaultAddress(Long userId) {
        return addressMapper.selectDefaultByUserId(userId);
    }
    
    /**
     * Search addresses by region
     */
    public List<UserAddress> searchByRegion(String region) {
        return addressMapper.selectByRegion(region);
    }
    
    /**
     * Get user addresses in specific region
     */
    public List<UserAddress> getUserAddressesInRegion(Long userId, String region) {
        return addressMapper.selectByUserIdAndRegion(userId, region);
    }
    
    /**
     * Search addresses by phone
     */
    public List<UserAddress> getByPhone(String phone) {
        return addressMapper.selectByPhone(phone);
    }
    
    /**
     * Get address count for user
     */
    public int getAddressCount(Long userId) {
        return addressMapper.countByUserId(userId);
    }
    
    /**
     * Check if user has default address
     */
    public boolean hasDefaultAddress(Long userId) {
        return addressMapper.hasDefaultAddress(userId);
    }
    
    /**
     * Add address with business validation
     */
    @Transactional
    public UserAddress addAddress(UserAddress address) {
        if (getAddressCount(address.getUserId()) >= 10) {
            throw new RuntimeException("Maximum 10 addresses allowed per user");
        }
        
        if (!hasDefaultAddress(address.getUserId()) || address.getIsDefault() == 1) {
            if (address.getIsDefault() == 1) {
                clearDefaultAddress(address.getUserId());
            }
            address.setIsDefault(1);
        } else {
            address.setIsDefault(0);
        }
        
        return insert(address);
    }
    
    /**
     * Set address as default
     */
    @Transactional
    public boolean setAsDefault(Long userId, Long addressId) {
        clearDefaultAddress(userId);
        return addressMapper.setAsDefault(addressId, userId) > 0;
    }
    
    /**
     * Clear default address for user
     */
    public boolean clearDefaultAddress(Long userId) {
        return addressMapper.clearDefaultByUserId(userId) > 0;
    }
    
    /**
     * Update address with business logic
     */
    @Transactional
    public UserAddress updateAddress(UserAddress address) {
        if (address.getIsDefault() == 1) {
            clearDefaultAddress(address.getUserId());
        }
        return update(address);
    }
    
    /**
     * Delete address with validation
     */
    @Transactional
    public boolean deleteAddress(Long addressId, Long userId) {
        UserAddress address = getById(addressId);
        if (address == null || !address.getUserId().equals(userId)) {
            throw new RuntimeException("Address not found or access denied");
        }
        
        boolean isDefault = address.getIsDefault() == 1;
        boolean deleted = deleteById(addressId);
        
        if (deleted && isDefault) {
            List<UserAddress> remaining = getByUserId(userId);
            if (!remaining.isEmpty()) {
                setAsDefault(userId, remaining.get(0).getId());
            }
        }
        
        return deleted;
    }
    
    /**
     * Delete all addresses for user
     */
    public boolean deleteAllByUserId(Long userId) {
        return addressMapper.deleteByUserId(userId) > 0;
    }
    
    /**
     * Delete non-default addresses for user
     */
    public boolean deleteNonDefaultAddresses(Long userId) {
        return addressMapper.deleteNonDefaultByUserId(userId) > 0;
    }
    
    /**
     * Get full address string
     */
    public String getFullAddress(UserAddress address) {
        return String.format("%s %s (%s %s)", 
                address.getRegion(), 
                address.getDetailAddress(),
                address.getReceiver(),
                address.getPhone());
    }
} 