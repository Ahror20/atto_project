package org.example.service;

import lombok.Setter;
import org.example.dto.Terminal;
import org.example.enums.GeneralStatus;
import org.example.repository.TerminalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Setter
@Service
public class TerminalService {
    @Autowired
    private TerminalRepository terminalRepository;


    public void addTerminal(Terminal terminal) {
        Terminal exist = terminalRepository.getTerminalByCode(terminal.getCode());
        if (exist != null) {
            System.out.println("Terminal code exists");
            return;
        }
        terminal.setCreatedDate(LocalDateTime.now());
        terminal.setStatus(GeneralStatus.ACTIVE);
        terminalRepository.save(terminal);
    }

    public void terminalList() {
        List<Terminal> terminalList = terminalRepository.getTerminalList();
        for (Terminal terminal : terminalList) {
            System.out.println(terminal);
        }
    }

    public void updateTerminal(Terminal terminal) {
        Terminal exist = terminalRepository.getTerminalByCode(terminal.getCode());
        if (exist == null) {
            System.out.println("Terminal not found");
            return;
        }

        terminalRepository.updateTerminal(terminal);
    }

    public void changeTerminalStatus(String code) {
        Terminal terminal = terminalRepository.getTerminalByCode(code);
        if (terminal == null) {
            System.out.println("Terminal not found");
            return;
        }
        int n=0;
        if (terminal.getStatus().equals(GeneralStatus.ACTIVE)) {
            n=terminalRepository.changeTerminalStatus(code, GeneralStatus.BLOCK);
        } else {
            n=terminalRepository.changeTerminalStatus(code, GeneralStatus.BLOCK);
        }
        if (n==1){
            System.out.println("your terminal status changed");
        }
        else {
            System.out.println("error");
        }

    }

    public void deleteTerminal(String code) {
        Terminal terminal = terminalRepository.getTerminalByCode(code);
        if (terminal == null) {
            System.out.println("Terminal not found");
            return;
        }

        int n= terminalRepository.deleteTerminal(code);
        if (n==1){

            System.out.println("terminal o`chirildi");
        }
    }


}
