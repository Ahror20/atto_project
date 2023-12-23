package org.example.controller;

import lombok.Setter;
import org.example.dto.Profile;
import org.example.service.CardService;
import org.example.util.ScannerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Scanner;
@Controller
public class CardController {
    @Setter
    @Autowired
    private CardService cardService;
    public void start(Profile profile){
        boolean t = true;
        while (t) {
            cardMenu();
            switch (ScannerUtil.getAction()) {
                case 1:
                    //add card
                    addCard(profile);
                    break;
                case 2:
                    //cardliost
                    cardList(profile);
                    break;
                case 3:
                    // Update card
                    updateCard(profile);
                    break;
                case 4:
                    //Change Card status
                    changeCardStatus(profile);
                    break;
                case 5:
                    //Delete Card
                    deleteCard(profile);
                    break;
                case 0:
                    t = false;
                    break;
                default:
                    System.out.println("select correct menu");
                    break;
            }
        }
    }
    private void cardMenu() {
        System.out.println("*** CARD MENU *** ");
        System.out.println("1.Add card");
        System.out.println("2.Card list");
        System.out.println("3.Update card");
        System.out.println("4.Change Card status");
        System.out.println("5. Delete Card");
        System.out.println("0.exit" + "\n");
    }
    private void addCard(Profile profile) {
        System.out.print("Enter card number: ");
        Scanner scanner = new Scanner(System.in);
        String cardNumber = scanner.nextLine();

        System.out.print("Enter card expired date (yyyy.MM.dd): ");
        String expiredDate = scanner.nextLine();

        cardService.adminCreateCard(cardNumber, expiredDate,profile);
    }
    private void cardList(Profile profile) {
        cardService.cardList(profile);
    }
    private void deleteCard(Profile profile) {
        System.out.print("Enter card number: ");
        Scanner scanner = new Scanner(System.in);
        String cardNumber = scanner.nextLine();

        cardService.adminDeleteCard(cardNumber,profile);
    }
    private void changeCardStatus(Profile profile) {
        System.out.print("Enter card number: ");
        Scanner scanner = new Scanner(System.in);
        String cardNumber = scanner.nextLine();

        cardService.adminChangeStatus(cardNumber,profile);
    }
    private void updateCard(Profile profile) {
        System.out.print("Enter card number: ");
        Scanner scanner = new Scanner(System.in);
        String cardNumber = scanner.nextLine();

        System.out.print("Enter card expired date (yyyy.MM.dd): ");
        String expiredDate = scanner.nextLine();

        cardService.adminUpdateCard(cardNumber, expiredDate,profile);
    }
}
