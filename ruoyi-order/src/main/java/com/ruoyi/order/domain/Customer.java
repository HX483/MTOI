package com.ruoyi.order.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 顾客对象 customer
 * 
 * @author xihao
 * @date 2025-11-30
 */
public class Customer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 顾客ID */
    private Long customerId;

    /** 顾客姓名 */
    @Excel(name = "顾客姓名")
    private String customerName;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String phone;

    /** 头像 */
    @Excel(name = "头像")
    private String avatar;

    /** 会员等级 */
    @Excel(name = "会员等级")
    private Integer memberLevel;

    /** 积分 */
    @Excel(name = "积分")
    private Long points;

    /** 总消费金额 */
    @Excel(name = "总消费金额")
    private BigDecimal totalConsume;

    public void setCustomerId(Long customerId) 
    {
        this.customerId = customerId;
    }

    public Long getCustomerId() 
    {
        return customerId;
    }

    public void setCustomerName(String customerName) 
    {
        this.customerName = customerName;
    }

    public String getCustomerName() 
    {
        return customerName;
    }

    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }

    public void setAvatar(String avatar) 
    {
        this.avatar = avatar;
    }

    public String getAvatar() 
    {
        return avatar;
    }

    public void setMemberLevel(Integer memberLevel) 
    {
        this.memberLevel = memberLevel;
    }

    public Integer getMemberLevel() 
    {
        return memberLevel;
    }

    public void setPoints(Long points) 
    {
        this.points = points;
    }

    public Long getPoints() 
    {
        return points;
    }

    public void setTotalConsume(BigDecimal totalConsume) 
    {
        this.totalConsume = totalConsume;
    }

    public BigDecimal getTotalConsume() 
    {
        return totalConsume;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("customerId", getCustomerId())
            .append("customerName", getCustomerName())
            .append("phone", getPhone())
            .append("avatar", getAvatar())
            .append("memberLevel", getMemberLevel())
            .append("points", getPoints())
            .append("totalConsume", getTotalConsume())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
