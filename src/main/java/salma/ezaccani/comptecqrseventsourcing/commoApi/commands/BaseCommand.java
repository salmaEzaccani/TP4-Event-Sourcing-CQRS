package salma.ezaccani.comptecqrseventsourcing.commoApi.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public abstract class BaseCommand<T> {
    //Identi aggregation -> effectuer la commande c est le compte
    @TargetAggregateIdentifier
    // pas de setter objet immuable
    @Getter
    private T id;

    public BaseCommand(T id) {
        this.id = id;
    }
}
