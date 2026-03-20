package com.inventory.ui;

import com.inventory.entity.Product;
import com.inventory.service.ProductService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ProductController {

    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private TextField stockField;

    @FXML private TableView<Product> table;
    @FXML private TableColumn<Product, String> nameCol;
    @FXML private TableColumn<Product, Double> priceCol;
    @FXML private TableColumn<Product, Integer> stockCol;

    private final ProductService service = new ProductService();

    private Product selectedProduct;   // ⭐ IMPORTANT

    @FXML
    public void initialize() {

        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        priceCol.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getPrice()));
        stockCol.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getStock()));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // ⭐ SELECT ROW
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectedProduct = newVal;

                nameField.setText(newVal.getName());
                priceField.setText(String.valueOf(newVal.getPrice()));
                stockField.setText(String.valueOf(newVal.getStock()));
            }
        });

        load();
    }

    private void load() {
        table.setItems(FXCollections.observableArrayList(service.getAll()));
    }

    @FXML
    private void add() {
        Product p = new Product();
        p.setName(nameField.getText());
        p.setPrice(Double.parseDouble(priceField.getText()));
        p.setStock(Integer.parseInt(stockField.getText()));

        service.save(p);

        clearFields();
        load();
    }

    // ✅ FULL FIXED UPDATE
    @FXML
    private void update() {

        if (selectedProduct == null) {
            showAlert("Select product first!");
            return;
        }

        selectedProduct.setName(nameField.getText());
        selectedProduct.setPrice(Double.parseDouble(priceField.getText()));
        selectedProduct.setStock(Integer.parseInt(stockField.getText()));

        service.update(selectedProduct);

        clearFields();
        load();
    }

    @FXML
    private void delete() {

        if (selectedProduct == null) {
            showAlert("Select product first!");
            return;
        }

        service.delete(selectedProduct);

        clearFields();
        load();
    }

    // ✅ SELL FEATURE + LOW STOCK ALERT
    @FXML
    private void sell() {

        if (selectedProduct == null) {
            showAlert("Select product first!");
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setHeaderText("Enter quantity to sell:");

        dialog.showAndWait().ifPresent(qty -> {

            int quantity = Integer.parseInt(qty);

            if (selectedProduct.getStock() < quantity) {
                showAlert("Not enough stock!");
                return;
            }

            selectedProduct.setStock(selectedProduct.getStock() - quantity);
            service.update(selectedProduct);

            // LOW STOCK WARNING
            if (selectedProduct.getStock() < 5) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Low Stock ⚠");
                alert.setContentText(selectedProduct.getName() + " is running low!");
                alert.show();
            }

            load();
        });
    }

    private void clearFields() {
        nameField.clear();
        priceField.clear();
        stockField.clear();
        selectedProduct = null;
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(msg);
        alert.show();
    }
}