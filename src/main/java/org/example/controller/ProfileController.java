package org.example.controller;

import lombok.Setter;
import org.example.dto.Profile;
import org.example.service.CardService;
import org.example.service.TransactionService;
import org.example.util.ScannerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Scanner;
@Setter
@Controller
public class ProfileController {
    @Autowired
    private CardService cardService;
    @Autowired
    private TransactionService transactionService;

    public void start(Profile profile) {
        boolean b = true;

        while (b) {
            menu();
            int operation = ScannerUtil.getAction();
            switch (operation) {
                case 1:
                    addCard(profile);
                    break;
                case 2:
                    cardList(profile);
                    break;
                case 3:
                    changeCardStatus(profile);
                    break;
                case 4:
                    deleteCard(profile);
                    break;
                case 5:
                    refill(profile);
                    break;
                case 6:
                    transactionList(profile);
                    break;
                case 7:
                    payment(profile);
                    break;
                case 0:
                    b = false;
                    break;
                default:
                    b = false;
                    System.out.println("Wrong operation number");
            }
        }
    }

    public void menu() {
        System.out.println("1. Add Card");
        System.out.println("2. Card List ");
        System.out.println("3. Card Change Status");
        System.out.println("4. Delete Card");
        System.out.println("5. ReFill ");
        System.out.println("6. Transaction List");
        System.out.println("7. Make Payment");
        System.out.println("0. Log out" +"\n");
    }

    /**
     * Card
     */

    private void addCard(Profile profile) {
        System.out.print("Enter card number: ");

        Scanner scanner = new Scanner(System.in);
        String cardNumber = scanner.nextLine();

        cardService.addCardToProfile(profile.getPhone(), cardNumber);
    }

    private void cardList(Profile profile) {
        System.out.println("--- Card List ---");
        cardService.profileCardList(profile.getPhone());
    }

    private void changeCardStatus(Profile profile) {
        System.out.print("Enter card number: ");

        Scanner scanner = new Scanner(System.in);
        String cardNumber = scanner.nextLine();
        cardService.userChangeCardStatus(profile.getPhone(), cardNumber);
    }

    private void deleteCard(Profile profile) {
        System.out.print("Enter card number: ");
        Scanner scanner = new Scanner(System.in);
        String cardNumber = scanner.nextLine();

        cardService.userDeleteCard(profile.getPhone(), cardNumber);
    }

    private void refill(Profile profile) {
        System.out.print("Enter card number: ");
        Scanner scanner = new Scanner(System.in);
        String cardNumber = scanner.nextLine();

        System.out.print("Enter amount: ");
        Double amount = scanner.nextDouble();
        cardService.userRefillCard(profile.getPhone(), cardNumber, amount);
    }


    /**
     * Transaction
     */
    private void transactionList(Profile profile) {
        transactionService.getTransactionList(profile);
    }

    private void payment(Profile profile) {
        Scanner scanner  = new Scanner(System.in);

        System.out.print("enter card number: ");
        String card_number = scanner.next();

        System.out.print("enter terminal address: ");
        String address = scanner.next();

       transactionService.makePayment(card_number,address,profile);
    }


}
