package com.example.taskmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.widget.ImageView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskClickListener {
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private TaskAdapter adapter;
    private TaskViewModel taskViewModel;
    private ImageView settingsIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.task_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(adapter);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(this, tasks -> {
            adapter.setTasks(tasks);
        });

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, TaskDetailActivity.class);
            startActivity(intent);
        });

        settingsIcon = findViewById(R.id.settings_icon);
        settingsIcon.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onTaskClick(Task task) {
        Intent intent = new Intent(this, TaskDetailActivity.class);
        intent.putExtra("task_id", task.id);
        startActivity(intent);
    }
}
