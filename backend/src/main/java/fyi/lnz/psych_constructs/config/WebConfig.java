package fyi.lnz.psych_constructs.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.protobuf.ProtobufHttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** Allows protobuf messages to be passed to and from the rest controllers */
@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Autowired
  @Lazy
  ProtobufHttpMessageConverter protobufHttpMessageConverter;

  @Bean
  ProtobufHttpMessageConverter protobufHttpMessageConverter() {
    return new ProtobufHttpMessageConverter();
  }

  @Bean
  RestTemplate restTemplate(ProtobufHttpMessageConverter hmc) {
    return new RestTemplate(Arrays.asList(hmc));
  }

  @Override
  public void extendMessageConverters(@NonNull List<HttpMessageConverter<?>> converters) {
    converters.add(protobufHttpMessageConverter);
  }

}