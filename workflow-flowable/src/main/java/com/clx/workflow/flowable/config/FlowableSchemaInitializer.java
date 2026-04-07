package com.clx.workflow.flowable.config;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Flowable 数据库初始化器
 * 在应用启动时检查并创建Flowable表
 *
 * @author clx
 */
@Configuration
public class FlowableSchemaInitializer {

    private static final Logger logger = LoggerFactory.getLogger(FlowableSchemaInitializer.class);

    @Autowired
    private DataSource dataSource;

    /**
     * 优先级最高的初始化器，在所有其他Bean之前执行
     * 确保Flowable表在引擎初始化之前就存在
     */
    @Bean
    @Order(1)
    public CommandLineRunner initFlowableSchema() {
        return args -> {
            try (Connection connection = dataSource.getConnection()) {
                // 检查ACT_GE_PROPERTY表是否存在
                ResultSet tables = connection.getMetaData().getTables(null, null, "ACT_GE_PROPERTY", null);
                if (!tables.next()) {
                    logger.info("========================================");
                    logger.info("Flowable表不存在，开始自动创建...");
                    logger.info("========================================");

                    // 使用ProcessEngineConfiguration来创建表
                    SpringProcessEngineConfiguration config = new SpringProcessEngineConfiguration();
                    config.setDataSource(dataSource);
                    config.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
                    config.setDatabaseType("mysql");
                    config.setAsyncExecutorActivate(false);

                    // 构建ProcessEngine会自动创建表
                    ProcessEngine processEngine = config.buildProcessEngine();
                    processEngine.close();

                    logger.info("========================================");
                    logger.info("Flowable表创建成功！");
                    logger.info("========================================");
                } else {
                    logger.info("Flowable表已存在，跳过创建");
                }
                tables.close();
            } catch (Exception e) {
                logger.error("初始化Flowable表失败: {}", e.getMessage());
                throw e;
            }
        };
    }
}