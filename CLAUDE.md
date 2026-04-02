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

### 核心原则

**不要一次性迁移所有代码，而是按照实际开发的顺序逐步构建。**

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

## 项目结构

```
src/main/java/com/clx/workflow/
├── WorkFlowApplication.java          # 启动类
│
├── common/                           # 通用模块
│   ├── config/                       # 配置类
│   │   ├── MybatisPlusConfig.java
│   │   ├── MyMetaObjectHandler.java
│   │   └── RedisConfig.java
│   ├── constant/                     # 常量
│   │   └── HttpStatus.java
│   ├── core/
│   │   ├── controller/
│   │   │   └── BaseController.java
│   │   ├── domain/
│   │   │   ├── AjaxResult.java
│   │   │   ├── BaseEntity.java
│   │   │   └── model/
│   │   │       ├── LoginBody.java
│   │   │       └── LoginUser.java
│   │   └── redis/
│   │       └── RedisCache.java
│   ├── exception/
│   │   ├── GlobalExceptionHandler.java
│   │   └── ServiceException.java
│   └── utils/
│       └── StringUtils.java
│
├── framework/                        # 框架模块（安全认证）
│   ├── config/
│   │   └── SecurityConfig.java
│   ├── controller/
│   │   └── SysLoginController.java
│   ├── filter/
│   │   └── JwtAuthenticationTokenFilter.java
│   ├── handler/
│   │   └── AuthenticationEntryPointImpl.java
│   └── service/
│       ├── SysLoginService.java
│       ├── TokenService.java
│       └── UserDetailsServiceImpl.java
│
├── system/                           # 系统模块
│   ├── controller/
│   │   └── SysUserController.java
│   ├── domain/
│   │   └── SysUser.java
│   ├── mapper/
│   │   └── SysUserMapper.java
│   └── service/
│       ├── ISysUserService.java
│       └── impl/
│           └── SysUserServiceImpl.java
│
└── resources/
    ├── application.yml               # 主配置
    ├── application-local.yml         # 本地配置
    └── sql/
        └── sys_user.sql              # 建表语句
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


## 参考项目

- **pmhub**: `E:\goodworlk\pmhubd` - Spring Boot 项目管理 + BPM 工作流学习参考