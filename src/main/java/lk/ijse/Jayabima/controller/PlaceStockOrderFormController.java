package lk.ijse.Jayabima.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.Jayabima.bo.BOFactory;
import lk.ijse.Jayabima.bo.custom.ItemBO;
import lk.ijse.Jayabima.bo.custom.StockOrderBO;
import lk.ijse.Jayabima.bo.custom.SupplierBO;
import lk.ijse.Jayabima.db.DbConnection;
import lk.ijse.Jayabima.dto.ItemDto;
import lk.ijse.Jayabima.dto.PlaceStockOrderDto;
import lk.ijse.Jayabima.dto.SupplierDto;
import lk.ijse.Jayabima.dto.tm.StockCartTm;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlaceStockOrderFormController {

    @FXML
    private JFXComboBox<String> cmbItemCode;

    @FXML
    private JFXComboBox<String> cmbSupplierId;

    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colDescription;

    @FXML
    private TableColumn<?, ?> colBrand;

    @FXML
    private TableColumn<?, ?> colItemCode;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colSupId;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblSupplierName;

    @FXML
    private Label lblBrandName;

    @FXML
    private AnchorPane rootHome;

    @FXML
    private TableView<StockCartTm> tblOrderCart;

    @FXML
    private TextField txtQty;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    private SupplierBO supplierBO = (SupplierBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.SUPPLIER);
    private ItemBO itemBO = (ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.ITEM);

    private StockOrderBO stockOrderBO = (StockOrderBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.STOCK_ORDER);
    private ObservableList<StockCartTm> obList = FXCollections.observableArrayList();

    public void initialize() {
        setDateAndTime();
        setDate();
        generateNextStockOrderId();
        loadItemCodes();
        loadSupplierIds();
        setCellValueFactory();

    }

    private void setCellValueFactory() {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colSupId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
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

    private void setDate() {
        lblOrderDate.setText(String.valueOf(LocalDate.now()));
    }
    private void loadSupplierIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<SupplierDto> idList = supplierBO.getAllSupplier();

            for (SupplierDto dto : idList) {
                obList.add(dto.getSupId());
            }

            cmbSupplierId.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void loadItemCodes() {
        ObservableList<String> obList = FXCollections.observableArrayList();
        try {
            List<ItemDto> itemDtos = itemBO.getAllItem();

            for (ItemDto dto : itemDtos) {
                obList.add(dto.getItemCode());
            }
            cmbItemCode.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setRemoveBtnAction(Button btn) {
        btn.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if (type.orElse(no) == yes) {
                int focusedIndex = tblOrderCart.getSelectionModel().getSelectedIndex();

                obList.remove(focusedIndex);
                tblOrderCart.refresh();
            }
        });
    }

    private void generateNextStockOrderId() {
        try {
            String orderId = stockOrderBO.generateStockOrderID();
            lblOrderId.setText(orderId);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void cmbSupplierOnAction(ActionEvent event) {
        String id = cmbSupplierId.getValue();
//        CustomerModel customerModel = new CustomerModel();
        try {
            SupplierDto supplierDto = supplierBO.searchSupplier(id);
            lblSupplierName.setText(supplierDto.getSupName());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void cmbItemOnAction(ActionEvent event) {
        String code = cmbItemCode.getValue();

        txtQty.requestFocus();
        try {
            ItemDto dto = itemBO.searchItem(code);
            lblBrandName.setText(dto.getItemName());
            lblDescription.setText(dto.getItemDesc());
            lblQtyOnHand.setText(String.valueOf(dto.getItemQty()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        String code = cmbItemCode.getValue();
        String name = lblBrandName.getText();
        String description = lblDescription.getText();
        int qty = Integer.parseInt(txtQty.getText());
        String supName = lblSupplierName.getText();
        Button btn = new Button("Remove");

        setRemoveBtnAction(btn);
        btn.setCursor(Cursor.HAND);

        if (!obList.isEmpty()) {
            for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
                if (colItemCode.getCellData(i).equals(code)) {
                    int col_qty = (int) colQty.getCellData(i);
                    qty += col_qty;

                    obList.get(i).setQty(qty);

                    tblOrderCart.refresh();
                    return;
                }
            }
        }
        var cartTm = new StockCartTm(code, name, description, qty, supName, btn);
        obList.add(cartTm);

        tblOrderCart.setItems(obList);
        txtQty.clear();
    }

    @FXML
    void btnBuyNowOnAction(ActionEvent event) throws JRException, SQLException {
        String stockOrder_id = lblOrderId.getText();
        String sup_id = cmbSupplierId.getValue();
        LocalDate stockOrder_date = LocalDate.parse(lblOrderDate.getText());
        
        List<StockCartTm> stockCartTmList = new ArrayList<>();
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            StockCartTm stockCartTm = obList.get(i);
            stockCartTmList.add(stockCartTm);
        }
        System.out.println("Place order form controller : "+ stockCartTmList);
        var placeStockOrderDto = new PlaceStockOrderDto(stockOrder_id, sup_id, stockOrder_date, stockCartTmList);
        try {
            boolean isSuccess = stockOrderBO.placeStockOrder(placeStockOrderDto);
            if (isSuccess) {
                new Alert(Alert.AlertType.CONFIRMATION, "Stock Order Success").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void btnNewCustomerOnAction(ActionEvent event) throws IOException {
        Parent anchorPane = FXMLLoader.load(getClass().getResource("/view/supplier_form.fxml"));
        Scene scene = new Scene(anchorPane);

        Stage stage = new Stage();
        stage.setTitle("Customer Manage");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        btnAddToCartOnAction(event);
    }
    @FXML
    void btnStockOrderDetailsOnAction(ActionEvent event) throws IOException, JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/reports/stockorderdetail.jrxml");
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
