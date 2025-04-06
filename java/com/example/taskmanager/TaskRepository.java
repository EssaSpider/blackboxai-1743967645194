package com.example.taskmanager;

import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<Task>> allTasks;
    private Executor executor = Executors.newSingleThreadExecutor();

    public TaskRepository(TaskDatabase database) {
        taskDao = database.taskDao();
        allTasks = taskDao.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public void insert(Task task) {
        executor.execute(() -> taskDao.insertTask(task));
    }

    public void update(Task task) {
        executor.execute(() -> taskDao.updateTask(task));
    }

    public void delete(Task task) {
        executor.execute(() -> taskDao.deleteTask(task));
    }
}