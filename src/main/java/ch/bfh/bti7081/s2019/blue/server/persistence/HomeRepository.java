package ch.bfh.bti7081.s2019.blue.server.persistence;

import ch.bfh.bti7081.s2019.blue.server.persistence.model.Home;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeRepository extends JpaRepository<Home, Long> {

}
