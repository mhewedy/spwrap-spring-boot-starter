package spwrap;

import org.junit.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

public class SpwrapAutoConfigurationTests {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(DataSourceAutoConfiguration.class,
                    SpwrapAutoConfiguration.class));

    @Test
    public void testBasicSetup() {
        contextRunner
                .run(context -> assertThat(context).hasSingleBean(DAO.class));
    }

    @Test
    public void backoffWhenNoDataSourceFound() {
        contextRunner.withClassLoader(new FilteredClassLoader(DataSource.class))
                .run(context -> assertThat(context).doesNotHaveBean(DAO.class));
    }

    @Test
    public void backoffWhenNoDAOFound() {
        contextRunner.withClassLoader(new FilteredClassLoader(DAO.class))
                .run(context -> assertThat(context).doesNotHaveBean(DAO.class));
    }

    @Test
    public void backoffWhenItIsDisabled() {
        contextRunner.withPropertyValues("spwrap.enabled=false")
                .run(context -> assertThat(context).doesNotHaveBean(DAO.class));
    }

    @Test
    public void backoffWhenDAOBeanFound() {
        contextRunner.withUserConfiguration(MyConfig.class)
                .run(context -> {
                    assertThat(context).hasSingleBean(DAO.class);
                    assertThat(context.getBean("myDaoInMyConfig")).isNotNull();
                });
    }

    static class MyConfig {
        @Bean("myDaoInMyConfig")
        public DAO dao(DataSource dataSource) {
            return new DAO.Builder(dataSource)
                    .config(new spwrap.Config().useStatusFields(false))
                    .build();
        }
    }
}
