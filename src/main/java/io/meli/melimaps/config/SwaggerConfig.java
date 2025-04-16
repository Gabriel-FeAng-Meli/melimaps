package io.meli.melimaps.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    Info info = new Info()
        .title("Meli Maps")
        .version("0.0.1")
        .description("API that calculates the optimal route for an user, considering it's prefferences in transport type, cost, CO2 emissions and more.")
        .contact(
            new Contact()
                .name("Gabriel Ferreira Angelo")
                .email("gabriel.fangelo@mercadolivre.com"));


    Server server = new Server()
        .url("http://localhost:8080")
        .description("Meli Maps API");

    return new OpenAPI()
        .components(new Components())
        .info(info)
        .addServersItem(server);
  }

  @Bean
  public ModelResolver modelResolver(ObjectMapper objectMapper) {
    return new ModelResolver(objectMapper);
  }

}

