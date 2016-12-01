package asmln.test.sboot.freemarker;

import asmln.test.sboot.freemarker.data.Template;
import asmln.test.sboot.freemarker.data.TemplateRepository;
import freemarker.cache.TemplateLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Data Base TemplateLoader for Freemarker
 */
@Repository
public class DbTemplateLoader implements TemplateLoader {

    private final TemplateRepository repository;

    @Autowired
    public DbTemplateLoader(TemplateRepository repository) {
        this.repository = repository;
    }

    @Override
    public Object findTemplateSource(String name) throws IOException {
        return repository.findOne(name);
    }

    @Override
    public long getLastModified(Object templateSource) {
        Template emailTemplate = (Template) templateSource;
        Template tmpl = repository.findOne(emailTemplate.name);
        return tmpl.lastModified.getTimeInMillis();
    }

    @Override
    public Reader getReader(Object templateSource, String encoding) throws IOException {
        return new StringReader(((Template) templateSource).content);
    }

    @Override
    public void closeTemplateSource(Object templateSource) throws IOException {

    }

    @Override
    public String toString() {
        return "DbTemplateLoader";
    }
}
