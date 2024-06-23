package com.oli.node

//import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder

@SpringBootApplication
class ChillBotListenerApplication/*(
    val applicationContext: ApplicationContext,
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val beans: Array<String> = applicationContext.getBeanDefinitionNames()
        Arrays.sort(beans)
        for (bean in beans) {
            println(bean)
        }
    }
}*/

fun main(args: Array<String>) {
    SpringApplicationBuilder(ChillBotListenerApplication ::class.java)
        .bannerMode(Banner.Mode.OFF)
        .run(*args)
}