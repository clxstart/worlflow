# CLAUDE.md

## 项目概述

WorkFlow 是一个基于 Spring Boot 2.7.18 的工作流项目，使用 Java 8 开发，参考 pmhub 项目学习构建。

## 技术栈

- **Java**: 1.8
- **Spring Boot**: 2.7.18
- **构建工具**: Maven
- **ORM**: MyBatis-Plus 3.5.3.1
- **缓存**: Redis
- **认证**: Spring Security + JWT
- **数据库**: MySQL 8.0

---

## 像人一样写代码 - 开发流程指南

### 1. 开发顺序

按照依赖关系，从底层到上层：

```
配置依赖(pom.xml)
      ↓
通用工具类(common/utils)
      ↓
基础配置(config)
      ↓
实体类(domain/entity)
      ↓
数据访问层(mapper)
      ↓
业务逻辑层(service)
      ↓
控制器层(controller)
```

### 2. 每个模块的开发步骤

**以用户模块为例：**

```
第1步：创建实体类 SysUser.java
       - 定义字段，继承 BaseEntity
       - 添加校验注解

第2步：创建 Mapper 接口
       - 继承 BaseMapper<SysUser>
       - 无需写 SQL，MyBatis-Plus 自动提供 CRUD

第3步：创建 Service 接口
       - 继承 IService<SysUser>
       - 定义业务方法

第4步：创建 Service 实现
       - 继承 ServiceImpl<SysUserMapper, SysUser>
       - 实现业务逻辑

第5步：创建 Controller
       - 继承 BaseController
       - 注入 Service
       - 定义 REST 接口
```

### 3. 基础设施先行

在开发业务模块前，先准备好：

| 顺序 | 类/配置 | 作用 |
|------|---------|------|
| 1 | HttpStatus.java | HTTP 状态码常量 |
| 2 | StringUtils.java | 字符串工具类 |
| 3 | AjaxResult.java | 统一响应格式 |
| 4 | BaseEntity.java | 基础实体（自动填充字段） |
| 5 | BaseController.java | 基础控制器（通用方法） |
| 6 | GlobalExceptionHandler.java | 全局异常处理 |
| 7 | MybatisPlusConfig.java | 分页插件配置 |
| 8 | RedisConfig.java | Redis 序列化配置 |

### 4. 新功能开发流程

**示例：添加角色管理模块**

```
1. 先检查是否有依赖的其他模块
   - 角色需要关联用户 → 确认用户模块已完成

2. 创建数据库表
   - 编写 SQL 文件: sql/sys_role.sql
   - 执行建表语句

3. 按分层顺序创建代码
   - domain/SysRole.java
   - mapper/SysRoleMapper.java
   - service/ISysRoleService.java
   - service/impl/SysRoleServiceImpl.java
   - controller/SysRoleController.java

4. 测试验证
   - 启动项目
   - 测试 CRUD 接口
```

### 5. 遇到依赖时的处理

**场景：开发 TokenService 需要 RedisCache**

```
❌ 错误做法：直接复制所有相关代码

✅ 正确做法：
   1. 先创建 RedisConfig.java（配置 RedisTemplate）
   2. 再创建 RedisCache.java（封装 Redis 操作）
   3. 最后创建 TokenService.java（使用 RedisCache）

每创建一个类，确保它依赖的类已经存在。
```

### 6. 配置文件同步

创建新功能时，同步更新：

```
application.yml    → 添加新配置项
pom.xml           → 添加新依赖
```

---

## 构建命令

```bash
# 编译项目
mvn clean compile

# 运行应用
mvn spring-boot:run

# 打包项目
mvn clean package -DskipTests

# 运行测试
mvn test
```

---

## API 接口

### 公开接口（无需认证）

| 端点 | 方法 | 描述 |
|------|------|------|
| `/login` | POST | 登录获取 Token |
| `/hello` | GET | 测试接口 |

### 认证接口（需要 Token）

| 端点 | 方法 | 描述 |
|------|------|------|
| `/logout` | POST | 登出 |
| `/system/user/list` | GET | 用户列表 |
| `/system/user/{id}` | GET | 用户详情 |
| `/system/user` | POST | 新增用户 |
| `/system/user` | PUT | 修改用户 |
| `/system/user/{ids}` | DELETE | 删除用户 |

### 请求示例

**登录：**
```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"123456"}'
```

**访问受保护接口：**
```bash
curl http://localhost:8080/system/user/list \
  -H "Authorization: Bearer {token}"
```

---

## 开发约定

- **包名前缀**: `com.clx.workflow`
- **实体类**: 放在 `domain` 目录，继承 `BaseEntity`
- **Mapper**: 放在 `mapper` 目录，继承 `BaseMapper<T>`
- **Service**: 放在 `service` 目录，接口以 `I` 开头
- **Controller**: 放在 `controller` 目录，继承 `BaseController`
- **统一响应**: 使用 `AjaxResult` 返回
- **异常处理**: 抛出 `ServiceException`
- **默认端口**: 8080

一、人事管理
- 用户管理：员工信息录入、编辑、离职处理
- 部门管理：组织架构配置、层级关系
- 岗位管理：岗位定义、岗位职责
- 角色权限：岗位权限分配、数据权限
- 考勤管理：上下班打卡、考勤统计、异常处理
- 请假管理：请假申请、审批流程、假期余额
- 薪资管理：薪资核算、工资条生成、绩效奖金

二、项目管理
- 项目创建：项目立项、基本信息配置
- 任务管理：任务分配、进度跟踪、优先级
- 项目概览：项目统计、进度可视化
- 里程碑管理：关键节点设置、达成记录

三、工作流审批
- 流程设计：BPMN流程图设计
- 表单设计：自定义审批表单
- 流程发起：请假、报销、采购等审批发起
- 流程审批：多级审批、流转记录
- 流程监控：待办事项、已办事项

四、财务管理
- 收支管理：收入登记、支出记录、分类统计
- 报销管理：报销申请、审批、支付记录
- 发票管理：发票录入、查验、归档
- 财务报表：月度/季度/年度报表生成
- 预算管理：预算编制、执行监控

五、客户管理(CRM)
- 客户信息：客户档案、联系人管理
- 销售跟进：拜访记录、跟进状态
- 合同管理：合同签订、履约跟踪
- 销售统计：销售业绩、转化率分析

六、资产管理
- 资产登记：设备、办公用品入库登记
- 资产领用：领用申请、归还记录
- 资产盘点：定期盘点、差异处理
- 资产折旧：折旧计算、资产报废

七、文档管理
- 文件存储：文件上传、分类存储
- 知识库：公司制度、操作手册
- 文档共享：权限控制、在线预览
- 版本管理：文档版本记录

八、系统运维
- 通知公告：公司公告发布
- 操作日志：操作记录查询
- 定时任务：任务调度管理
- 代码生成：快速开发辅助
- 服务监控：系统运行状态
- 缓存管理：Redis缓存监控