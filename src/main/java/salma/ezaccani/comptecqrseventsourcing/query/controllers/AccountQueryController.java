package salma.ezaccani.comptecqrseventsourcing.query.controllers;

import salma.ezaccani.comptecqrseventsourcing.commoApi.queries.GetAccountByIdQuery;
import salma.ezaccani.comptecqrseventsourcing.commoApi.queries.GetAllAccountsQuery;
import salma.ezaccani.comptecqrseventsourcing.query.entities.Account;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/queries/accounts")
@Slf4j
public class AccountQueryController {

    private QueryGateway queryGateway;


    @GetMapping("/allAccounts")
    public List<Account> allAccounts(){
        List<Account> response=queryGateway.query(new GetAllAccountsQuery(), ResponseTypes.multipleInstancesOf(Account.class)).join();
        return response;
    }
    @GetMapping("/ById/{id}")
    public Account accountById(@PathVariable String id){
        return queryGateway.query(new GetAccountByIdQuery(id),ResponseTypes.instanceOf(Account.class)).join();
    }


}
