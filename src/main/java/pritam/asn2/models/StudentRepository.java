package pritam.asn2.models;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for the Student class. This interface is used to interact with the database, doing things like read and write.
 *
 * For this project, extending Spring Data JPA stuff is enough. By extending JpaRepository, we get a bunch of methods for free, such as findAll, findById, save, delete, etc.
 *
 * If any other special queries are needed, we can add them here.
 */
public interface StudentRepository extends JpaRepository<Student, Integer> {
}
