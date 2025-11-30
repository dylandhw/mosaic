package com.example.tasktrack;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.example.tasktrack.Project;
import com.example.tasktrack.Task;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Project.class, Task.class}, version = 1, exportSchema = false)
public abstract class TaskTrackDatabase extends RoomDatabase {
    public abstract TaskTrackDao taskTrackDao();

    private static volatile TaskTrackDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    // Executor service to run database operations on a background thread
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TaskTrackDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            // Synchronized block ensures only one thread can create the database instance
            synchronized (TaskTrackDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    TaskTrackDatabase.class, "task_track_db")
                            // You might add .allowMainThreadQueries() here for testing, but DO NOT use in production.
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}