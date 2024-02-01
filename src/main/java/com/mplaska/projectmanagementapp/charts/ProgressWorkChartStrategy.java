package com.mplaska.projectmanagementapp.charts;

import com.mplaska.projectmanagementapp.model.coredata.Project;
import com.mplaska.projectmanagementapp.model.chartsdata.ProgressWorkChartData;
import javafx.geometry.Side;
import javafx.scene.chart.*;
import javafx.util.Pair;

import java.util.ArrayList;

public class ProgressWorkChartStrategy implements ChartStrategy {
    private ProgressWorkChartData progressWorkChartData;

    @Override
    public void setOperatedProject(Project operatedProject) {
        progressWorkChartData = new ProgressWorkChartData(operatedProject);
    }

    @Override
    public Chart generateChart() {
        BarChart<Number, String> progressWorkChart = new BarChart<>(new NumberAxis(), new CategoryAxis());
        NumberAxis xAxis = (NumberAxis) progressWorkChart.getXAxis();
        CategoryAxis yAxis = (CategoryAxis) progressWorkChart.getYAxis();

        progressWorkChart.setTitle("Progress Work Chart");
        progressWorkChart.setStyle("-fx-font-weight: bold");
        progressWorkChart.setLegendVisible(false);

        xAxis.setLabel("Progress (%)");
        yAxis.setLabel("Tasks");

        xAxis.setSide(Side.TOP);
        xAxis.setTickUnit(5);
        xAxis.setUpperBound(100);
        xAxis.setAutoRanging(false);


        ArrayList<Pair<String, Double>> dataset = progressWorkChartData.createDataset();

        XYChart.Series<Number, String> series = new XYChart.Series<>();

        for (int i = 0; i < dataset.size(); ++i) {
            series.getData().add(new XYChart.Data<>(dataset.get(i).getValue(),
                    "Task " + (i+1) + " [" + dataset.get(i).getKey() + "]"));
        }
        progressWorkChart.getData().add(series);

        // function which determines gap
        int x = series.getData().size();
        double barGap = 0;
        if (x <= 4 || x >= 12) {
            barGap = (55.0 / 4.0 * x - 115.0) / (3.0 / 8.0 * x * x - 5.0 / 2.0 * x + 1);
        } else {
            barGap = (5.0 / 6.0 * x - 10.0) / (1.0 / 32.0 * x * x - 11.0 / 24.0 * x + 1);
        }
        progressWorkChart.setBarGap(barGap);

        for (XYChart.Data<Number, String> data : series.getData()) {
            if (data.getXValue().doubleValue() <= 30) {
                data.getNode().setStyle("-fx-bar-fill: rgb(255,0,0);");
            } else if (data.getXValue().doubleValue() < 80) {
                data.getNode().setStyle("-fx-bar-fill: rgb(128,0,128);");
            } else {
                data.getNode().setStyle("-fx-bar-fill: rgb(0,255,0);");
            }
        }

        return progressWorkChart;
    }

    @Override
    public ArrayList<String> getInfo() {
        return new ArrayList<>();
    }
}
