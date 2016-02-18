package se.omegapoint.facepalm.client.config;

import org.apache.catalina.Context;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.Collections.singletonList;

@Configuration
public class TomcatConfig {

    @Bean
    public EmbeddedServletContainerFactory servletContainerFactory() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setTomcatContextCustomizers(singletonList(new HttpCustomizer()));
        return factory;
    }

    private static class HttpCustomizer implements TomcatContextCustomizer {

        @Override
        public void customize(final Context context) {
            context.setUseHttpOnly(false); // Defaults to true, protects against session cookie leakage in client (dont fix as contributer)
        }
    }
}
