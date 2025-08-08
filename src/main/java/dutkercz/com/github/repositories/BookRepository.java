package dutkercz.com.github.repositories;

import dutkercz.com.github.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long>{
}
