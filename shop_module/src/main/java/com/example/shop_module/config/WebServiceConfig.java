package com.example.shop_module.config;

import com.example.shop_module.service.soap.ProductEndpoint;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.validation.XmlValidator;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import javax.xml.transform.Source;

@Configuration
@EnableWs
public class WebServiceConfig {
    public static  final String NAMESPACE_GREETING = "http://example.com/shop_module/ws/greeting";

    @Bean
    public ServletRegistrationBean messsageDispatcherServlet(ApplicationContext applicationContext){
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet,"/ws/*");
    }
    @Bean(name="greeting")
    public DefaultWsdl11Definition defaultWsdl11Definition() {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("GreetingPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace(NAMESPACE_GREETING);
        wsdl11Definition.setSchema(xsdGreetingSchema());
        return wsdl11Definition;
    }

    @Bean("greetingSchema")
    public   XsdSchema xsdGreetingSchema() {
            return new SimpleXsdSchema(new ClassPathResource("ws/greeting.xsd"));
    }

    @Bean(name = "product")
    public DefaultWsdl11Definition productWsdl11Definition(){
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("ProductPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace(ProductEndpoint.NAMESPACE_URL);
        wsdl11Definition.setSchema(xsdProductSchema());
        return wsdl11Definition;
    }
    @Bean("productSchema")
    public XsdSchema xsdProductSchema(){
        return new SimpleXsdSchema(new ClassPathResource("ws/product.xsd"));
    }


}
