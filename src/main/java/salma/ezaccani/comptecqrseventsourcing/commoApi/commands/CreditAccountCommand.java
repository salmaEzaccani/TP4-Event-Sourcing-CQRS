package salma.ezaccani.comptecqrseventsourcing.commoApi.commands;


import lombok.Getter;


// la 1ere CMD
public class CreditAccountCommand extends BaseCommand<String> {
    //pour cree acc
    @Getter private String currency;
    @Getter private double amount;
    public CreditAccountCommand(String id,String currency,double amount) {
        super(id);
        this.amount=amount;
        this.currency=currency;
    }
}
