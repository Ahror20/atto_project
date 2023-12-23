package org.example.controller;


import lombok.Setter;
import org.example.dto.Profile;
import org.example.service.ProfileService;
import org.example.service.TransactionService;
import org.example.util.ScannerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Scanner;

@Setter
@Controller
public class AdminController {
    @Autowired
    private ProfileService profileService;
    @Autowired
    private CardController cardController;
    @Autowired
    private TerminalController terminalController;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private StatisticController statisticController;

    public void start(Profile profile) {
        boolean b = true;
        while (b) {
            menu();
            int operation = ScannerUtil.getAction();
            switch (operation) {
                case 1:
                    cardController.start(profile);
                    break;
                case 2:
                    terminalController.start();
                    break;
                case 3:
                    profileList();
                    break;
                case 4:
                    changeProfileStatus();
                    break;
                case 5:
                    transactionService.transactionListForAdmin();
                    break;
                case 6:
                    transactionService.getBalanceFromCompanyCard();
                    break;
                case 7:
                    statisticController.start();
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
        System.out.println("*** ADMIN MENU ***");
        System.out.println("1.Card menu");
        System.out.println("2.Terminal menu");
        System.out.println("3.Profile list");
        System.out.println("4.Change profile status");
        System.out.println("5.Transaction list");
        System.out.println("6.Company card balance");
        System.out.println("7.Statistic");
        System.out.println("0. Log out" + "\n");
    }


    private void profileList() {
        profileService.profileList();
    }

    private void changeProfileStatus() {
        System.out.print("Enter profile phone: ");
        Scanner scanner = new Scanner(System.in);
        String phone = scanner.nextLine();

        profileService.changeProfileStatus(phone);
    }

}
