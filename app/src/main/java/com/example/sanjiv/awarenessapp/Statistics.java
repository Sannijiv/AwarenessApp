package com.example.sanjiv.awarenessapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Statistics extends Fragment {

    FirebaseDatabase database;
    DatabaseReference reference, referenceDay;

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
    DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat formatDate = new SimpleDateFormat("dd-MM");

    GraphView graphView;
    LineGraphSeries seriesLine;
    BarGraphSeries seriesBar;
    Button lijnButton, staafButton;
    String[] days;
    Long[] daysTimeMillis;
    Date[] daysGetTime;
    String[] SPINNERLIST = {"Vandaag", "Week"};
    MaterialBetterSpinner spinner;
    int countday, stoppedAt;
    DataPoint[] dpWeek;


    public Statistics() {
        // required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_statistieken, null);

        Calendar cur = Calendar.getInstance();
        final String curDay = format.format(cur.getTime());

        graphView = v.findViewById(R.id.graph);
        lijnButton = v.findViewById(R.id.statisticLine);
        staafButton = v.findViewById(R.id.statisticBar);

        final Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        days = new String[7];
        daysTimeMillis = new Long[7];
        daysGetTime = new Date[7];
        for (int i = 0; i < 7; i++) {
            days[i] = format.format(calendar.getTime());
            daysTimeMillis[i] = calendar.getTimeInMillis();
            daysGetTime[i] = calendar.getTime();
            calendar.add(Calendar.DAY_OF_MONTH, 1);
//            Log.d("Statistics", "array:" + days[i]);
//            Log.d("Statistics", "arrayTimeMillis:" + daysTimeMillis[i]);
//            Log.d("Statistics", "arrayGetTime:" + daysGetTime[i]);

        }

        seriesLine = new LineGraphSeries();
        seriesBar = new BarGraphSeries();

        seriesBar.setSpacing(10);
        database = FirebaseDatabase.getInstance();
        referenceDay = database.getReference("soundlevel").child("123456789");
        reference = database.getReference("soundlevel").child("123456789");


        lijnButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                lijnButton.setPressed(true);
                staafButton.setPressed(false);
                graphView.setVisibility(View.VISIBLE);
                graphView.removeAllSeries();
                graphView.addSeries(seriesLine);


                return true;
            }
        });

        staafButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                staafButton.setPressed(true);
                lijnButton.setPressed(false);
                graphView.setVisibility(View.VISIBLE);
                graphView.removeAllSeries();


                graphView.addSeries(seriesBar);
                return true;
            }
        });

        graphView.getViewport().setMinY(20.0);
        graphView.getViewport().setMaxY(120.0);
        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setScrollable(true);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this.getContext(),
                android.R.layout.simple_dropdown_item_1line, SPINNERLIST);

        spinner = (MaterialBetterSpinner) v.findViewById(R.id.spinner);
        spinner.setAdapter(arrayAdapter);


        graphView.setVisibility(View.GONE);

        spinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String datum = spinner.getText().toString();
                // Log.d("Statistics", "Value:" + spinner.getText().toString());
                countday = 0;

                if (datum.equalsIgnoreCase("vandaag")) {
                    graphView.getGridLabelRenderer().setNumHorizontalLabels(5);
                    Log.d("Statistics", "Current day: " + curDay);
                    Log.d("Statistics", "To updateChartToday");
                    updateChartToday(curDay);
                }

                if (datum.equalsIgnoreCase("week")) {
                    graphView.getGridLabelRenderer().setNumHorizontalLabels(7);

//                    graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));


                    Log.d("Statistics", "To updateChartWeek");
                    //    updateChartWeek();
                    Log.d("Statistics", "Current day: " + curDay);

                    dpWeek = new DataPoint[7];
                    for (int i = 0; i < 7; i++) {
                        Log.d("Statistics", "day checking: " + days[i]);
                        if (days[i].equalsIgnoreCase(curDay)) {
                            Log.d("Statistics", "Same day, week can't be longer");
                            stoppedAt = i;
                            dpWeek = new DataPoint[stoppedAt + 1];
                            updateChartWeek(days[i]);
                            break;
                        } else {
                            updateChartWeek(days[i]);
                        }

                    }


                }


            }
        });
        Toast.makeText(this.getContext(), "Test", Toast.LENGTH_SHORT).show();

        return v;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    public void updateChartToday(String datum) {

        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {

            @Override
            public String formatLabel(double value, boolean isValueX) {

                if (isValueX) {
                    return simpleDateFormat.format(new Date((long) value));
                } else {
                    return super.formatLabel(value, isValueX);
                }
            }
        });

        reference.child(datum).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DataPoint[] dp = new DataPoint[(int) dataSnapshot.getChildrenCount()];
                double gemiddeldDb = 0;
                int index = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PointValue punt = snapshot.getValue(PointValue.class);
                    gemiddeldDb += punt.getDecibellevel();
//                    Log.d("Statistics", "puntDecibel = " + punt.getDecibellevel());
//                    Log.d("Statistics", "tijd= " + punt.getTime());
//                    Log.d("Statistics", "index= " + index);

                    dp[index] = new DataPoint(punt.getTime(), punt.getDecibellevel());
                    index++;

                }
                int aantal = ((int) dataSnapshot.getChildrenCount());
                gemiddeldDb = gemiddeldDb / aantal;
                Log.d("Statistics", "Gemiddelde dblvl= " + gemiddeldDb);

                seriesLine.resetData(dp);
                seriesBar.resetData(dp);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    public void updateChartWeek(String datum) {
        graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {

            @Override
            public String formatLabel(double value, boolean isValueX) {

                if (isValueX) {
                    return formatDate.format(new Date((long) value));
                } else {
                    return super.formatLabel(value, isValueX);
                }
            }
        });
        Log.d("Statistics", "datum  " + datum);


        reference.child(datum).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                double gemiddeldDb = 0;
                int index = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PointValue punt = snapshot.getValue(PointValue.class);
                    gemiddeldDb += punt.getDecibellevel();
//                        Log.d("Statistics", "puntDecibel = " + punt.getDecibellevel());
//                        Log.d("Statistics", "tijd= " + punt.getTime());
//                        Log.d("Statistics", "index= " + index);
                    index++;

                }

                int aantal = ((int) dataSnapshot.getChildrenCount());
                gemiddeldDb = gemiddeldDb / aantal;
                Log.d("Statistics", "gemiddeldeDb = " + gemiddeldDb);

                dpWeek[countday] = new DataPoint(daysGetTime[countday], gemiddeldDb);

                Log.d("Statistics", "timeFormat looks like : " + format.format(daysGetTime[countday]));

                Log.d("Statistics", "Countday= " + countday);
                countday++;


                try {
                    if (dpWeek[stoppedAt].toString() != null) {
                        Log.d("Statistics", "Stopped at : " + stoppedAt);
                        for (int i = 0; i <= stoppedAt; i++) {
                            Log.d("Statistics", "valueOfdpWeek: " + i + "= " + dpWeek[i]);
                        }
                        seriesLine.resetData(dpWeek);
                        seriesBar.resetData(dpWeek);

                    }
                } catch (NullPointerException ex) {
                    Log.d("Statistics", "one of the fields is still empty");

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }


    public void updateChartMonth() {


    }

    public void updateChartYear() {


    }


}