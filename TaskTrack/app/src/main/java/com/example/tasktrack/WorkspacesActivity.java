package com.example.tasktrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.tasktrack.R;
import com.example.tasktrack.Project;
import com.example.tasktrack.TaskTrackViewModel;

public class WorkspacesActivity extends AppCompatActivity {
    private TaskTrackViewModel taskTrackViewModel;
    private ProjectListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workspaces); // You must create this layout

        // 1. Setup RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recyclerview_projects);
        // Adapter handles clicks to open TasksActivity
        adapter = new ProjectListAdapter(project -> {
            Intent intent = new Intent(WorkspacesActivity.this, TasksActivity.class);
            intent.putExtra("PROJECT_ID", project.getId());
            intent.putExtra("PROJECT_NAME", project.getName());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // 2. Initialize ViewModel
        taskTrackViewModel = new ViewModelProvider(this).get(TaskTrackViewModel.class);

        // 3. Observe LiveData for automatic UI updates
        taskTrackViewModel.allProjects.observe(this, projects -> {
            adapter.setProjects(projects);
        });

        // 4. Handle Add Project Button
        FloatingActionButton fab = findViewById(R.id.fab_add_project);
        fab.setOnClickListener(v -> showAddProjectDialog());
    }

    private void showAddProjectDialog() {
        final EditText input = new EditText(this);
        // Use a clean dialog for quick project creation
        new MaterialAlertDialogBuilder(this)
                .setTitle("New Project")
                .setMessage("Enter the name for your new workspace.")
                .setView(input)
                .setPositiveButton("Create", (dialog, which) -> {
                    String name = input.getText().toString().trim();
                    if (!name.isEmpty()) {
                        taskTrackViewModel.insertProject(new Project(name));
                    } else {
                        Toast.makeText(this, "Project name cannot be empty", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}