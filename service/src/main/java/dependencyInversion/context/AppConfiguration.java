package dependencyInversion.context;

import javax.inject.Named;

/**
 * Created by Iana_Kasimova on 28-Aug-18.
 */
@Configuration
public class AppConfiguration {

    @Bean
    public static PropertyPlaceConfigurer propertyPlaceConfigurer(){
        PropertyPlaceConfigurer placeConfigurer = new PropertyPlaceConfigurer();
        placeConfigurer.setProperties("/Users/anakasimova/IdeaProjects/studyProject/service/src/main/resources/configuration.properties");
        return placeConfigurer;
    }
}
