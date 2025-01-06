package salma.ezaccani.comptecqrseventsourcing.commoApi.Exceptions;

public class InvalidAmountException extends RuntimeException {
    public InvalidAmountException(String message){
        super(message);
    }
}
