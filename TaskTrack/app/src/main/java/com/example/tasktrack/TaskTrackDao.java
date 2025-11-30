package com.example.tasktrack;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.tasktrack.Project;
import com.example.tasktrack.Task;
import java.util.List;

@Dao
public interface TaskTrackDao {
    // --- Project CRUD ---
    @Insert
    void insertProject(Project project);

    // READ: Get all projects for the home screen
    @Query("SELECT * FROM projects ORDER BY id ASC")
    LiveData<List<Project>> getAllProjects();

    @Update
    void updateProject(Project project);

    @Delete
    void deleteProject(Project project);

    // --- Task CRUD ---
    @Insert
    void insertTask(Task task);

    // READ: Get all tasks for a specific project. Crucially, completed tasks are hidden/sorted.
    @Query("SELECT * FROM tasks WHERE projectId = :projectId AND isCompleted = 0 ORDER BY dueDate ASC, priority DESC")
    LiveData<List<Task>> getActiveTasksForProject(int projectId);

    // READ: Get completed tasks for review (optional feature based on requirements)
    @Query("SELECT * FROM tasks WHERE projectId = :projectId AND isCompleted = 1 ORDER BY dueDate DESC")
    LiveData<List<Task>> getCompletedTasksForProject(int projectId);

    @Update
    void updateTask(Task task); // Used for editing and marking complete

    @Delete
    void deleteTask(Task task);
}