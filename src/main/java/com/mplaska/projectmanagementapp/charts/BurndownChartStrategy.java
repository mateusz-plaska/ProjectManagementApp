package com.mplaska.projectmanagementapp.charts;

import com.mplaska.projectmanagementapp.model.coredata.Project;
import com.mplaska.projectmanagementapp.model.chartsdata.BurndownChartData;
import javafx.scene.chart.Chart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.Pair;

import java.time.LocalDate;
import java.util.ArrayList;

public class BurndownChartStrategy implements ChartStrategy {
    private BurndownChartData burndownChartData;
    @Override
    public void setOperatedProject(Project operatedProject) {
        burndownChartData = new BurndownChartData(operatedProject);
    }

    @Override
    public Chart generateChart() {
        LineChart<Number, Number> burndownChart = new LineChart<>(new NumberAxis(), new NumberAxis());
        NumberAxis xAxis = (NumberAxis) burndownChart.getXAxis();
        NumberAxis yAxis = (NumberAxis) burndownChart.getYAxis();

        burndownChart.setTitle("Burndown Chart");
        burndownChart.setStyle("-fx-font-weight: bold");
        xAxis.setLabel("Day");
        yAxis.setLabel("Amount of Tasks");

        xAxis.setForceZeroInRange(false);
        xAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(xAxis) {
            @Override
            public String toString(Number object) {
                return LocalDate.ofEpochDay(object.intValue()).toString();
            }
        });


        yAxis.setTickUnit(1);
        yAxis.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxis) {
            @Override
            public String toString(Number object) {
                // Hide label for not integer values
                if (object.intValue() == object.doubleValue()) {
                    return super.toString(object);
                } else {
                    return "";
                }
            }
        });


        ArrayList<Pair<String, ArrayList<Pair<Number, Number>>>> dataset = burndownChartData.createDataset();

        for (var series : dataset) {
            XYChart.Series<Number, Number> chartSeries = new XYChart.Series<>();
            chartSeries.setName(series.getKey());

            for (var data : series.getValue()) {
                chartSeries.getData().add(new XYChart.Data<>(data.getKey(), data.getValue()));
            }
            burndownChart.getData().add(chartSeries);
        }

        return burndownChart;
    }

    @Override
    public ArrayList<String> getInfo() {
        return burndownChartData.getInformation();
    }
}
