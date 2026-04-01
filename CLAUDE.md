# CLAUDE.md

## 项目概述

WorkFlow 是一个基于 Spring Boot 3.0.2 的工作流项目，使用 Java 17 开发。

## 技术栈

- **Java**: 17
- **Spring Boot**: 3.0.2
- **构建工具**: Maven

## 项目结构

```
src/main/java/com/clx/workflow/
├── WorkFlowApplication.java    # 主应用入口
└── demos/web/
    ├── BasicController.java    # 基本 REST 控制器
    ├── PathVariableController.java  # 路径变量控制器
    └── User.java               # 用户实体类
```

## 构建命令

```bash
# 编译项目
mvn compile

# 运行测试
mvn test

# 打包项目
mvn package

# 运行应用
mvn spring-boot:run
```

## API 端点

| 端点 | 方法 | 描述 |
|------|------|------|
| `/hello?name=xxx` | GET | 返回问候语 |
| `/user` | GET | 返回用户对象 (JSON) |
| `/save_user?name=xxx&age=xx` | GET | 保存用户信息 |
| `/html` | GET | 返回静态页面 |
| `/user/{userId}/roles/{roleId}` | GET | 获取用户角色 |
| `/javabeat/{regexp1:[a-z-]+}` | GET | 正则路径匹配示例 |

## 开发约定

- 包名前缀: `com.clx.workflow`
- 控制器放置于 `demos/web` 目录
- 使用 Apache License 2.0 许可证头
- 默认端口: 8080

## 测试

测试类位于 `src/test/java/com/clx/workflow/`，使用 Spring Boot Test 框架。