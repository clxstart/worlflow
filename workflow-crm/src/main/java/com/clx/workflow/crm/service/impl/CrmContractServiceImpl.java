package com.clx.workflow.crm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.clx.workflow.crm.domain.CrmContract;
import com.clx.workflow.crm.domain.CrmContractProduct;
import com.clx.workflow.crm.mapper.CrmContractMapper;
import com.clx.workflow.crm.service.ICrmContractProductService;
import com.clx.workflow.crm.service.ICrmContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 合同 Service 实现类
 *
 * @author clx
 */
@Service
public class CrmContractServiceImpl extends ServiceImpl<CrmContractMapper, CrmContract> implements ICrmContractService {

    @Autowired
    private ICrmContractProductService productService;

    @Override
    public List<CrmContract> selectContractList(CrmContract contract) {
        LambdaQueryWrapper<CrmContract> wrapper = new LambdaQueryWrapper<>();
        if (contract.getContractNo() != null && !contract.getContractNo().isEmpty()) {
            wrapper.like(CrmContract::getContractNo, contract.getContractNo());
        }
        if (contract.getContractName() != null && !contract.getContractName().isEmpty()) {
            wrapper.like(CrmContract::getContractName, contract.getContractName());
        }
        if (contract.getCustomerId() != null) {
            wrapper.eq(CrmContract::getCustomerId, contract.getCustomerId());
        }
        if (contract.getContractType() != null && !contract.getContractType().isEmpty()) {
            wrapper.eq(CrmContract::getContractType, contract.getContractType());
        }
        if (contract.getStatus() != null && !contract.getStatus().isEmpty()) {
            wrapper.eq(CrmContract::getStatus, contract.getStatus());
        }
        if (contract.getOwnerId() != null) {
            wrapper.eq(CrmContract::getOwnerId, contract.getOwnerId());
        }
        wrapper.orderByDesc(CrmContract::getCreateTime);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<CrmContractProduct> selectProductsByContractId(Long contractId) {
        return productService.selectProductsByContractId(contractId);
    }

    @Override
    @Transactional
    public boolean createContract(CrmContract contract, List<CrmContractProduct> products) {
        contract.setStatus("0"); // 草稿
        contract.setReceivedAmount(BigDecimal.ZERO);
        boolean result = save(contract);
        if (result && products != null && !products.isEmpty()) {
            for (CrmContractProduct product : products) {
                product.setContractId(contract.getContractId());
                product.setCreateTime(LocalDateTime.now());
                // 计算金额
                if (product.getPrice() != null && product.getQuantity() != null) {
                    BigDecimal amount = product.getPrice().multiply(new BigDecimal(product.getQuantity()));
                    if (product.getDiscount() != null) {
                        amount = amount.multiply(product.getDiscount()).divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
                    }
                    product.setAmount(amount);
                }
            }
            productService.saveBatch(products);

            // 更新合同总金额
            BigDecimal totalAmount = products.stream()
                    .map(p -> p.getAmount() != null ? p.getAmount() : BigDecimal.ZERO)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            contract.setContractAmount(totalAmount);
            updateById(contract);
        }
        return result;
    }

    @Override
    public boolean submitApproval(Long contractId) {
        LambdaUpdateWrapper<CrmContract> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(CrmContract::getContractId, contractId);
        wrapper.eq(CrmContract::getStatus, "0"); // 只有草稿可以提交
        wrapper.set(CrmContract::getStatus, "1"); // 审批中
        return baseMapper.update(null, wrapper) > 0;
    }

    @Override
    public boolean approveContract(Long contractId, String status, Long approveUserId) {
        LambdaUpdateWrapper<CrmContract> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(CrmContract::getContractId, contractId);
        wrapper.set(CrmContract::getStatus, status);
        wrapper.set(CrmContract::getApproveUserId, approveUserId);
        wrapper.set(CrmContract::getApproveTime, LocalDateTime.now());
        return baseMapper.update(null, wrapper) > 0;
    }

    @Override
    public boolean cancelContract(Long contractId) {
        LambdaUpdateWrapper<CrmContract> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(CrmContract::getContractId, contractId);
        wrapper.set(CrmContract::getStatus, "4"); // 已作废
        return baseMapper.update(null, wrapper) > 0;
    }

    @Override
    public boolean updateReceivedAmount(Long contractId, BigDecimal amount) {
        CrmContract contract = getById(contractId);
        if (contract == null) {
            return false;
        }
        BigDecimal newAmount = contract.getReceivedAmount() != null
                ? contract.getReceivedAmount().add(amount)
                : amount;
        LambdaUpdateWrapper<CrmContract> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(CrmContract::getContractId, contractId);
        wrapper.set(CrmContract::getReceivedAmount, newAmount);
        return baseMapper.update(null, wrapper) > 0;
    }

    @Override
    public Map<String, Object> contractStatistics(Long ownerId) {
        Map<String, Object> result = new HashMap<>();

        LambdaQueryWrapper<CrmContract> baseWrapper = new LambdaQueryWrapper<>();
        if (ownerId != null) {
            baseWrapper.eq(CrmContract::getOwnerId, ownerId);
        }

        // 总合同数
        result.put("total", baseMapper.selectCount(baseWrapper));

        // 按状态统计
        Map<String, Long> statusCount = new HashMap<>();
        for (int i = 0; i <= 4; i++) {
            LambdaQueryWrapper<CrmContract> wrapper = new LambdaQueryWrapper<>();
            if (ownerId != null) {
                wrapper.eq(CrmContract::getOwnerId, ownerId);
            }
            wrapper.eq(CrmContract::getStatus, String.valueOf(i));
            statusCount.put(String.valueOf(i), baseMapper.selectCount(wrapper));
        }
        result.put("statusCount", statusCount);

        // 合同总金额
        LambdaQueryWrapper<CrmContract> amountWrapper = new LambdaQueryWrapper<>();
        if (ownerId != null) {
            amountWrapper.eq(CrmContract::getOwnerId, ownerId);
        }
        amountWrapper.isNotNull(CrmContract::getContractAmount);
        List<CrmContract> contracts = baseMapper.selectList(amountWrapper);
        BigDecimal totalAmount = contracts.stream()
                .map(c -> c.getContractAmount() != null ? c.getContractAmount() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        result.put("totalAmount", totalAmount);

        // 已回款金额
        BigDecimal receivedAmount = contracts.stream()
                .map(c -> c.getReceivedAmount() != null ? c.getReceivedAmount() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        result.put("receivedAmount", receivedAmount);

        return result;
    }
}