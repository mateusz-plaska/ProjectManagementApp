package com.mplaska.projectmanagementapp.charts;

import com.mplaska.projectmanagementapp.model.coredata.Project;
import javafx.scene.chart.Chart;

import java.util.ArrayList;

public interface ChartStrategy {
    void setOperatedProject(Project operatedProject);
    Chart generateChart();

    ArrayList<String> getInfo();
}


