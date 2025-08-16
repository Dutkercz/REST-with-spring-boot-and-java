package dutkercz.com.github.repositories;

import dutkercz.com.github.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PersonRepository extends JpaRepository<Person, Long> {
    //informar ao spring que a query é do tipo de MODIFICAÇÃO DE DADOS, e não um 'simples' SELECT
    @Modifying(clearAutomatically = true) //limpa o objeto em memoria automaticamente
    @Query("""
            UPDATE Person p
            SET p.enabled = false
            WHERE p.id = :id
            """)
    void disablePerson(@Param("id") Long id);

}
