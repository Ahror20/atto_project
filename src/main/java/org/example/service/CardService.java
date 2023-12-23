package org.example.service;

import lombok.Setter;
import org.example.container.ComponentContainer;
import org.example.dto.Card;
import org.example.dto.Profile;
import org.example.enums.GeneralStatus;
import org.example.enums.TransactionType;
import org.example.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Setter
@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private TerminalService terminalService;

    public void addCardToProfile(String phone, String cardNum) {
        Card exists = cardRepository.getCardByNumber(cardNum);
        if (exists == null) {
            System.out.println("Card not found");
            return;
        }
        cardRepository.assignPhoneToCard(phone, cardNum);

    }

    public void profileCardList(String phone) {
        List<Card> cardList = cardRepository.getCardByProfilePhone(phone);
        if (cardList.isEmpty()){
            System.out.println("card not found");
            return;
        }
        for (Card card : cardList) {
            System.out.println(card);
        }
    }

    public void userChangeCardStatus(String phone, String cardNumber) {
        Card card = cardRepository.getCardByNumber(cardNumber);
        if (card == null) {
            System.out.println("Card not found");
            return;
        }

        if (card.getPhone() == null || !card.getPhone().equals(phone)) {
            System.out.println("Mazgi card not belongs to you");
            return;
        }
        int n = 0;
        if (card.getStatus().equals(GeneralStatus.ACTIVE)) {
            n = cardRepository.updateCardStatus(cardNumber, GeneralStatus.BLOCK);
        } else if (card.getStatus().equals(GeneralStatus.BLOCK)) {
            n = cardRepository.updateCardStatus(cardNumber, GeneralStatus.ACTIVE);
        }
        if (n==1){
            System.out.println("your card status changed");
        }
        else {
            System.out.println("error");
        }


    }

    public void userDeleteCard(String phone, String cardNumber) {
        Card card = cardRepository.getCardByNumber(cardNumber);
        if (card == null) {
            System.out.println("Card not found");
            return;
        }

        if (card.getPhone() == null || !card.getPhone().equals(phone)) {
            System.out.println("Card not belongs to you");
            return;
        }

        int n = cardRepository.deleteCard(cardNumber);
        if (n != 0) {
            System.out.println("Card deleted");
        }
    }

    public void adminCreateCard(String cardNumber, String expiredDate, Profile profile) {
        Card exist = cardRepository.getCardByNumber(cardNumber);
        if (exist != null) {
            System.out.println("Card Number is exist");
            return;
        }
        if (!profile.equals(ComponentContainer.currentUser)) {
            System.out.println("you are admin?");
            return;
        }

        Card card = new Card();
        card.setCardNumber(cardNumber);
      try {
          DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
          LocalDate localDate = LocalDate.parse(expiredDate, timeFormatter);
          card.setExpDate(localDate);
      }
       catch (RuntimeException e){
           System.out.println("formatni to`gri kiritng");
           return;
       }

        card.setBalance(0d);
        card.setStatus(GeneralStatus.ACTIVE);
        card.setCreatedDate(LocalDateTime.now());
        int n = cardRepository.save(card);

        if (n ==1 ) {
            System.out.println("Card successfully added");
        } else {
            System.out.println("ERROR");
        }
    }

    public void cardList(Profile profile) {
        if (!profile.equals(ComponentContainer.currentUser)) {
            System.out.println("yoa are admin?");
            return;
        }
        List<Card> cardList = cardRepository.getList();
        for (Card card : cardList) {
            System.out.println(card);
        }
    }

    public void adminDeleteCard(String cardNumber, Profile profile) {
        if (!profile.equals(ComponentContainer.currentUser)) {
            System.out.println("yoa are admin?");
            return;
        }
        Card card = cardRepository.getCardByNumber(cardNumber);
        if (card == null) {
            System.out.println("Card not found");
            return;
        }
        int n = cardRepository.deleteCard(cardNumber);
        if (n != 0) {
            System.out.println("Card deleted");
        }
    }

    public void adminChangeStatus(String cardNumber, Profile profile) {
        if (!profile.equals(ComponentContainer.currentUser)) {
            System.out.println("yoa are admin?");
            return;
        }
        Card card = cardRepository.getCardByNumber(cardNumber);
        if (card == null) {
            System.out.println("Card not found");
            return;
        }

        if (card.getStatus().equals(GeneralStatus.ACTIVE)) {
            cardRepository.updateCardStatus(cardNumber, GeneralStatus.BLOCK);
        } else if (card.getStatus().equals(GeneralStatus.BLOCK)) {
            cardRepository.updateCardStatus(cardNumber, GeneralStatus.ACTIVE);
        }

    }

    public void adminUpdateCard(String cardNumber, String expiredDate, Profile profile) {
        if (!profile.equals(ComponentContainer.currentUser)) {
            System.out.println("yoa are admin?");
            return;
        }
        Card exist = cardRepository.getCardByNumber(cardNumber);
        if (exist == null) {
            System.out.println("Card not found");
            return;
        }

        Card card = new Card();
        card.setCardNumber(cardNumber);
        try {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
            LocalDate localDate = LocalDate.parse(expiredDate, timeFormatter);
            card.setExpDate(localDate);
        } catch (RuntimeException e) {
            System.out.println("mazgi tog`ri formatta kiriting");
            return;
        }

        int n = cardRepository.updateCard(card);
        if (n == 1) {
            System.out.println("Card Updated");
        }
    }

    public void userRefillCard(String phone, String cardNumber, Double amount) {
        Card card = cardRepository.getCardByNumber(cardNumber);
        if (card == null) {
            System.out.println("Card not found");
            return;
        }

        if (card.getPhone() == null || !card.getPhone().equals(phone)) {
            System.out.println("Mazgi card not belongs to you.");
            return;
        }

        // refill card
        cardRepository.refillCard(cardNumber, card.getBalance() + amount);
        // make transaction
        transactionService.createTransaction(card.getId(), null, amount, TransactionType.ReFill);

    }

}
