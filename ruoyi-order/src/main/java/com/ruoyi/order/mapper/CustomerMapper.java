package com.ruoyi.order.mapper;

import java.util.List;
import com.ruoyi.order.domain.Customer;

/**
 * 顾客Mapper接口
 * 
 * @author xihao
 * @date 2025-11-30
 */
public interface CustomerMapper 
{
    /**
     * 查询顾客
     * 
     * @param customerId 顾客主键
     * @return 顾客
     */
    public Customer selectCustomerByCustomerId(Long customerId);

    /**
     * 查询顾客列表
     * 
     * @param customer 顾客
     * @return 顾客集合
     */
    public List<Customer> selectCustomerList(Customer customer);

    /**
     * 新增顾客
     * 
     * @param customer 顾客
     * @return 结果
     */
    public int insertCustomer(Customer customer);

    /**
     * 修改顾客
     * 
     * @param customer 顾客
     * @return 结果
     */
    public int updateCustomer(Customer customer);

    /**
     * 删除顾客
     * 
     * @param customerId 顾客主键
     * @return 结果
     */
    public int deleteCustomerByCustomerId(Long customerId);

    /**
     * 批量删除顾客
     * 
     * @param customerIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCustomerByCustomerIds(Long[] customerIds);
}
