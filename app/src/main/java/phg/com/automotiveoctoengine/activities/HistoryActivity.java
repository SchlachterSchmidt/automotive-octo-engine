package phg.com.automotiveoctoengine.activities;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import phg.com.automotiveoctoengine.R;
import phg.com.automotiveoctoengine.models.HistoryRecord;
import phg.com.automotiveoctoengine.services.HistoryService;

public class HistoryActivity extends AppCompatActivity {

    private final Context context = this;
    private LineChart lineChart;
    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        lineChart = findViewById(R.id.lineChart);
        pieChart = findViewById(R.id.pieChart);
    }

    @Override
    public void onResume() {
        super.onResume();
        setLineChart();
        setPieChart();

        List<HistoryRecord> historyRecordList = getRecords();

        LineData lineData = getLineData(historyRecordList);
        PieData pieData = getPieData(historyRecordList);

        if (lineData.getEntryCount() != 0) {
            lineChart.setData(lineData);
            lineChart.invalidate();
        }

        if (pieData.getEntryCount() != 0) {
            pieChart.setData(pieData);
            pieChart.invalidate();
        }
    }

    private void setLineChart() {
        lineChart.getDescription().setEnabled(false);
        lineChart.setTouchEnabled(true);
        lineChart.setDragDecelerationFrictionCoef(0.9f);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setDrawGridBackground(false);
        lineChart.setHighlightPerDragEnabled(true);
        lineChart.setBackgroundColor(Color.WHITE);
        lineChart.setViewPortOffsets(100f, 100f, 100f, 100f);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.RED);
        xAxis.setCenterAxisLabels(false);
        xAxis.setGranularity(24f); // 8 days?
        xAxis.setValueFormatter(new IAxisValueFormatter() {

            private final SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm");

            @Override
            public String getFormattedValue(float value, AxisBase axis) {

                long millis = TimeUnit.HOURS.toMillis((long) value);
                return mFormat.format(new Date(millis));
            }
        });

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(10f);
        leftAxis.setYOffset(-9f);
        leftAxis.setTextColor(Color.rgb(255, 192, 56));

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    private void setPieChart() {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);
        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);
        pieChart.setDrawCenterText(true);

        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        legend.setOrientation(Legend.LegendOrientation.VERTICAL);
        legend.setDrawInside(false);
        legend.setXEntrySpace(7f);
        legend.setYEntrySpace(0f);
        legend.setYOffset(0f);
    }

    private LineData getLineData(List<HistoryRecord> historyRecordList) {
        List<Entry> XYvalues = new ArrayList<>();
        float x = 0;

        for (HistoryRecord historyRecord: historyRecordList) {
            XYvalues.add(new Entry((x += 1), historyRecord.getDistractionScore()));
        }

        LineDataSet lineDataSet = new LineDataSet(XYvalues, "History Data Set");
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setColor(ColorTemplate.getHoloBlue());
        lineDataSet.setValueTextColor(ColorTemplate.getHoloBlue());
        lineDataSet.setLineWidth(1.5f);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawValues(false);
        lineDataSet.setFillAlpha(65);
        lineDataSet.setFillColor(ColorTemplate.getHoloBlue());
        lineDataSet.setHighLightColor(Color.rgb(244, 117, 117));
        lineDataSet.setDrawCircleHole(false);

        LineData data = new LineData(lineDataSet);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);
        return data;
    }

    private PieData getPieData(List<HistoryRecord> historyRecordList) {
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        Map<Float, Integer> distractorCounts = new HashMap<Float, Integer>();

        // count all occurrences of a particular distractor and add to map
        for (HistoryRecord record: historyRecordList) {
            Float label = record.getPredicted_label();
            Integer previousCount = distractorCounts.get(label);
            distractorCounts.put(label, previousCount == null ? 1 : previousCount + 1);
        }

        // for each distractor, add count of occurrences to pie chart
        for (Map.Entry<Float, Integer> distractorCount : distractorCounts.entrySet()){
            Float key = distractorCount.getKey();
            Integer value = distractorCount.getValue();
            entries.add(new PieEntry(value, String.format("%.0f", key)));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Catagories");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);

        // adding 11 colours from predefined colour templates for pretty visualisations
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);
        return data;
    }

    private List<HistoryRecord> getRecords() {
        HistoryService historyService = new HistoryService(context);
        return historyService.getRecords();
    }
}
