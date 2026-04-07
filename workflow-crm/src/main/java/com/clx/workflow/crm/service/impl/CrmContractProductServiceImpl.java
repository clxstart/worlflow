package com.clx.workflow.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.crm.domain.CrmContractProduct;
import com.clx.workflow.crm.mapper.CrmContractProductMapper;
import com.clx.workflow.crm.service.ICrmContractProductService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 合同产品明细 Service 实现类
 *
 * @author clx
 */
@Service
public class CrmContractProductServiceImpl extends ServiceImpl<CrmContractProductMapper, CrmContractProduct> implements ICrmContractProductService {

    @Override
    public List<CrmContractProduct> selectProductsByContractId(Long contractId) {
        LambdaQueryWrapper<CrmContractProduct> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CrmContractProduct::getContractId, contractId);
        return baseMapper.selectList(wrapper);
    }
}