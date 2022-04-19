# spwrap spring-boot starter

spring-boot starter for [spwrap](https://github.com/mhewedy/spwrap)

```java

@Configuration
public class Config {

    // DAO bean will be defined by the starter with default properties 
    // you can override the properties using `spwrap.` prefixed properties
    @Bean
    public CustomerDAO customerDAO(DAO dao) {
        return dao.create(CustomerDAO.class);
    }
}
```
