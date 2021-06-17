package lapan.go.id.monitoring;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class DetailServer extends AppCompatActivity {

    TextView tvTemp, tvHum, tvJam, tvTgl, tvKondisi,
            tvSortLive, tvSortClock, tvSortDay,
            tvBtnOrbital, tvBtnViasat, tvBtnZodiac;

    LineChart chart;
    LineDataSet lineDataSet = new LineDataSet(null,null);
    ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
    LineData lineData;

    String path = "Ruang Server";
    int Minute = 20;
    int Clock = 60;
    int Day = 1440;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_server);

        init();
        show_digital();
        onClick();

        tvSortLive.setBackground(getResources().getDrawable(R.drawable.bg_btn_whitesmoke));
        tvSortLive.setTextColor(getResources().getColor(R.color.whiteSmoke));
        tvSortClock.setBackground(getResources().getDrawable(R.drawable.bg_btn_transparent));
        tvSortClock.setTextColor(getResources().getColor(R.color.whiteTransparent));
        tvSortDay.setBackground(getResources().getDrawable(R.drawable.bg_btn_transparent));
        tvSortDay.setTextColor(getResources().getColor(R.color.whiteTransparent));

        chart.getDescription().setEnabled(false);
        chart.setNoDataText(getString(R.string.set_no_data_text));
        chart.setNoDataTextColor(getResources().getColor(R.color.whiteSmoke));
        chart.invalidate();
    }

    private void init() {
        tvTemp = findViewById(R.id.tvTemp);
        tvHum = findViewById(R.id.tvHum);
        tvJam = findViewById(R.id.tvJam);
        tvTgl = findViewById(R.id.tvTgl);
        tvKondisi = findViewById(R.id.tvKondisi);
        chart = findViewById(R.id.chart);
        tvSortLive = findViewById(R.id.tvSortLive);
        tvSortClock = findViewById(R.id.tvSortClock);
        tvSortDay = findViewById(R.id.tvSortDay);
        tvBtnOrbital = findViewById(R.id.tvBtnOrbital);
        tvBtnViasat = findViewById(R.id.tvBtnViasat);
        tvBtnZodiac = findViewById(R.id.tvBtnZodiac);
    }

    private void show_digital() {
        new DetailSuhu(path, 1, tvTemp, tvHum, tvJam, tvTgl, tvKondisi);
    }

    private void onClick() {
        tvSortLive.setOnClickListener(v -> {
            tvSortLive.setBackground(getResources().getDrawable(R.drawable.bg_btn_whitesmoke));
            tvSortLive.setTextColor(getResources().getColor(R.color.whiteSmoke));
            tvSortClock.setBackground(getResources().getDrawable(R.drawable.bg_btn_transparent));
            tvSortClock.setTextColor(getResources().getColor(R.color.whiteTransparent));
            tvSortDay.setBackground(getResources().getDrawable(R.drawable.bg_btn_transparent));
            tvSortDay.setTextColor(getResources().getColor(R.color.whiteTransparent));
            chart.invalidate();
            retrieveData();
        });

        tvSortClock.setOnClickListener(v -> {
            tvSortLive.setBackground(getResources().getDrawable(R.drawable.bg_btn_transparent));
            tvSortLive.setTextColor(getResources().getColor(R.color.whiteTransparent));
            tvSortClock.setBackground(getResources().getDrawable(R.drawable.bg_btn_whitesmoke));
            tvSortClock.setTextColor(getResources().getColor(R.color.whiteSmoke));
            tvSortDay.setBackground(getResources().getDrawable(R.drawable.bg_btn_transparent));
            tvSortDay.setTextColor(getResources().getColor(R.color.whiteTransparent));
            chart.invalidate();
            retrieveDataClock();
        });

        tvSortDay.setOnClickListener(v -> {
            tvSortLive.setBackground(getResources().getDrawable(R.drawable.bg_btn_transparent));
            tvSortLive.setTextColor(getResources().getColor(R.color.whiteTransparent));
            tvSortClock.setBackground(getResources().getDrawable(R.drawable.bg_btn_transparent));
            tvSortClock.setTextColor(getResources().getColor(R.color.whiteTransparent));
            tvSortDay.setBackground(getResources().getDrawable(R.drawable.bg_btn_whitesmoke));
            tvSortDay.setTextColor(getResources().getColor(R.color.whiteSmoke));
            chart.invalidate();
            retrieveDataDay();
        });

        tvBtnOrbital.setOnClickListener(v -> {
            Intent orbital = new Intent(DetailServer.this, DetailOrbital.class);
            startActivity(orbital);
            overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
            finish();
        });

        tvBtnViasat.setOnClickListener(v -> {
            Intent viasat = new Intent(DetailServer.this, DetailViasat.class);
            startActivity(viasat);
            overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
            finish();
        });

        tvBtnZodiac.setOnClickListener(v -> {
            Intent zodiac = new Intent(DetailServer.this, DetailZodiac.class);
            startActivity(zodiac);
            overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(tvSortLive.callOnClick()){
            chart.invalidate();
            retrieveData();
        }
        else if(tvSortClock.callOnClick()){
            chart.invalidate();
            retrieveDataClock();
        }
        else if(tvSortDay.callOnClick()){
            chart.invalidate();
            retrieveDataDay();
        }
        else {
            chart.invalidate();
            retrieveData();
        }
    }

    private void retrieveData() {
        GraphSuhu(Minute);
    }

    private void retrieveDataClock() {
        GraphSuhu(Clock);
    }

    private void retrieveDataDay() {
        GraphSuhu(Day);
    }

    public void GraphSuhu(final int limit) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(path);
        Query query = databaseReference.orderByKey().limitToLast(limit);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Entry> data = new ArrayList<>();
                ArrayList<Integer> colors = new ArrayList<>();
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        DataPoints dataPoints = child.getValue(DataPoints.class);
                        data.add(new Entry(dataPoints.getTime(), dataPoints.getTemperature()));
                        if ( dataPoints.getTemperature() >= 0 && dataPoints.getTemperature() < 20 ){
                            colors.add(getResources().getColor( R.color.blueLight));
                        } else if ( dataPoints.getTemperature() >= 20 && dataPoints.getTemperature() <= 35 ){
                            colors.add(getResources().getColor( R.color.blue_light));
                        } else if ( dataPoints.getTemperature() > 35 && dataPoints.getTemperature() <= 100 ){
                            colors.add(getResources().getColor( R.color.hot_orange));
                        }
                    }
                    showChart(data);
                    lineDataSet.setColors(colors);
                    if (limit == 20) {
                        lineDataSet.setDrawCircles(true);
                        lineDataSet.setCircleColors(colors);
                        lineDataSet.setCircleSize(1.5f);
                    } else {
                        lineDataSet.setDrawCircles(false);
                    }
                    chart.invalidate();
                } else {
                    chart.clear();
                    chart.invalidate();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showChart(ArrayList<Entry> data) {
        lineDataSet.setValues(data);
        lineDataSet.setLabel("DataSet 1");
        lineDataSet.setDrawFilled(true);
        if (Utils.getSDKInt() >= 18) {
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_blue_light);
            lineDataSet.setFillDrawable(drawable);
        } else {
            lineDataSet.setFillAlpha(5);
        }
        lineDataSet.setLineWidth(1.5f);
//        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//        lineDataSet.setCubicIntensity(0.05f);
        lineDataSet.setDrawValues(false);
        iLineDataSets.clear();
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);

        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setLabelRotationAngle(0f);//45
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setDrawLabels(false);
        xAxis.setLabelCount(3, true);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                Date date = new Date((long) value);
                SimpleDateFormat fmt;
                fmt = new SimpleDateFormat("HH:mm zz");
                fmt.setTimeZone(TimeZone.getDefault());
                String s = fmt.format(date);
                return s;
            }
        });

        YAxis yAxisL = chart.getAxis(YAxis.AxisDependency.LEFT);
        yAxisL.setDrawGridLines(false);
        yAxisL.setDrawLabels(false);
        yAxisL.setAxisMinimum(15);
        yAxisL.setAxisMaximum(45);

        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);
        mv.setChartView(chart);
        chart.setMarker(mv);
        chart.getLegend().setEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setEnabled(false);
        chart.getXAxis().setEnabled(false);
        chart.notifyDataSetChanged();
        chart.clear();
        chart.setData(lineData);
        chart.invalidate();
        chart.moveViewTo(lineData.getEntryCount(),50L, YAxis.AxisDependency.LEFT);
    }
}