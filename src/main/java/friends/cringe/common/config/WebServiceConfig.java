package friends.cringe.common.config;

import friends.cringe.common.url.BaseUrl;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.List;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

  public static final String NAMESPACE = "http://www.cringe.friends/soap";

  @Override
  public void addInterceptors(List<EndpointInterceptor> interceptors) {
    PayloadValidatingInterceptor validatingInterceptor = new PayloadValidatingInterceptor();
    validatingInterceptor.setValidateRequest(true);
    validatingInterceptor.setValidateResponse(true);
    validatingInterceptor.setXsdSchema(cringeSoapXsd());
    interceptors.add(validatingInterceptor);
  }

  @Bean
  public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
    MessageDispatcherServlet servlet = new MessageDispatcherServlet();
    servlet.setApplicationContext(applicationContext);
    servlet.setTransformWsdlLocations(true);
    return new ServletRegistrationBean(servlet, BaseUrl.BASE_SOAP_API + "/*");
  }

  @Bean(name = "cringeSoapWsdl")
  public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema cringeSoapXsd) {
    DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
    wsdl11Definition.setPortTypeName("SoapPort");
    wsdl11Definition.setLocationUri(BaseUrl.BASE_SOAP_API);
    wsdl11Definition.setTargetNamespace(NAMESPACE);
    wsdl11Definition.setSchema(cringeSoapXsd);
    return wsdl11Definition;
  }

  @Bean
  public XsdSchema cringeSoapXsd() {
    return new SimpleXsdSchema(new ClassPathResource("cringe.xsd"));
  }

}
