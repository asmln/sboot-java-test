package asmln.test.sboot.freemarker.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Template
 */
@Repository
public interface TemplateRepository extends CrudRepository<Template, String> {
}
