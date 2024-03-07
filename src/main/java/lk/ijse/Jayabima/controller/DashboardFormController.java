package lk.ijse.Jayabima.controller;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;
import lk.ijse.Jayabima.dao.DAOFactory;
import lk.ijse.Jayabima.dao.custom.DashboardDAO;
import lk.ijse.Jayabima.dao.custom.impl.DashboardDAOImpl;
import lk.ijse.Jayabima.db.DbConnection;
import lk.ijse.Jayabima.dto.CustomerDto;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class DashboardFormController {


    @FXML
    private Label lblCustomer;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblEmployee;

    @FXML
    private Label lblItems;

    @FXML
    private Label lblItemOrders;

    @FXML
    private Label lblStockOrders;

    @FXML
    private Label lblSupplier;

    @FXML
    private Label lblTime;

    DashboardDAO dashboardDAO = (DashboardDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.DASHBOARD);

    public void initialize() throws SQLException {
        setDateAndTime();
        countCustomer();
        countEmployee();
        countSupplier();
        countItem();
        countStockOrder();
        countItemOrder();
    }
    private void setDateAndTime(){
        Platform.runLater(() -> {
            lblDate.setText(String.valueOf(LocalDate.now()));

            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), event -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
                String timeNow = LocalTime.now().format(formatter);
                lblTime.setText(timeNow);
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        });
    }

    private void countCustomer() throws SQLException {
        int count = dashboardDAO.countCustomer();
        lblCustomer.setText(String.valueOf(count));

    }

    private void countEmployee() throws SQLException {
        int count = dashboardDAO.countEmployee();
        lblEmployee.setText(String.valueOf(count));
    }

    private void countSupplier() throws SQLException {
        int count = dashboardDAO.countSupplier();
        lblSupplier.setText(String.valueOf(count));
    }

    private void countItem() throws SQLException {
        int count = dashboardDAO.countItem();
        lblItems.setText(String.valueOf(count));
    }

    private void countStockOrder() throws SQLException {
        int count = dashboardDAO.countStockOrder();
        lblItemOrders.setText(String.valueOf(count));
    }

    private void countItemOrder() throws SQLException {
        int count = dashboardDAO.countItemOrder();
        lblStockOrders.setText(String.valueOf(count));
    }
}
