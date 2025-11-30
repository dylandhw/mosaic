package com.example.tasktrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.tasktrack.R;
import com.example.tasktrack.Task;
import com.example.tasktrack.TaskTrackViewModel;

public class TasksActivity extends AppCompatActivity {
    private TaskTrackViewModel taskTrackViewModel;
    private TaskListAdapter adapter;
    private int projectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks); // You must create this layout

        // Get Project ID from the Intent
        projectId = getIntent().getIntExtra("PROJECT_ID", -1);
        String projectName = getIntent().getStringExtra("PROJECT_NAME");

        if (projectId == -1) {
            finish(); // Invalid project ID, close activity
            return;
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(projectName);
        }

        // 1. Setup RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerview_tasks);
        taskTrackViewModel = new ViewModelProvider(this).get(TaskTrackViewModel.class);

        // Adapter handles task completion and editing
        adapter = new TaskListAdapter(taskTrackViewModel, task -> {
            // Logic to open TaskEditorActivity for editing
            Intent editIntent = new Intent(TasksActivity.this, TaskEditorActivity.class);
            // Pass task details here
            startActivity(editIntent);
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 2. Observe Active Tasks
        taskTrackViewModel.getActiveTasksForProject(projectId).observe(this, tasks -> {
            adapter.setTasks(tasks);
        });

        // 3. Handle Add Task Button
        FloatingActionButton fab = findViewById(R.id.fab_add_task);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(TasksActivity.this, TaskEditorActivity.class);
            intent.putExtra("PROJECT_ID", projectId);
            startActivity(intent);
        });
    }
}