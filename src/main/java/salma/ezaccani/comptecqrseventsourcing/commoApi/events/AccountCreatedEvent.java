package salma.ezaccani.comptecqrseventsourcing.commoApi.events;

import salma.ezaccani.comptecqrseventsourcing.commoApi.enums.AccountState;
import lombok.Getter;

public  class AccountCreatedEvent extends BaseEvent<String> {
    @Getter private double amount;
    @Getter private String currency;
    @Getter private AccountState status;

    public AccountCreatedEvent(String id, double amount, String currency, AccountState status) {
        super(id);
        this.amount = amount;
        this.currency = currency;
        this.status = status;
    }
}
