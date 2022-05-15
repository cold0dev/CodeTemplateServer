package cold.codetemplate.repositories;

import cold.codetemplate.models.Template;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TemplateRepository extends JpaRepository<Template,Long> {
}