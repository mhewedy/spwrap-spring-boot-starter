package spwrap;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ConditionalOnClass(DAO.class)
@ConditionalOnBean(DataSource.class)
@ConditionalOnMissingBean(DAO.class)
@EnableConfigurationProperties(SpwrapProperties.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@ConditionalOnProperty(prefix = "spwrap", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SpwrapAutoConfiguration {

    @Bean
    public DAO dao(DataSource dataSource, SpwrapProperties properties) {
        return new DAO.Builder(dataSource)
                .config(new spwrap.Config()
                        .useStatusFields(properties.isUseStatusFields())
                        .successCode(properties.getSuccessCode()))
                .build();
    }
}
