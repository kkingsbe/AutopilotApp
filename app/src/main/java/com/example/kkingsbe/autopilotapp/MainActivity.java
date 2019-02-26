package com.example.kkingsbe.autopilotapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ArrayList<FunctionItem> functionList;
    private RecyclerView functionsRecyclerView;
    private FunctionsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateRecyclerView();
    }

    @Override
    public void onClick(View view) {

    }

    private void populateRecyclerView() {
        functionList = new ArrayList<>();
        functionList.add(new FunctionItem("Servos", "Make sure all servos are working correctly"));
        functionList.add(new FunctionItem("PID Params", "Tune the PID parameters"));
        functionList.add(new FunctionItem("Manual Flight", "Fly the UAV using the tilt sensors on your phone"));

        functionsRecyclerView = findViewById(R.id.functionsRecyclerView);
        functionsRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new FunctionsAdapter(functionList);

        functionsRecyclerView.setLayoutManager(mLayoutManager);
        functionsRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new FunctionsAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                Intent intent;
                switch(functionList.get(position).getText1())
                {
                    case "Servos":
                        intent = new Intent(MainActivity.this, ServoTest.class);
                        startActivityForResult(intent, 1);
                    case "Manual Flight":
                        intent = new Intent(MainActivity.this, ManualFlight.class);
                        startActivityForResult(intent, 2);
                }
            }
        });
    }
}
