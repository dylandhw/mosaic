package com.example.tasktrack;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.tasktrack.Project;
import com.example.tasktrack.Task;
import com.example.tasktrack.TaskTrackDao;

import java.util.List;

public class TaskTrackRepository {
    private final TaskTrackDao taskTrackDao;

    public TaskTrackRepository(Application application) {
        com.example.tasktrack.TaskTrackDatabase db = com.example.tasktrack.TaskTrackDatabase.getDatabase(application);
        taskTrackDao = db.taskTrackDao();
    }

    // --- READ Operations (return LiveData) ---
    public LiveData<List<Project>> getAllProjects() {
        return taskTrackDao.getAllProjects();
    }

    public LiveData<List<Task>> getActiveTasksForProject(int projectId) {
        return taskTrackDao.getActiveTasksForProject(projectId);
    }

    // --- WRITE Operations (must run on background thread) ---
    public void insertProject(Project project) {
        TaskTrackDatabase.databaseWriteExecutor.execute(() ->
                taskTrackDao.insertProject(project)
        );
    }

    public void insertTask(Task task) {
        TaskTrackDatabase.databaseWriteExecutor.execute(() ->
                taskTrackDao.insertTask(task)
        );
    }

    public void updateTask(Task task) {
        TaskTrackDatabase.databaseWriteExecutor.execute(() ->
                taskTrackDao.updateTask(task)
        );
    }

    public void deleteProject(Project project) {
        TaskTrackDatabase.databaseWriteExecutor.execute(() ->
                taskTrackDao.deleteProject(project)
        );
    }
    // Implement deleteTask and updateProject similarly...
}