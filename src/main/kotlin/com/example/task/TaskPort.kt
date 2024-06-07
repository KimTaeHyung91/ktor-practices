package com.example.task

interface TaskLoadPort {
    suspend fun load(): List<Task>
    suspend fun load(id: Int): Task
}

interface TaskSavePort {
    suspend fun save(task: Task): Int
}

interface TaskUpdatePort {
    suspend fun update(criteria: Int, block: (Task) -> Unit)
}

interface TaskDeletePort {
    suspend fun delete(criteria: Int)
}