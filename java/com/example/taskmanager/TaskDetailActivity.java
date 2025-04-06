package com.example.taskmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TaskDetailActivity extends AppCompatActivity {
    private EditText titleEditText;
    private EditText descriptionEditText;
    private Button saveButton;
    private TaskViewModel taskViewModel;
    private int taskId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        
        titleEditText = findViewById(R.id.edit_text_title);
        descriptionEditText = findViewById(R.id.edit_text_description);
        saveButton = findViewById(R.id.button_save);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            taskId = extras.getInt("task_id", -1);
            if (taskId != -1) {
                taskViewModel.getAllTasks().observe(this, tasks -> {
                    for (Task task : tasks) {
                        if (task.id == taskId) {
                            titleEditText.setText(task.title);
                            descriptionEditText.setText(task.description);
                            break;
                        }
                    }
                });
            }
        }

        saveButton.setOnClickListener(view -> {
            String title = titleEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();

            if (title.isEmpty()) {
                Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show();
                return;
            }

            Task task = new Task();
            task.title = title;
            task.description = description;
            task.timestamp = System.currentTimeMillis();

            if (taskId == -1) {
                taskViewModel.insert(task);
            } else {
                task.id = taskId;
                taskViewModel.update(task);
            }

            finish();
        });
    }
}
