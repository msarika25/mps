package eu.mps.ibpts.controller;

import eu.mps.ibpts.domain.dto.AccountBalanceDTO;
import eu.mps.ibpts.domain.dto.TransferRequestDTO;
import eu.mps.ibpts.domain.entity.Account;
import eu.mps.ibpts.domain.entity.Transaction;
import eu.mps.ibpts.exception.IbptsException;
import eu.mps.ibpts.service.AccountService;
import eu.mps.ibpts.service.BankTransferService;
import eu.mps.ibpts.service.MiniStatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IBPTSController {
    @Autowired
    private BankTransferService bankTransferService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private MiniStatementService miniStatementService;

    //transfer the funds
    @PostMapping("/transfer")
    public ResponseEntity<Object> transfer(@RequestBody TransferRequestDTO transferRequestDTO) {
    try {
        String transferStatus = bankTransferService.transfer(transferRequestDTO);
        return ResponseEntity.ok().body(transferStatus);
    }
    catch(IbptsException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }}

   // Get the balance
    @GetMapping("/accounts/{accountId}/balance")
    public ResponseEntity<Object> getAccountBalance(@PathVariable long accountId) {
        try {
            Account account = accountService.getAccountBalance(accountId);
            AccountBalanceDTO accountBalanceDTO = new AccountBalanceDTO(accountId, account.getBalance(), account.getCurrency());
            return ResponseEntity.ok().body(accountBalanceDTO);
        } catch (IbptsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Get the mini statement for {count} transactions
    @GetMapping("/accounts/{accountId}/{count}/statements/mini")
    public ResponseEntity<Object> getMiniStatement(@PathVariable long accountId, @PathVariable int count) {
        try {
            List<Transaction> transactionList = miniStatementService.getMiniStatement(accountId, count);
            return ResponseEntity.ok().body(transactionList);
        } catch (IbptsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


}

