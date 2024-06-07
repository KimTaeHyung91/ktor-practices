package com.example.task

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object TaskTable : IntIdTable() {
    val name = varchar("name", 50)
    val description = varchar("description", 50)
}

class TaskEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TaskEntity>(TaskTable)

    var name by TaskTable.name
    var description by TaskTable.description

    fun update(task: Task) {
        name = task.name
        description = task.description
    }

    fun toDomain(): Task {
        return Task(name, description)
    }
}