package lk.ijse.Jayabima.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Jayabima.bo.BOFactory;
import lk.ijse.Jayabima.bo.custom.RegisterBO;
import lk.ijse.Jayabima.dto.RegisterDto;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class SignUpPageController {
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtMobile;
    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtRepeatPassword;
    @FXML
    private AnchorPane rootNode;

    RegisterBO registerBO = (RegisterBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.REGISTER);

    public void initialize(){

    }
    private void clearFields() {
        txtUserName.setText("");
        txtMobile.setText("");
        txtPassword.setText("");
        txtRepeatPassword.setText("");
    }



    @FXML
    void btnSignInFormOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/login_form.fxml"));
        Scene scene =new Scene(rootNode);
        Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Jayabima Hardware");
        primaryStage.show();
    }

    @FXML
    void btnSignUpOnAction(ActionEvent event) throws IOException {
        String name = txtUserName.getText();
        String mobile = txtMobile.getText();
        String password = txtPassword.getText();
        String repeatPassword = txtRepeatPassword.getText();
            try {
                if (!validateUserDetails()) {
                    return;
                }
                if(!password.equals(repeatPassword)) {
                    showErrorNotification("Password do not match !", "The password should be equal !");
                    return;
                }
                clearFields();
                var dto = new RegisterDto(name, mobile, password, repeatPassword);
                boolean isSaved = registerBO.saveUser(dto);
                if (isSaved){
                    new Alert(Alert.AlertType.CONFIRMATION,"User Saved").show();
                }
            }catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                clearFields();
            }
    }
    public boolean validateUserDetails() {
        boolean isValid = true;

        if (!Pattern.matches("[A-Za-z]{4,}", txtUserName.getText())) {
            showErrorNotification("Invalid Username", "The username you entered is invalid");
            isValid = false;
        }

        if (!Pattern.matches("\\d{10}", txtMobile.getText())) {
            showErrorNotification("Invalid Mobile Number", "The mobile number you entered is invalid");
            isValid = false;
        }

        if (!Pattern.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", txtPassword.getText())) {
            showErrorNotification("Invalid Password", "The password you entered is invalid");
            isValid = false;
        }
        return isValid;
    }

    private static void showErrorNotification(String title, String text) {
        Notifications.create()
                .title(title)
                .text(text)
                .showError();
    }
}
