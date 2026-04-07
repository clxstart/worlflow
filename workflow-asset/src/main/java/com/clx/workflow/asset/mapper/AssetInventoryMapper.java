package com.clx.workflow.asset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.clx.workflow.asset.domain.AssetInventory;
import org.apache.ibatis.annotations.Mapper;

/**
 * 资产盘点Mapper接口
 *
 * @author clx
 */
@Mapper
public interface AssetInventoryMapper extends BaseMapper<AssetInventory> {

}