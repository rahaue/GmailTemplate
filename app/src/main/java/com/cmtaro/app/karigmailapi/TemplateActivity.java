package com.cmtaro.app.karigmailapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.RoomDatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class TemplateActivity extends AppCompatActivity {

    EditText editText, subjectText;
    Button btAdd, btReset;
    RecyclerView recyclerView;
    List<MainData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template);

        // Assign variable
        editText = findViewById(R.id.edit_text);

        // 変更点
        subjectText = findViewById(R.id.subject_text);
        btAdd = findViewById(R.id.bt_add);
        btReset = findViewById(R.id.bt_reset);
        recyclerView = findViewById(R.id.recycle_view);

        // initialize database
        database = RoomDB.getInstance(this);
        // Store database value in data list
        // dataList を編集すれば良い
        dataList = database.mainDao().getAll();

        // Initialize Linear Layout manager
        linearLayoutManager = new LinearLayoutManager(this);

        // Set layout manager
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MainAdapter(TemplateActivity.this, dataList);
        // Set Adapter
        //　ここでセットしています
        recyclerView.setAdapter(adapter);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 直すとしたらここ
                // get string from edit text
                String sSubject = subjectText.getText().toString().trim();
                String sText = editText.getText().toString().trim();

                // check condition
                if(!sText.equals("")) {
                    // Initalize Main Data
                    MainData data = new MainData();
                    // Set Subjext and Text on MainData
                    data.setSubject(sSubject);
                    data.setText(sText);
                    // Insert text in database
                    database.mainDao().insert(data);
                    // Clear edit text
                    editText.setText("");
                    subjectText.setText("");
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.mainDao().reset(dataList);
                dataList.clear();
                dataList.addAll(database.mainDao().getAll());
                adapter.notifyDataSetChanged();
            }
        });
    }
}