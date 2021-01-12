package com.test.controller;

import com.sun.mail.smtp.SMTPOutputStream;
import com.test.EmailManager;
import com.test.controller.services.LoginService;
import com.test.model.EmailAccount;
import com.test.view.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginWindowController extends BaseController{
    @FXML
    private Label errorLabel;

    @FXML
    private TextField emailAddressField;

    @FXML
    private PasswordField passwordField;

    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void loginButtonAction() {
        if (fieldsAreValid()){
            EmailAccount emailAccount = new EmailAccount(emailAddressField.getText(), passwordField.getText());
            LoginService loginService = new LoginService(emailAccount, emailManager);
            EmailLoginResult emailLoginResult = loginService.login();

            switch (emailLoginResult){
                case SUCCESS:
                    System.out.println("login succesfull for " + emailAccount );
                    return;
            }
        };
        System.out.println("loginButtonAction()");
        viewFactory.showMainWindow();
        //close the LoginWindow stage
        Stage loginWindowStage = (Stage) errorLabel.getScene().getWindow();
        viewFactory.closeStage(loginWindowStage);
    }

    private boolean fieldsAreValid() {
        if(emailAddressField.getText().isEmpty()){
            errorLabel.setText("Please fill email");
            return false;
        }
        if(passwordField.getText().isEmpty()){
            errorLabel.setText("Please fill password");
            return false;
        }
        return true;
    }

}

