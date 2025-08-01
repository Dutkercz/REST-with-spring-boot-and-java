package dutkercz.com.github.repositories;

import dutkercz.com.github.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
