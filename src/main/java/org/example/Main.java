package org.example;

import org.example.controller.AuthController;
import org.example.db.DataBase;
import org.example.db.InitDataBase;
import org.example.dto.Profile;
import org.example.repository.ProfileRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {


        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        AuthController authController = (AuthController) applicationContext.getBean("authController");

        DataBase dataBase = (DataBase) applicationContext.getBean("dataBase");
        dataBase.initTable();

        InitDataBase initDataBase = (InitDataBase) applicationContext.getBean("initDataBase");
        initDataBase.adminInit();
        initDataBase.addCompanyCard();

        authController.start();


    }
}
