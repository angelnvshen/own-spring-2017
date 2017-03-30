package own.stu.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import java.util.Properties;

@SpringBootApplication
public class OwnSpringApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

//		return application.sources(OwnSpringApplication.class);
		return application.sources(OwnSpringApplication.class).properties(getProperties());
	}

	public static void main(String[] args) throws Exception {

//		SpringApplication.run(OwnSpringApplication.class, args);
		SpringApplication application = new SpringApplication(OwnSpringApplication.class);
		application.setAdditionalProfiles("file:C:/Users/dell/Desktop/own-spring/application.properties");
		application.run(args);
	}

	static Properties getProperties() {
		Properties props = new Properties();
		props.put("spring.config.location", "file:C:/Users/dell/Desktop/own-spring/application.properties");
		return props;
	}

}
