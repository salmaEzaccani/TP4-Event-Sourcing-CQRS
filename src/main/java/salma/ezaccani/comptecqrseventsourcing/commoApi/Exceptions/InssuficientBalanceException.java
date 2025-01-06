package salma.ezaccani.comptecqrseventsourcing.commoApi.Exceptions;

public class InssuficientBalanceException extends RuntimeException {
    public InssuficientBalanceException(String message){
        super(message);
    }
}
