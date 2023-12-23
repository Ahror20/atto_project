package org.example.controller;

import lombok.Setter;
import org.example.service.TransactionService;
import org.example.util.ScannerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Scanner;
@Controller

public class StatisticController {
    @Setter
    @Autowired
    private TransactionService transactionService;
    public void start() {
        boolean t = true;
        while (t) {
            menu();
            switch (ScannerUtil.getAction()) {
                case 1:
                    transactionService.todayTransactionListForAdmin();
                    break;
                case 2:
                    transactionByDay();
                    break;
                case 3:
                    transactionBetweenDays();
                    // Update card
                    break;
                case 4:
                    transactionService.getBalanceFromCompanyCard();
                    break;
                case 5:
                    transactionListByTerminal();
                    break;
                case 6:
                   transactionByCard();
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
    private void menu(){
        System.out.println("*** STATISTIC  MENU ***");
        System.out.println("1. Bugungi to'lovlar");
        System.out.println("2. Kunlik to'lovla");
        System.out.println("3. Oraliq to'lovlar");
        System.out.println("4. Umumiy balance");
        System.out.println("5. Transaction by Terminal");
        System.out.println("6. Transaction By Card");
        System.out.println("0.exit" +"\n");
    }

    private void transactionByDay() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("enter (yyyy-MM-dd) date:");
        String date = scanner.next();

        transactionService.transactionByDayForAdmin(date);
    }

    private void transactionBetweenDays() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("enter  (yyyy-MM-dd) fromDate: ");
        String fromDate = scanner.next();
        System.out.print("enter toDate: ");
        String toDate  = scanner.next();

        transactionService.transactionBetweenDays(fromDate,toDate);

    }
    private void transactionListByTerminal(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("enter terminal address: ");
        String address = scanner.next();
        transactionService.transactionByTerminalForAdmin(address);
    }

    private void transactionByCard() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("enter card number: ");
        String number = scanner.next();
        transactionService.transactionListByCard(number);
    }

}
