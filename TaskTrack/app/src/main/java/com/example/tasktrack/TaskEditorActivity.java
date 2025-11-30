package com.example.tasktrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.tasktrack.R;
import com.example.tasktrack.Task;
import com.example.tasktrack.TaskTrackViewModel;

public class TaskEditorActivity extends AppCompatActivity {
    private EditText titleInput;
    private EditText notesInput;
    private EditText dueDateInput;
    private Button saveButton;

    private TaskTrackViewModel viewModel;
    private int projectId;

    // Optional: Existing task ID if editing
    private int taskId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_editor); // You must create this layout

        viewModel = new ViewModelProvider(this).get(TaskTrackViewModel.class);
        projectId = getIntent().getIntExtra("PROJECT_ID", -1);

        // Get view references
        titleInput = findViewById(R.id.edit_text_task_title);
        notesInput = findViewById(R.id.edit_text_task_notes);
        dueDateInput = findViewById(R.id.edit_text_task_due_date);
        saveButton = findViewById(R.id.button_save_task);

        if (projectId == -1) {
            Toast.makeText(this, "Error: Project not found.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        saveButton.setOnClickListener(v -> saveTask());
    }

    private void saveTask() {
        String title = titleInput.getText().toString().trim();
        String notes = notesInput.getText().toString().trim();
        String dueDate = dueDateInput.getText().toString().trim();
        String priority = "Medium"; // Default priority for quick creation

        if (title.isEmpty()) {
            Toast.makeText(this, "Task title is required!", Toast.LENGTH_SHORT).show();
            return;
        }

        // --- Create a new Task (no support for editing in this simplified example) ---
        Task newTask = new Task(
                projectId,
                title,
                notes.isEmpty() ? null : notes, // Set to null if empty
                dueDate.isEmpty() ? null : dueDate,
                priority,
                false // Always starts incomplete
        );

        viewModel.insertTask(newTask);
        Toast.makeText(this, "Task created!", Toast.LENGTH_SHORT).show();
        finish();
    }
}