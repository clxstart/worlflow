package com.clx.workflow.crm.controller;

import com.clx.workflow.common.core.controller.BaseController;
import com.clx.workflow.common.core.domain.AjaxResult;
import com.clx.workflow.crm.domain.CrmContract;
import com.clx.workflow.crm.domain.CrmContractProduct;
import com.clx.workflow.crm.service.ICrmContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 合同 Controller
 *
 * @author clx
 */
@RestController
@RequestMapping("/crm/contract")
public class CrmContractController extends BaseController {

    @Autowired
    private ICrmContractService contractService;

    /**
     * 查询合同列表
     */
    @PreAuthorize("@ss.hasPermi('crm:contract:list')")
    @GetMapping("/list")
    public AjaxResult list(CrmContract contract) {
        List<CrmContract> list = contractService.selectContractList(contract);
        return success(list);
    }

    /**
     * 查询合同详情（含产品明细）
     */
    @PreAuthorize("@ss.hasPermi('crm:contract:query')")
    @GetMapping("/{contractId}")
    public AjaxResult getInfo(@PathVariable Long contractId) {
        CrmContract contract = contractService.getById(contractId);
        List<CrmContractProduct> products = contractService.selectProductsByContractId(contractId);
        return success().put("contract", contract).put("products", products);
    }

    /**
     * 查询合同产品明细
     */
    @PreAuthorize("@ss.hasPermi('crm:contract:query')")
    @GetMapping("/products/{contractId}")
    public AjaxResult getProducts(@PathVariable Long contractId) {
        return success(contractService.selectProductsByContractId(contractId));
    }

    /**
     * 新增合同
     */
    @PreAuthorize("@ss.hasPermi('crm:contract:add')")
    @PostMapping
    public AjaxResult add(@Validated @RequestBody CrmContract contract) {
        contract.setOwnerId(getUserId());
        return toAjax(contractService.save(contract));
    }

    /**
     * 创建合同（含产品明细）
     */
    @PreAuthorize("@ss.hasPermi('crm:contract:add')")
    @PostMapping("/create")
    public AjaxResult create(@RequestBody ContractVO vo) {
        vo.getContract().setOwnerId(getUserId());
        return toAjax(contractService.createContract(vo.getContract(), vo.getProducts()));
    }

    /**
     * 修改合同
     */
    @PreAuthorize("@ss.hasPermi('crm:contract:edit')")
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody CrmContract contract) {
        return toAjax(contractService.updateById(contract));
    }

    /**
     * 提交审批
     */
    @PreAuthorize("@ss.hasPermi('crm:contract:approve')")
    @PutMapping("/submit/{contractId}")
    public AjaxResult submit(@PathVariable Long contractId) {
        return toAjax(contractService.submitApproval(contractId));
    }

    /**
     * 审批合同
     */
    @PreAuthorize("@ss.hasPermi('crm:contract:approve')")
    @PutMapping("/approve/{contractId}")
    public AjaxResult approve(@PathVariable Long contractId, @RequestParam String status) {
        return toAjax(contractService.approveContract(contractId, status, getUserId()));
    }

    /**
     * 作废合同
     */
    @PreAuthorize("@ss.hasPermi('crm:contract:edit')")
    @PutMapping("/cancel/{contractId}")
    public AjaxResult cancel(@PathVariable Long contractId) {
        return toAjax(contractService.cancelContract(contractId));
    }

    /**
     * 删除合同
     */
    @PreAuthorize("@ss.hasPermi('crm:contract:remove')")
    @DeleteMapping("/{contractIds}")
    public AjaxResult remove(@PathVariable Long[] contractIds) {
        return toAjax(contractService.removeByIds(java.util.Arrays.asList(contractIds)));
    }

    /**
     * 合同统计
     */
    @PreAuthorize("@ss.hasPermi('crm:contract:query')")
    @GetMapping("/statistics")
    public AjaxResult statistics() {
        Map<String, Object> stats = contractService.contractStatistics(getUserId());
        return success(stats);
    }

    /**
     * 合同VO
     */
    public static class ContractVO {
        private CrmContract contract;
        private List<CrmContractProduct> products;

        public CrmContract getContract() {
            return contract;
        }

        public void setContract(CrmContract contract) {
            this.contract = contract;
        }

        public List<CrmContractProduct> getProducts() {
            return products;
        }

        public void setProducts(List<CrmContractProduct> products) {
            this.products = products;
        }
    }
}