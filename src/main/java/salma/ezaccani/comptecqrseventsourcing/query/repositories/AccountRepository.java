package salma.ezaccani.comptecqrseventsourcing.query.repositories;

import salma.ezaccani.comptecqrseventsourcing.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,String> {
}
