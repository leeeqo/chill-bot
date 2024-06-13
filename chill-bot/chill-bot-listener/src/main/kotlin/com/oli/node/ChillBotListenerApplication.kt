package com.oli.node

import org.springframework.boot.Banner
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.ComponentScan
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import java.util.*


//import org.springframework.data.jpa.repository.config.EnableJpaRepositories

//@EnableJpaRepositories("com.oli.node.*")
//@EntityScan("com.oli.*")
//@ComponentScan("co,.oli.*")
//@Configuration
@SpringBootApplication
/*@EnableAutoConfiguration(exclude = [
    DataSourceAutoConfiguration::class,
    DataSourceTransactionManagerAutoConfiguration::class,
    HibernateJpaAutoConfiguration::class])*/
class ChillBotListenerApplication(
    val applicationContext: ApplicationContext,
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val beans: Array<String> = applicationContext.getBeanDefinitionNames()
        Arrays.sort(beans)
        for (bean in beans) {
            println(bean)
        }
    }
}

fun main(args: Array<String>) {
    SpringApplicationBuilder(ChillBotListenerApplication ::class.java)
        .bannerMode(Banner.Mode.OFF)
        .run(*args)
}