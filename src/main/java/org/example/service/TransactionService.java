package org.example.service;

import lombok.Setter;
import org.example.container.ComponentContainer;
import org.example.container.TotalFaire;
import org.example.dto.Card;
import org.example.dto.Profile;
import org.example.dto.Terminal;
import org.example.dto.Transaction;
import org.example.enums.GeneralStatus;
import org.example.enums.TransactionType;
import org.example.repository.CardRepository;
import org.example.repository.TerminalRepository;
import org.example.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private TerminalRepository terminalRepository;

    public void createTransaction(Integer cardId, Integer terminalId, Double amount, TransactionType type) {
        Transaction transaction = new Transaction();
        transaction.setCardId(cardId);
        transaction.setTerminalId(terminalId);
        transaction.setAmount(amount);
        transaction.setTransactionType(type);
        transaction.setCreatedDate(LocalDateTime.now());

        transactionRepository.createTransaction(transaction);
    }

    public void getTransactionList(Profile profile) {
        List<Transaction> transactions = transactionRepository.getAll();
        if (transactions.isEmpty()) {
            System.out.println("transaction not found");
            return;
        }
        if (!profile.equals(ComponentContainer.currentUser)){
            System.out.println(" This profile is not yours");
            return;
        }

        for (Transaction transaction : transactions) {
            if (transaction != null && profile.getId().equals(ComponentContainer.currentUser.getId())) {
                System.out.println(transaction);
            }
        }
    }

    public void makePayment(String card_number, String address,Profile profile) {
        Card card = checkCard(card_number);
        if (card == null) {
            System.out.println("card not found");
            return;
        }

        Terminal terminal = checkTerminal(address);
        if (terminal == null) {
            System.out.println("terminal not found" + "\n");
            return;
        }
        if (card.getBalance() < TotalFaire.faire) {
            System.err.println("there is not enough money" + "\n");
            return;
        }
        if (card.getStatus().equals(GeneralStatus.BLOCK)) {
            System.out.println("this card is block" + "\n");
            return;
        }
        if (!profile.equals(ComponentContainer.currentUser)){
            System.out.println("This is card not yours");
            return;
        }


        Transaction transaction = new Transaction();
        transaction.setCardId(card.getId());
        transaction.setTerminalId(terminal.getId());
        transaction.setAmount(TotalFaire.faire);
        transaction.setTransactionType(TransactionType.Payment);
        transaction.setCreatedDate(LocalDateTime.now());

//user
        card.setBalance(card.getBalance() - TotalFaire.faire);
        cardRepository.addMinusCard(card_number, card.getBalance());


//company
       addBalanceCompanyCard(TotalFaire.faire);

        int result = transactionRepository.createTransaction(transaction);
        if (result == 0) {
            System.out.println("the transaction was not completed " + "\n");
        }
        System.out.println("the transaction has successfully completed" + "\n");
    }

    public Card checkCard(String card_id) {
        List<Card> cardList = cardRepository.getList();
        for (Card card : cardList) {
            if (card != null && card.getCardNumber().equals(card_id)) {
                return card;
            }
        }
        return null;
    }

    public Terminal checkTerminal(String address) {
        List<Terminal> terminalList = terminalRepository.getTerminalList();
        for (Terminal terminal : terminalList) {
            if (terminal != null && terminal.getAddress().equals(address)) {
                return terminal;
            }
        }
        return null;
    }
    public  void addBalanceCompanyCard(Double amount){
        Card card = cardRepository.getCardByNumber("7777");
        card.setBalance(card.getBalance()+amount);
        cardRepository.refillCard(card.getCardNumber(),card.getBalance());
    }
    public  void getBalanceFromCompanyCard(){
        Card card = cardRepository.getCardByNumber("7777");
        System.out.println( "balance: " + card.getBalance() +"$" +"\n");
    }

    public void transactionListForAdmin() {
        List<Transaction> transactions = transactionRepository.getAll();
        if (transactions.isEmpty()){
            System.out.println("transactions not found");
            return;
        }
        for (Transaction transaction:transactions){
            if (transaction != null ){
                System.out.println(transaction );
            }
        }
    }

    public void todayTransactionListForAdmin() {
        LocalDate localDate = LocalDateTime.now().toLocalDate();
        List<Transaction> transactions = transactionRepository.getAll();

        List<Transaction> todayTrans = new ArrayList<>();
        for (Transaction transaction:transactions){
            if (transaction != null && transaction.getCreatedDate().toLocalDate().equals(localDate) ){
                todayTrans.add(transaction);
                System.out.println(transaction );
            }
        }
        if (todayTrans.isEmpty()){
            System.out.println("transactions not found");
        }

    }

    public void transactionByDayForAdmin(String date) {
        List<Transaction> transactions = transactionRepository.getAll();
        if (transactions.isEmpty()){
            System.out.println("transactions not found");
            return;
        }
        for (Transaction transaction:transactions){
            if (transaction != null && transaction.getCreatedDate().toLocalDate().toString().equals(date) ){
                System.out.println(transaction );
            }

        }
    }

    public void transactionByTerminalForAdmin(String address) {
        Terminal terminal = checkTerminal(address);
        if (terminal == null){
            System.out.println("terminal not found");
            return;
        }
        List<Transaction> transactions = transactionRepository.getAll();
        for (Transaction transaction:transactions){
            if (transaction != null && transaction.getTerminalId().equals(terminal.getId()) ){
                System.out.println(transaction );
            }
        }
    }

    public void transactionListByCard(String number) {
        Card card = checkCard(number);
        if (card == null){
            System.out.println("card not found");
            return;
        }
        List<Transaction> transactions = transactionRepository.getAll();
        for (Transaction transaction:transactions){
            if (transaction != null && transaction.getCardId().equals(card.getId()) ){
                System.out.println(transaction );
            }
        }
    }

    public void transactionBetweenDays(String fromDate, String toDate) {
        try {
            LocalDate fromdate = LocalDate.parse(fromDate);
            LocalDate todate = LocalDate.parse(toDate);
            List<Transaction> transactions = transactionRepository.transactionBetweenDay(fromdate.atStartOfDay(), todate.atStartOfDay().plusDays(1));
            for (Transaction transaction:transactions){
                if (transaction != null ){
                    System.out.println(transaction );
                }
                else {
                    System.out.println("transaction not found");
                    return;
                }
            }
        }
        catch (RuntimeException e){
            System.out.println("formatni togri kiriting");
        }



    }
}
