package salma.ezaccani.comptecqrseventsourcing.commands.aggregates;

import salma.ezaccani.comptecqrseventsourcing.commoApi.Exceptions.InssuficientBalanceException;
import salma.ezaccani.comptecqrseventsourcing.commoApi.Exceptions.InvalidAmountException;
import salma.ezaccani.comptecqrseventsourcing.commoApi.commands.CreateAccountCommand;
import salma.ezaccani.comptecqrseventsourcing.commoApi.commands.CreditAccountCommand;
import salma.ezaccani.comptecqrseventsourcing.commoApi.commands.DebitAccountCommand;
import salma.ezaccani.comptecqrseventsourcing.commoApi.enums.AccountState;
import salma.ezaccani.comptecqrseventsourcing.commoApi.events.AccountActivatedEvent;
import salma.ezaccani.comptecqrseventsourcing.commoApi.events.AccountCreatedEvent;
import salma.ezaccani.comptecqrseventsourcing.commoApi.events.AccountCreditedEvent;
import salma.ezaccani.comptecqrseventsourcing.commoApi.events.AccountDebitedEvent;
import lombok.extern.slf4j.Slf4j;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Date;

@Aggregate
@Slf4j
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double amount;
    private String currency;
        private AccountState status;

    public AccountAggregate() {

    }
    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCmd) {
        if (createAccountCmd.getAmount()<0) throw new  RuntimeException("impossible to create an account with a null balance!");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCmd.getId(),
                createAccountCmd.getAmount(),
                createAccountCmd.getCurrency(),
                AccountState.CREATED
        ));

    }
    @EventSourcingHandler
    public void on(AccountCreatedEvent event){
        this.accountId=event.getId();
        this.amount =event.getAmount();
        this.currency=event.getCurrency();
        this.status=AccountState.CREATED;
        AggregateLifecycle.apply(new AccountActivatedEvent(event.getId(),
                AccountState.ACTIVATED));
    }
    @EventSourcingHandler
    public void on(AccountActivatedEvent event){
        this.status=event.getStatus();
    }

    @CommandHandler
    public void handle(CreditAccountCommand creditAccountCmd) throws InvalidAmountException {
        log.info("Attempting to credit amount: {}", creditAccountCmd.getAmount());
        log.info("Attempting to credit with currency: {}", creditAccountCmd.getCurrency());
        if (creditAccountCmd.getAmount()<=0) throw new InvalidAmountException("impossible to credit a negative amount!");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                creditAccountCmd.getId(),
                creditAccountCmd.getAmount(),
                creditAccountCmd.getCurrency(),
                new Date()
        ));
    }
    @EventSourcingHandler
    public void on(AccountCreditedEvent event){
        this.amount+=event.getBalance();
    }

    @CommandHandler
    public void handle(DebitAccountCommand debitAccountCmd) throws InssuficientBalanceException,InvalidAmountException{
        if (debitAccountCmd.getAmount()<=0) throw new InvalidAmountException("impossible to credit a negative amount!");
        if (debitAccountCmd.getAmount()>this.amount) throw new InssuficientBalanceException(" NO sufficient Balance Exception!! "+this.amount);
        AggregateLifecycle.apply(new AccountDebitedEvent(
                debitAccountCmd.getId(),
                debitAccountCmd.getAmount(),
                debitAccountCmd.getCurrency(),
                new Date()
        ));
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent event){
        this.amount-=event.getBalance();
    }


}