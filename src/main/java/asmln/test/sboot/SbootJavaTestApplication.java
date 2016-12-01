package asmln.test.sboot;

import freemarker.cache.StringTemplateLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
@Configuration
public class SbootJavaTestApplication {

	@Autowired
	private ResourceLoader resourceLoader;

	public static void main(String[] args) {
		SpringApplication.run(SbootJavaTestApplication.class, args);
	}

	@Bean("freemarkerConfig")
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public freemarker.template.Configuration freemarkerConfig() throws IOException {
		freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_25);
		cfg.setClassForTemplateLoading(this.getClass(), "/fm");
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(freemarker.template.TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setLogTemplateExceptions(false);
		return cfg;
	}

	@Bean("freemarkerStrConfig")
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public freemarker.template.Configuration freemarkerStrConfig() throws IOException {
		freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_25);

		StringTemplateLoader stringLoader = new StringTemplateLoader();
		String firstTemplate = "firstTemplate";
		stringLoader.putTemplate("userName", "${userName}");
		stringLoader.putTemplate("text", "<#include \"userName\">; ${count}");
		cfg.setTemplateLoader(stringLoader);
		return cfg;
	}
}
