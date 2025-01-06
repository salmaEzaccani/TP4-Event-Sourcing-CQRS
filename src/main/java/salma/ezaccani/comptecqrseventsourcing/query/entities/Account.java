package salma.ezaccani.comptecqrseventsourcing.query.entities;



import salma.ezaccani.comptecqrseventsourcing.commoApi.enums.AccountState;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
        @Id
        private String id;
        private double amount;
        private String currency;
        @Enumerated(EnumType.STRING)
        private AccountState status;
        @OneToMany(mappedBy = "account")
        private List<Operation> operations;
}

