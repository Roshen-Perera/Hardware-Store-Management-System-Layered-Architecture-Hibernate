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
import lk.ijse.Jayabima.bo.custom.LoginBO;
import lk.ijse.Jayabima.dao.DAOFactory;
import lk.ijse.Jayabima.db.DbConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPageController {

    @FXML
    private AnchorPane rootNode;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtUserName;

    LoginBO loginBO = (LoginBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.LOGIN);
    private void clearFields() {
        txtUserName.setText("");
        txtPassword.setText("");
    }



    @FXML
    void btnForgotPasswordOnAction(ActionEvent event) {

    }

    @FXML
    void btnSignInOnAction(ActionEvent event) throws IOException, SQLException{
        String username = txtUserName.getText();
        String password = txtPassword.getText();
        try {
            boolean login = loginBO.login(username, password);
            if(username.isEmpty() || password.isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Empty").show();
                return;
            }
            if (login) {
                Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/buttonbar_form.fxml"));
                Scene scene =new Scene(rootNode);
                Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();
                primaryStage.setScene(scene);
                primaryStage.setTitle("Jayabima Hardware");
            } else {
                new Alert(Alert.AlertType.ERROR, "oops! credentials are wrong!").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage());
            clearFields();
        }
        ButtonBarFormController.staticLabel.setText("Welcome "+txtUserName.getText()+" !");
    }

    @FXML
    void btnSignUpFormOnAction(ActionEvent event) throws IOException {
        Parent rootNode = FXMLLoader.load(this.getClass().getResource("/view/signup_form.fxml"));
        Scene scene =new Scene(rootNode);
        Stage primaryStage = (Stage) this.rootNode.getScene().getWindow();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Jayabima Hardware");
    }
}
