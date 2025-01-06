package salma.ezaccani.comptecqrseventsourcing.commoApi.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class DebitAccountRequestDTO {

    private String  accountId;
    private String currency;
    private double initialBalance;
}
