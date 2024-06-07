package com.example.task

import com.example.common.suspendTransaction
import org.koin.core.annotation.Single

@Single(binds = [TaskLoadPort::class, TaskSavePort::class, TaskUpdatePort::class, TaskDeletePort::class])
class TaskRepository : TaskLoadPort, TaskSavePort, TaskUpdatePort, TaskDeletePort {

    override suspend fun load(): List<Task> = suspendTransaction {
        TaskEntity.all().map { it.toDomain() }
    }

    override suspend fun load(id: Int): Task = suspendTransaction {
        val find = TaskEntity.findById(id) ?: throw Error("Not Found Task by $id")

        find.toDomain()
    }

    override suspend fun save(task: Task): Int = suspendTransaction {
        val task = TaskEntity.new {
            name = task.name
            description = task.description
        }

        task.id.value
    }

    override suspend fun update(criteria: Int, block: (Task) -> Unit) = suspendTransaction {
        val taskEntity = TaskEntity.findById(criteria) ?: throw Error("Not Found Task by $id")

        val task = taskEntity.toDomain()
        block(task)
        taskEntity.update(task)
    }

    override suspend fun delete(criteria: Int) = suspendTransaction {
        val taskEntity = TaskEntity.findById(criteria) ?: throw Error("Not Found Task by $id")
        taskEntity.delete()
    }
}