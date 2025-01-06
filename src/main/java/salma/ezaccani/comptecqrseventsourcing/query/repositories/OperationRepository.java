package salma.ezaccani.comptecqrseventsourcing.query.repositories;

import salma.ezaccani.comptecqrseventsourcing.query.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation,Long> {
}
