package lk.ijse.Jayabima.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import lk.ijse.Jayabima.bo.BOFactory;
import lk.ijse.Jayabima.bo.custom.SupplierBO;
import lk.ijse.Jayabima.db.DbConnection;
import lk.ijse.Jayabima.dto.SupplierDto;
import lk.ijse.Jayabima.dto.tm.SupplierTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.Notifications;

import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

public class SupplierFormController {

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colMobile;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private AnchorPane rootHome;

    @FXML
    private TableView<SupplierTm> tblSupplier;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtMobile;

    @FXML
    private TextField txtName;

    @FXML
    private Label lblId;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;
    private SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtDescription.setText("");
        txtMobile.setText("");
    }

    public void initialize() {
        setCellValueFactory();
        loadAllSupplier();
        generateNextSupplierID();
        tableListener();
        setDateAndTime();
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

    private boolean btnClearPressed = false;

    private void  generateNextSupplierID(){
        try {
            String previousSupplierID = lblId.getText();
            String supplierID = supplierBO.generateSupplierID();
            lblId.setText(supplierID);
            clearFields();
            if (btnClearPressed){
                lblId.setText(previousSupplierID);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void tableListener() {
        tblSupplier.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
//            System.out.println(newValue);
            setData(newValue);
        });
    }

    private void setData(SupplierTm row) {
        lblId.setText(row.getSupId());
        txtName.setText(row.getSupName());
        txtDescription.setText(row.getSupDesc());
        txtMobile.setText(row.getSupMobile());
    }



    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("supName"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("supDesc"));
        colMobile.setCellValueFactory(new PropertyValueFactory<>("supMobile"));
    }

    private void loadAllSupplier() {

        ObservableList<SupplierTm> obList = FXCollections.observableArrayList();

        try {
            List<SupplierDto> dtoList = supplierBO.getAllSupplier();

            for (SupplierDto dto : dtoList) {
                obList.add(
                        new SupplierTm(
                                dto.getSupId(),
                                dto.getSupName(),
                                dto.getSupDesc(),
                                dto.getSupMobile()
                        )
                );
            }
            tblSupplier.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void btnAddSupplierOnAction(ActionEvent event) {
        String id = lblId.getText();
        String name = txtName.getText();
        String desc = txtDescription.getText();
        String mobile = txtMobile.getText();

        try {
            if (!validateSupplierDetails()) {
                return;
            }
            clearFields();
            var dto = new SupplierDto(id, name, desc, mobile);
            boolean isSaved = supplierBO.saveSupplier(dto);
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Saved !").show();
                clearFields();
                loadAllSupplier();
                generateNextSupplierID();
            }else {
                new Alert(Alert.AlertType.ERROR, "Customer details not saved").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnClearSupplierOnAction(ActionEvent event) {
        clearFields();
        generateNextSupplierID();
    }

    @FXML
    void btnDeleteSupplierOnAction(ActionEvent event) throws ClassNotFoundException {
        String id = lblId.getText();

        try{
            boolean isDeleted = supplierBO.deleteSupplier(id);
            if(isDeleted) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
                clearFields();
                loadAllSupplier();
                generateNextSupplierID();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION, "customer not deleted!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id = txtId.getText();

        try {
            SupplierDto supplierDto = supplierBO.searchSupplier(id);
            if (supplierDto != null) {
                txtId.setText(supplierDto.getSupId());
                txtName.setText(supplierDto.getSupName());
                txtDescription.setText(supplierDto.getSupDesc());
                txtMobile.setText(supplierDto.getSupMobile());
            } else {
                new Alert(Alert.AlertType.ERROR, "customer not found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateSupplierOnAction(ActionEvent event) {
        String id = lblId.getText();
        String name = txtName.getText();
        String desc = txtDescription.getText();
        String mobile = txtMobile.getText();

        try {
            if (!validateSupplierDetails()) {
                return;
            }
            clearFields();
            var dto = new SupplierDto(id, name, desc, mobile);
            boolean isSaved = supplierBO.updateSupplier(dto);
            if(isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Supplier Saved !").show();
                clearFields();
                loadAllSupplier();
            }else {
                new Alert(Alert.AlertType.ERROR, "Customer details not saved").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    public boolean validateSupplierDetails() {
        boolean isValid = true;

        if (!Pattern.matches("^[a-zA-Z0-9\\s]*$", txtName.getText())) {
            showErrorNotification("Invalid Supplier Name", "The supplier name you entered is invalid");
            isValid = false;

        }

        if (!Pattern.matches("^[a-zA-Z0-9 ,.]+$", txtDescription.getText())) {
            showErrorNotification("Invalid Description", "The description you entered is invalid");
            isValid = false;

        }

        if (!Pattern.matches("^(?:0|94|\\+94|0094)?(?:(11|21|23|24|25|26|27|31|32|33|34|35|36|37|38|41|45|47|51|52|54|55|57|63|65|66|67|81|91)(0|2|3|4|5|7|9)|7(0|1|2|4|5|6|7|8)\\d)\\d{6}$", txtMobile.getText())) {
            showErrorNotification("Invalid Mobile Number", "The mobile number you entered is invalid");
            isValid = false;
        }
        return isValid;
    }

    private void showErrorNotification(String title, String text) {
        Notifications.create()
                .title(title)
                .text(text)
                .showError();
    }

    @FXML
    void btnGetReportOnAction(ActionEvent event) throws SQLException, JRException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/reports/SupplierReport.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);

        JasperReport jasperReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                null,
                DbConnection.getInstance().getConnection()
        );
        JasperViewer.viewReport(jasperPrint, false);
    }
}
