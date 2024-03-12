package com.papel.interview.configs;

import com.papel.interview.domain.entities.City;
import com.papel.interview.domain.entities.Country;
import com.papel.interview.domain.models.CityDTO;
import com.papel.interview.domain.models.CountryDTO;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoreConfiguration {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Papel Interview API")
                        .description("Papel Interview API endpoints for countries and cities")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        modelMapper.addMappings(new PropertyMap<CountryDTO, Country>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });
        modelMapper.addMappings(new PropertyMap<CityDTO, City>() {
            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });
        return modelMapper;
    }
}
