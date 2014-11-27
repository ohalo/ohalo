package cn.ohalo.log;

import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

@SuppressWarnings("deprecation")
public class TestHelloWorld {

	public static void main(String[] args) {
		ClassPathResource resource = new ClassPathResource("spring.xml");  
        XmlBeanFactory beanFactory = new XmlBeanFactory(resource);  
        OhaloService bean = (OhaloService)beanFactory.getBean("ohaloService");  
        bean.test("张三");
	}
}
