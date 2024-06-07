package com.example.task

import kotlinx.serialization.Serializable
import org.koin.core.annotation.Single

interface TaskUseCase {
    suspend fun getTask(): List<TaskInfo>

    suspend fun getTaskBy(id: Int): TaskInfo

    suspend fun saveTask(command: SaveTaskCommand): String

    suspend fun modifyTask(criteria: Int, command: ModifyTaskCommand)

    suspend fun removeTask(criteria: Int)

    @Serializable
    data class TaskInfo(
        val name: String,
        val description: String,
    )

    @Serializable
    data class SaveTaskCommand(
        val name: String,
        val description: String,
    )

    @Serializable
    data class ModifyTaskCommand(
        val name: String,
        val description: String,
    )
}


@Single(binds = [TaskUseCase::class])
class TaskService(
    private val taskLoadPort: TaskLoadPort,
    private val taskSavePort: TaskSavePort,
    private val taskUpdatePort: TaskUpdatePort,
    private val taskDeletePort: TaskDeletePort
) : TaskUseCase {
    override suspend fun getTask(): List<TaskUseCase.TaskInfo> {
        val load = taskLoadPort.load()

        return load.map { TaskUseCase.TaskInfo(it.name, it.description) }
    }

    override suspend fun saveTask(command: TaskUseCase.SaveTaskCommand): String {
        val id = taskSavePort.save(Task(name = command.name, description = command.description))

        return id.toString()
    }

    override suspend fun getTaskBy(id: Int): TaskUseCase.TaskInfo {
        val load = taskLoadPort.load(id)

        return load.run { TaskUseCase.TaskInfo(name, description) }
    }

    override suspend fun modifyTask(criteria: Int, command: TaskUseCase.ModifyTaskCommand) {
        taskUpdatePort.update(criteria) {
            it.modifyInfo(command.name, command.description)
        }
    }

    override suspend fun removeTask(criteria: Int) {
        taskDeletePort.delete(criteria)
    }
}