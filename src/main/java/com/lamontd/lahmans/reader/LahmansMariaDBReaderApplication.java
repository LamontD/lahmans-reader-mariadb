/*
 * Copyright (C) 2020 lamontdozierjr
 *
 * This program is licensed under a Creative Commons Attribution-Share 
 * Alike 3.0 Unported License ("CC-BY-SA"). An explanation of CC-BY-SA can 
 * be found at:
 *
 * <http://creativecommons.org/license/by-sa/3.0/>.
 *
 * Unless required by applicable law or agreed to in writing, this software
 * is distributed on an "AS IS" BASIS, WITHOUT ANY WARRANTY or even 
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 */
package com.lamontd.lahmans.reader;

import com.lamontd.transactionmanager.model.TransactionManagerConsts;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {
    TransactionManagerConsts.SERVICES_BASE_PACKAGE,
    "com.lamontd.lahmans.reader"
})
public class LahmansMariaDBReaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(LahmansMariaDBReaderApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI(@Value("${lahmans-reader-api.version}") String apiVersion) {
        Info apiInfo = new Info()
                .title("Lahmans Baseball Database")
                .description("Provides local access to data in the 2019 Lahmans"
                        + " Baseball Database, as well as some other things for some of my purposes")
                .version(apiVersion)
                .contact(new Contact().name("Lamont Dozier, Jr.").email("lamont.dozier.jr@gmaiul.com"))
                .license(new License()
                        .name("Creative Commons Attribution-ShareAlike Unported 3.0 license (CC BY-SA 3.0)")
                        .url("https://creativecommons.org/licenses/by-sa/3.0/"));
        ExternalDocumentation lahmanDocs = new ExternalDocumentation()
                .description("Description of Lahmans Baseball Database")
                .url("http://www.seanlahman.com/baseball-archive/statistics/");
        return new OpenAPI()
                .info(apiInfo)
                .externalDocs(lahmanDocs);
    }

}
