package salma.ezaccani.comptecqrseventsourcing.commands.controllers;


import salma.ezaccani.comptecqrseventsourcing.commoApi.commands.CreateAccountCommand;
import salma.ezaccani.comptecqrseventsourcing.commoApi.commands.CreditAccountCommand;
import salma.ezaccani.comptecqrseventsourcing.commoApi.commands.DebitAccountCommand;
import salma.ezaccani.comptecqrseventsourcing.commoApi.dtos.CreateAccountRequestDTO;
import salma.ezaccani.comptecqrseventsourcing.commoApi.dtos.CreditAccountRequestDTO;
import salma.ezaccani.comptecqrseventsourcing.commoApi.dtos.DebitAccountRequestDTO;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

//controller pour la partie commande (la partie ecriture)

@RestController
@RequestMapping(path = "/commands/account")
@AllArgsConstructor
public class AccountCommandController {
    private CommandGateway commandGateway;
    private EventStore eventStore;

    @PostMapping(path = "/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountRequestDTO request){
        CompletableFuture<String> commandResponse = commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                request.getInitialBalance(),
                request.getCurrency()
        ));
        return commandResponse;
    }

    //modifer l etat du compte MAJ
    @PutMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO request){
        CompletableFuture<String> commandResponse=commandGateway.send(new CreditAccountCommand(
                request.getAccountId(),
                request.getCurrency(),
                request.getInitialBalance()
        ));
        return commandResponse;
    }


    @PutMapping("/Debit")
    public CompletableFuture<String> creditAccount(@RequestBody DebitAccountRequestDTO request) {
        CompletableFuture<String> commandResponse=commandGateway.send(new DebitAccountCommand(
                request.getAccountId(),
                Double.parseDouble(String.valueOf(request.getInitialBalance())), // Conversion si nécessaire
                request.getCurrency()
        ));
        return commandResponse;
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<String> exceptionHandler(Exception exception){
        ResponseEntity<String> entity =new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return entity;
    }

    @GetMapping("/eventStore/{accountId}")
    public Stream eventStore(@PathVariable String accountId){
        return eventStore.readEvents(accountId).asStream();
    }

}
