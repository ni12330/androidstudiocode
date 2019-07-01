package com.example.android_project.home;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android_project.R;
import com.example.android_project.chart.Chart;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;
import static com.example.android_project.Login_user.LoginMemberDTO.user_id;
import static com.example.android_project.home.Home.efficiency;

public class Home_Chart extends AppCompatActivity {

    Button btn_back;
    String charts = "";
    int year_oil_cost = 0;
    int year_repair_cost = 0;
    int year_oil = 0;
    int year_mileage = 0;

    TextView year_oil_cost_view, year_repair_cost_view, total_cost_view;
    TextView avg_efficiency_view, year_oil_view, year_mileage_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_chart);

        year_oil_cost_view = findViewById(R.id.year_oil_cost_view);
        year_repair_cost_view = findViewById(R.id.year_repair_cost_view);
        total_cost_view = findViewById(R.id.total_cost_view);
        avg_efficiency_view = findViewById(R.id.avg_efficiency_view);
        year_oil_view = findViewById(R.id.year_oil_view);
        year_mileage_view = findViewById(R.id.year_mileage_view);

        Chart chart = new Chart(user_id);

        try {
            charts = chart.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        getOilCostChart(0);
        getRepairCostChart(12);
        getOil(24);
        getMonthMileageChart(36);

        year_oil_cost_view.setText(String.valueOf(year_oil_cost));
        year_repair_cost_view.setText(String.valueOf(year_repair_cost));
        total_cost_view.setText(String.valueOf(year_oil_cost + year_repair_cost));
        avg_efficiency_view.setText(efficiency);
        year_oil_view.setText(String.valueOf(year_oil));
        year_mileage_view.setText(String.valueOf(year_mileage));

        btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void getOil(int startPoint) {
        String[] sp = charts.split(",");

        int[] oil = new int[12];
        int max = Integer.parseInt(sp[startPoint].trim());

        for(int i=startPoint; i<startPoint+12; i++) {
            oil[i-startPoint] = Integer.parseInt(sp[i].trim());
            if(max < oil[i-startPoint]) {
                max = oil[i-startPoint];
            }
            year_oil += oil[i-startPoint];
        }
    }

    public void getOilCostChart(int startPoint) {
        //Open up MainActivity.java to reference LineChartView.
        LineChartView lineChartView = findViewById(R.id.oilCostChart);

        //나타내고 싶은 데이터 초기화
        String[] axisData = {"1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월",
                "10월", "11월", "12월"};

        String[] sp = charts.split(",");

        int[] yAxisData = new int[12];
        int max = Integer.parseInt(sp[startPoint].trim());

        for(int i=startPoint; i<startPoint+12; i++) {
            yAxisData[i-startPoint] = Integer.parseInt(sp[i].trim());
            if(max < yAxisData[i-startPoint]) {
                max = yAxisData[i-startPoint];
            }
            year_oil_cost += yAxisData[i-startPoint];
        }

        // Next declare 2 types of List like the following.
        //These lists will be used to hold the data for Axis and Y-Axis.
        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();

        // Declare and initialize the line which appears inside graph chart, this line will hold the values of Y-Axis.
        Line line = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0"));


        //리스트에 추가
        for(int i = 0; i < axisData.length; i++){
            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
        }

        for (int i = 0; i < yAxisData.length; i++){
            yAxisValues.add(new PointValue(i, yAxisData[i]));
        }

        //Declare a list of a type Line.
        //This list will hold the line of the graph chart.
        List lines = new ArrayList();
        lines.add(line);

        // Now you can add the graph line to the overall data chart.
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //Now you need to add the following code to be able to see the Android line chart.
        lineChartView.setLineChartData(data);

        Axis axis = new Axis();
        axis.setValues(axisValues);
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
        data.setAxisYLeft(yAxis);

        //X축 텍스트 색, 크기
        axis.setTextSize(5);
        axis.setTextColor(Color.parseColor("#03A9F4"));

        //Y축 텍스트 색, 크기
        yAxis.setTextColor(Color.parseColor("#03A9F4"));
        yAxis.setTextSize(10);



        //Y축 이름주기
        /*yAxis.setName("Sales in millions");*/


        //If you had noticed that Y-Axis data inside the line chart doesn't show it's full data based on
        // what you initialized in the beginning. You can fix it by adding the following code.
        //What you did here is you explicitly specified a value
        // that is higher than the Y-Axis actual data which is in this example is (90).
        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.top = max + max/3;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);
    }

    public void getRepairCostChart(int startPoint) {
        //Open up MainActivity.java to reference LineChartView.
        LineChartView lineChartView = findViewById(R.id.monthRepairChart);

        //나타내고 싶은 데이터 초기화
        String[] axisData = {"1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월",
                "10월", "11월", "12월"};

        String[] sp = charts.split(",");

        int[] yAxisData = new int[12];
        int max = Integer.parseInt(sp[startPoint].trim());

        for(int i=startPoint; i<startPoint+12; i++) {
            yAxisData[i-startPoint] = Integer.parseInt(sp[i].trim());
            if(max < yAxisData[i-startPoint]) {
                max = yAxisData[i-startPoint];
            }

            year_repair_cost += yAxisData[i-startPoint];
        }

        // Next declare 2 types of List like the following.
        //These lists will be used to hold the data for Axis and Y-Axis.
        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();

        // Declare and initialize the line which appears inside graph chart, this line will hold the values of Y-Axis.
        Line line = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0"));


        //리스트에 추가
        for(int i = 0; i < axisData.length; i++){
            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
        }

        for (int i = 0; i < yAxisData.length; i++){
            yAxisValues.add(new PointValue(i, yAxisData[i]));
        }

        //Declare a list of a type Line.
        //This list will hold the line of the graph chart.
        List lines = new ArrayList();
        lines.add(line);

        // Now you can add the graph line to the overall data chart.
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //Now you need to add the following code to be able to see the Android line chart.
        lineChartView.setLineChartData(data);

        Axis axis = new Axis();
        axis.setValues(axisValues);
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
        data.setAxisYLeft(yAxis);

        //X축 텍스트 색, 크기
        axis.setTextSize(5);
        axis.setTextColor(Color.parseColor("#03A9F4"));

        //Y축 텍스트 색, 크기
        yAxis.setTextColor(Color.parseColor("#03A9F4"));
        yAxis.setTextSize(10);



        //Y축 이름주기
        /*yAxis.setName("Sales in millions");*/


        //If you had noticed that Y-Axis data inside the line chart doesn't show it's full data based on
        // what you initialized in the beginning. You can fix it by adding the following code.
        //What you did here is you explicitly specified a value
        // that is higher than the Y-Axis actual data which is in this example is (90).
        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.top = max + max/3;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);
    }

    public void getMonthMileageChart(int startPoint) {
        //Open up MainActivity.java to reference LineChartView.
        LineChartView lineChartView = findViewById(R.id.monthMileageChart);

        //나타내고 싶은 데이터 초기화
        String[] axisData = {"1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월",
                "10월", "11월", "12월"};

        String[] sp = charts.split(",");

        int[] yAxisData = new int[12];
        int max = Integer.parseInt(sp[startPoint].trim());

        for(int i=startPoint; i<startPoint+12; i++) {
            yAxisData[i-startPoint] = Integer.parseInt(sp[i].trim());
            if(max < yAxisData[i-startPoint]) {
                max = yAxisData[i-startPoint];
            }

            year_mileage += yAxisData[i-startPoint];
        }

        // Next declare 2 types of List like the following.
        //These lists will be used to hold the data for Axis and Y-Axis.
        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();

        // Declare and initialize the line which appears inside graph chart, this line will hold the values of Y-Axis.
        Line line = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0"));


        //리스트에 추가
        for(int i = 0; i < axisData.length; i++){
            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
        }

        for (int i = 0; i < yAxisData.length; i++){
            yAxisValues.add(new PointValue(i, yAxisData[i]));
        }

        //Declare a list of a type Line.
        //This list will hold the line of the graph chart.
        List lines = new ArrayList();
        lines.add(line);

        // Now you can add the graph line to the overall data chart.
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //Now you need to add the following code to be able to see the Android line chart.
        lineChartView.setLineChartData(data);

        Axis axis = new Axis();
        axis.setValues(axisValues);
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
        data.setAxisYLeft(yAxis);

        //X축 텍스트 색, 크기
        axis.setTextSize(5);
        axis.setTextColor(Color.parseColor("#03A9F4"));

        //Y축 텍스트 색, 크기
        yAxis.setTextColor(Color.parseColor("#03A9F4"));
        yAxis.setTextSize(10);



        //Y축 이름주기
        /*yAxis.setName("Sales in millions");*/


        //If you had noticed that Y-Axis data inside the line chart doesn't show it's full data based on
        // what you initialized in the beginning. You can fix it by adding the following code.
        //What you did here is you explicitly specified a value
        // that is higher than the Y-Axis actual data which is in this example is (90).
        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.top = max + max/3;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);
    }


}