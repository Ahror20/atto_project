package org.example.controller;

import lombok.Setter;
import org.example.dto.Terminal;
import org.example.service.TerminalService;
import org.example.util.ScannerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Scanner;
@Controller
public class TerminalController {
    @Setter
    @Autowired
    private TerminalService terminalService;
    public void start() {
        boolean t = true;
        while (t) {
            terminalMenu();
            switch (ScannerUtil.getAction()) {
                case 1:
                    //add terminal
                    createTerminal();
                    break;
                case 2:
                    //list
                    terminalService.terminalList();
                    break;
                case 3:
                    // Update card
                    updateTerminal();
                    break;
                case 4:
                    changeTerminalStatus();
                    break;
                case 5:
                    deleteTerminal();
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
    private void terminalMenu() {
        System.out.println("*** TERMINAL MENU *** ");
        System.out.println("1.Create terminal ");
        System.out.println("2.Terminal list");
        System.out.println("3.Update terminal");
        System.out.println("4.Change terminal status");
        System.out.println("5. Delete terminal");
        System.out.println("0.exit" + "\n");
    }
    private void createTerminal() {
        System.out.print("Enter  code: ");
        Scanner scanner = new Scanner(System.in);
        String code = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        Terminal terminal = new Terminal();
        terminal.setCode(code);
        terminal.setAddress(address);

        terminalService.addTerminal(terminal);
    }
    private void updateTerminal() {
        System.out.print("Enter code: ");
        Scanner scanner = new Scanner(System.in);
        String code = scanner.nextLine();

        System.out.print("Enter address: ");
        String address = scanner.nextLine();

        Terminal terminal = new Terminal();
        terminal.setCode(code);
        terminal.setAddress(address);

        terminalService.updateTerminal(terminal);
    }
    private void changeTerminalStatus() {
        System.out.print("Enter code: ");
        Scanner scanner = new Scanner(System.in);
        String code = scanner.nextLine();
        terminalService.changeTerminalStatus(code);
    }
    private void deleteTerminal() {
        System.out.print("Enter code: ");
        Scanner scanner = new Scanner(System.in);
        String code = scanner.nextLine();
        terminalService.deleteTerminal(code);
    }

}
