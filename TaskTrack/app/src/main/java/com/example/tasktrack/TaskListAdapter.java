package com.example.tasktrack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tasktrack.R;
import com.example.tasktrack.Task;
import com.example.tasktrack.TaskTrackViewModel;
import java.util.ArrayList;
import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    private List<Task> tasks = new ArrayList<>();
    private final TaskTrackViewModel viewModel;
    private final OnTaskClickListener listener;

    public interface OnTaskClickListener {
        void onTaskClick(Task task);
    }

    public TaskListAdapter(TaskTrackViewModel viewModel, OnTaskClickListener listener) {
        this.viewModel = viewModel;
        this.listener = listener;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_task, parent, false); // Create this layout
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task currentTask = tasks.get(position);
        holder.taskTitleTextView.setText(currentTask.getTitle());
        holder.taskDueDateTextView.setText(currentTask.getDueDate() != null ? currentTask.getDueDate() : "");

        // Handle task completion using the CheckBox
        holder.completionCheckBox.setChecked(currentTask.isCompleted());

        // CRUCIAL: Prevent infinite loop when setting CheckBox state
        holder.completionCheckBox.setOnCheckedChangeListener(null);
        holder.completionCheckBox.setChecked(currentTask.isCompleted());

        holder.completionCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            currentTask.setCompleted(isChecked);
            // This call updates the database, which triggers the LiveData,
            // causing the RecyclerView to refresh and hide the task if completed.
            viewModel.updateTask(currentTask);
        });

        holder.itemView.setOnClickListener(v -> listener.onTaskClick(currentTask));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox completionCheckBox;
        private final TextView taskTitleTextView;
        private final TextView taskDueDateTextView;

        private TaskViewHolder(View itemView) {
            super(itemView);
            completionCheckBox = itemView.findViewById(R.id.checkbox_task_complete);
            taskTitleTextView = itemView.findViewById(R.id.text_view_task_title);
            taskDueDateTextView = itemView.findViewById(R.id.text_view_task_due_date);
        }
    }
}