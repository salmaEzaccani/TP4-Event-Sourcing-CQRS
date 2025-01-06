package salma.ezaccani.comptecqrseventsourcing.commoApi.commands;
import lombok.Getter;


// la 1ere CMD
public class CreateAccountCommand extends BaseCommand<String> {
    //pour cree acc
   @Getter private double amount;
   @Getter private  String currency;

    public CreateAccountCommand(String id, double amount, String currency) {
        super(id);
        this.amount = amount;
        this.currency = currency;
    }
}