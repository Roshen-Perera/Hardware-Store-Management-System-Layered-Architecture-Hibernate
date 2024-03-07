package lk.ijse.Jayabima.controller;

import com.jfoenix.controls.JFXComboBox;
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
import lk.ijse.Jayabima.bo.custom.ItemBO;
import lk.ijse.Jayabima.bo.custom.SupplierBO;
import lk.ijse.Jayabima.db.DbConnection;
import lk.ijse.Jayabima.dto.ItemDto;
import lk.ijse.Jayabima.dto.SupplierDto;
import lk.ijse.Jayabima.dto.tm.ItemTm;
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

public class ItemFormController {

    @FXML
    private AnchorPane rootHome;
    @FXML
    private TextField txtItemDesc;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtItemQty;

    @FXML
    private JFXComboBox<String> cmdSupplierId;

    @FXML
    private Label lblSupplierName;

    @FXML
    private Label lblSupplierId;

    @FXML
    private TextField txtItemUnitPrice;

    @FXML
    private TextField txtItemCode;

    @FXML
    private TableView<ItemTm> tblItem;

    @FXML
    private TableColumn<?, ?> colBrand;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableColumn<?, ?> colSupplierId;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblId;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    private ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);
    private SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);



    public void initialize() {
        loadAllItems();
        setCellValueFactory();
        generateNextItemID();
        tableListener();
        setDateAndTime();
        loadSupplierIds();
    }

    private void loadSupplierIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<SupplierDto> idList = supplierBO.getAllSupplier();

            for (SupplierDto dto : idList) {
                obList.add(dto.getSupId());
            }
            cmdSupplierId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    private void  generateNextItemID(){
        try {
            String previousCustomerID = lblId.getText();
            String customerID = itemBO.generateItemID();
            lblId.setText(customerID);
            clearFields();
            if (btnClearPressed){
                lblId.setText(previousCustomerID);
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void tableListener() {
        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValued, newValue) -> {
            System.out.println(newValue);
            setData(newValue);
        });
    }

    private void setData(ItemTm row) {
        lblId.setText(row.getItemCode());
        txtItemName.setText(row.getItemName());
        txtItemDesc.setText(row.getItemDesc());
        txtItemQty.setText(String.valueOf(row.getItemQty()));
        txtItemUnitPrice.setText(String.valueOf(row.getItemUnitPrice()));
        lblSupplierId.setText(row.getSupplierId());
    }

    private void clearFields() {
        txtItemCode.setText("");
        txtItemName.setText("");
        txtItemDesc.setText("");
        txtItemQty.setText("");
        txtItemUnitPrice.setText("");
        lblSupplierId.setText("");
        lblSupplierName.setText("");
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colBrand.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("itemDesc"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("itemUnitPrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("itemQty"));
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));

    }

    private void loadAllItems() {
//        var model = new ItemModel();
        ObservableList<ItemTm> obList = FXCollections.observableArrayList();
        try {
            List<ItemDto> dtoList = itemBO.getAllItem();

            for (ItemDto dto : dtoList) {
                obList.add(new ItemTm(
                        dto.getItemCode(),
                        dto.getItemName(),
                        dto.getItemDesc(),
                        dto.getItemQty(),
                        dto.getItemUnitPrice(),
                        dto.getSupplierId()
                ));
            }
            tblItem.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void btnAddItemOnAction(ActionEvent event) {
        String item_code = lblId.getText();
        String item_name = txtItemName.getText();
        String item_desc = txtItemDesc.getText();
        int item_qty = Integer.parseInt(txtItemQty.getText());
        double item_unitPrice = Double.parseDouble(txtItemUnitPrice.getText());
        String supplierId = lblSupplierId.getText();


        try {
            if(!validateItemDetails()) {
                return;
            }
            clearFields();
            var dto = new ItemDto(item_code, item_name, item_desc, item_qty, item_unitPrice, supplierId);
            boolean isSaved = itemBO.saveItem(dto);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "Item Saved").show();
            }
            loadAllItems();
            clearFields();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnClearItemOnAction(ActionEvent event) {
        clearFields();
        generateNextItemID();
    }

    @FXML
    void btnDeleteItemOnAction(ActionEvent event) throws ClassNotFoundException {
        String item_code = lblId.getText();
        try {
            boolean isDeleted = itemBO.deleteItem(item_code);
            if(isDeleted){
                new Alert(Alert.AlertType.CONFIRMATION,"Item Deleted").show();
                loadAllItems();
                clearFields();
            } else {
                new Alert(Alert.AlertType.CONFIRMATION,"Item Not Deleted").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) {
        String id = txtItemCode.getText();
        try {
            ItemDto itemDto = itemBO.searchItem(id);
            if (itemDto != null) {
                txtItemCode.setText(itemDto.getItemCode());
                txtItemName.setText(itemDto.getItemName());
                txtItemDesc.setText(itemDto.getItemDesc());
                txtItemQty.setText(String.valueOf(itemDto.getItemQty()));
                txtItemUnitPrice.setText(String.valueOf(itemDto.getItemUnitPrice()));
                lblSupplierId.setText(itemDto.getSupplierId());
            }else {
                new Alert(Alert.AlertType.INFORMATION, "Customer Saved").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }


    }

    @FXML
    void btnUpdateItemOnAction(ActionEvent event) {
        String item_code = lblId.getText();
        String item_name = txtItemName.getText();
        String item_desc = txtItemDesc.getText();
        int item_qty = Integer.parseInt(txtItemQty.getText());
        double item_unitPrice = Double.parseDouble(txtItemUnitPrice.getText());
        String supplierId = lblSupplierId.getText();

        try {
            if(!validateItemDetails()) {
                return;
            }
            clearFields();
            var dto = new ItemDto(item_code, item_name, item_desc, item_qty, item_unitPrice, supplierId);
            boolean isUpdated = itemBO.updateItem(dto);
            if (isUpdated) {
                new Alert(Alert.AlertType.CONFIRMATION, "Items are updated").show();
                loadAllItems();
                clearFields();
            }else {
                new Alert(Alert.AlertType.CONFIRMATION, "Items are not updated").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

    }

    @FXML
    void cmbSupplierOnAction(ActionEvent event) {
        String id = cmdSupplierId.getValue();
//        CustomerModel customerModel = new CustomerModel();
        try {
            SupplierDto supplierDto = supplierBO.searchSupplier(id);
            lblSupplierId.setText(supplierDto.getSupId());
            lblSupplierName.setText(supplierDto.getSupName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validateItemDetails() {
        boolean isValid = true;

        if (!Pattern.matches("^[a-zA-Z0-9\\s]*$", txtItemName.getText())) {
            showErrorNotification("Invalid Item Name", "The item name you entered is invalid");
            isValid = false;
        }

        if (!Pattern.matches("^[a-zA-Z0-9 ,.]+$", txtItemDesc.getText())) {
            showErrorNotification("Invalid Description", "The description you entered is invalid");
            isValid = false;
        }

        if (!Pattern.matches("\\d+", txtItemQty.getText())) {
            showErrorNotification("Invalid Quantity", "The quantity you entered is invalid");
            isValid = false;
        }

        if (!Pattern.matches("^[0-9]+\\.?[0-9]*$", txtItemUnitPrice.getText())) {
            showErrorNotification("Invalid Salary", "The salary you entered is invalid");
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
        InputStream resourceAsStream = getClass().getResourceAsStream("/reports/ItemReport.jrxml");
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
