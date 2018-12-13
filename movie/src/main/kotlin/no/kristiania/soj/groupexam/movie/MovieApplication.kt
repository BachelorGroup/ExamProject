package no.kristiania.soj.groupexam.movie

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import springfox.documentation.builders.PathSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.service.ApiInfo

@SpringBootApplication(scanBasePackages = ["no.kristiania.soj.groupexam"])
@EnableSwagger2
@EnableDiscoveryClient
class MovieApplication {

    @Bean
    fun swaggerApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.any())
                .build()
    }
    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title("API for movie rest")
                .description("this is the /api part of the movie")
                .version("1.0")
                .build()
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(MovieApplication::class.java, *args)
}