package com.example.tasktrack;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.tasktrack.TaskTrackViewModel;
import com.example.tasktrack.Project;
import com.example.tasktrack.Task;
import java.util.List;

public class TaskTrackViewModel extends AndroidViewModel {
    private final TaskTrackRepository repository;
    public final LiveData<List<Project>> allProjects;

    public TaskTrackViewModel(Application application) {
        super(application);
        repository = new TaskTrackRepository(application);
        allProjects = repository.getAllProjects();
    }

    // --- Project Methods ---
    public void insertProject(Project project) {
        repository.insertProject(project);
    }

    public void deleteProject(Project project) {
        repository.deleteProject(project);
    }

    // --- Task Methods ---
    // Returns LiveData that changes based on the project ID
    public LiveData<List<Task>> getActiveTasksForProject(int projectId) {
        return repository.getActiveTasksForProject(projectId);
    }

    public void insertTask(Task task) {
        repository.insertTask(task);
    }

    // Used for editing and marking completion
    public void updateTask(Task task) {
        repository.updateTask(task);
    }
}