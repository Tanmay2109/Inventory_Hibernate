package com.inventory.ui;

import com.inventory.entity.Product;
import com.inventory.service.ProductService;
import javafx.fxml.FXML;
import javafx.scene.chart.*;

public class ReportController {

    @FXML
    private BarChart<String, Number> barChart;

    private ProductService service = new ProductService();

    @FXML
    public void initialize() {

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Product Prices");

        for (Product p : service.getAll()) {
            series.getData().add(new XYChart.Data<>(p.getName(), p.getPrice()));
        }

        barChart.getData().add(series);
    }
}