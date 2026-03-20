package com.inventory.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.chart.PieChart;
import javafx.collections.FXCollections;

import com.inventory.service.ProductService;
import com.inventory.entity.Product;

public class DashboardViewController {

    @FXML private Label totalProducts;
    @FXML private Label totalStock;
    @FXML private Label lowStock;
    @FXML private PieChart pieChart;

    private ProductService service = new ProductService();

    @FXML
    public void initialize() {

        var products = service.getAll();

        int total = products.size();
        int stockSum = 0;
        int low = 0;

        var data = FXCollections.<PieChart.Data>observableArrayList();

        for (Product p : products) {
            stockSum += p.getStock();

            if (p.getStock() < 5) {
                low++;
            }

            data.add(new PieChart.Data(p.getName(), p.getStock()));
        }

        totalProducts.setText(String.valueOf(total));
        totalStock.setText(String.valueOf(stockSum));
        lowStock.setText(String.valueOf(low));

        pieChart.setData(data);
    }
}