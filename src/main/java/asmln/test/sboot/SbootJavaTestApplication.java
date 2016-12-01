package asmln.test.sboot;

import asmln.test.sboot.freemarker.data.Template;
import asmln.test.sboot.freemarker.data.TemplateRepository;
import asmln.test.sboot.freemarker.DbTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Calendar;

@SpringBootApplication
@Configuration
public class SbootJavaTestApplication {

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private TemplateRepository repository;

	@Autowired
	private DbTemplateLoader dbTemplateLoader;

	public static void main(String[] args) {
		SpringApplication.run(SbootJavaTestApplication.class, args);
	}

	@PostConstruct
	public void init() {
		repository.save(new Template("text", "<#include \"userName\">; <#include \"count\">", Calendar.getInstance()));
		repository.save(new Template("userName", "${userName}", Calendar.getInstance()));
		repository.save(new Template("count", "${count}", Calendar.getInstance()));
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

	@Bean("freemarkerDbConfig")
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	public freemarker.template.Configuration freemarkerDbConfig() throws IOException {
		freemarker.template.Configuration cfg = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_25);

		cfg.setTemplateLoader(dbTemplateLoader);
		return cfg;
	}
}
