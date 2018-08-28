package dependencyInversion.context;

/**
 * Created by Iana_Kasimova on 28-Aug-18.
 */
@Configuration
public class AppConfiguration {

    @Bean
    public static PropertyPlaceConfigurer propertyPlaceConfigurer(){
        PropertyPlaceConfigurer placeConfigurer = new PropertyPlaceConfigurer();
        placeConfigurer.setProperties("/configuration.properties");
        return placeConfigurer;
    }
}
