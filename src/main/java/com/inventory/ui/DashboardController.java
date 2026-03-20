package com.inventory.ui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node;

public class DashboardController {

    @FXML
    private BorderPane root;

    @FXML
    public void initialize() {
        showDashboard();   // ✅ load dashboard FIRST
    }

    @FXML
    private void showProducts() {
        load("product_view.fxml");
    }

    @FXML
    private void showDashboard() {
        load("dashboard_view.fxml");
    }

    @FXML
    private void showReports() {
        load("report_view.fxml");
    }

    private void load(String file) {
        try {
            Node node = FXMLLoader.load(getClass().getResource("/fxml/" + file));
            root.setCenter(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}