package com.school_mall.repository;

import com.school_mall.entity.UserAddress;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * User address repository interface
 */
@Mapper
public interface UserAddressMapper extends BaseMapper<UserAddress> {
    
    /**
     * Select address by ID
     */
    UserAddress selectById(@Param("id") Long id);
    
    /**
     * Select all addresses
     */
    List<UserAddress> selectAll();
    
    /**
     * Select addresses by user ID
     */
    List<UserAddress> selectByUserId(@Param("userId") Long userId);
    
    /**
     * Select default address by user ID
     */
    UserAddress selectDefaultByUserId(@Param("userId") Long userId);
    
    /**
     * Search addresses by region
     */
    List<UserAddress> selectByRegion(@Param("region") String region);
    
    /**
     * Get user addresses in specific region
     */
    List<UserAddress> selectByUserIdAndRegion(@Param("userId") Long userId,
                                              @Param("region") String region);
    
    /**
     * Search addresses by phone
     */
    List<UserAddress> selectByPhone(@Param("phone") String phone);
    
    /**
     * Get address count for user
     */
    int countByUserId(@Param("userId") Long userId);
    
    /**
     * Check if user has default address
     */
    boolean hasDefaultAddress(@Param("userId") Long userId);
    
    /**
     * Insert address
     */
    int insert(UserAddress address);
    
    /**
     * Insert address selectively
     */
    int insertSelective(UserAddress address);
    
    /**
     * Update address by ID
     */
    int updateById(UserAddress address);
    
    /**
     * Update address by ID selectively
     */
    int updateByIdSelective(UserAddress address);
    
    /**
     * Clear default address for user
     */
    int clearDefaultByUserId(@Param("userId") Long userId);
    
    /**
     * Set address as default
     */
    int setAsDefault(@Param("addressId") Long addressId, @Param("userId") Long userId);
    
    /**
     * Delete address by ID
     */
    int deleteById(@Param("id") Long id);
    
    /**
     * Delete all addresses for user
     */
    int deleteByUserId(@Param("userId") Long userId);
    
    /**
     * Delete non-default addresses for user
     */
    int deleteNonDefaultByUserId(@Param("userId") Long userId);
} 