package Task

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import kotlin.test.Test

object TaskTable : IntIdTable() {
    val name = varchar("name", 50)
    val description = varchar("description", 50).nullable()
}

class TaskDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TaskDao>(TaskTable)

    var name by TaskTable.name
    var description by TaskTable.description
}

class TaskInfoDaoTest {
    @Test
    fun generateTable() {
        Database.connect(
            "jdbc:h2:mem:test;MODE=PostgreSQL",
            driver = "org.h2.Driver",
            user = "root",
            password = "",
            databaseConfig = DatabaseConfig {
                sqlLogger = StdOutSqlLogger
                explicitDialect

            }
        )

        transaction {
            SchemaUtils.create(TaskTable)

            TaskDao.new {
                name = "name"
                description = "description"
            }


            TaskTable.batchInsert(
                listOf(
                    "cleaning" to "Clean the house",
                    "gardening" to "Mow the lawn",
                    "shopping" to "Buy the groceries",
                    "painting" to "Paint the fence"
                )
            ) { (name, desc) ->
                this[TaskTable.name] = name
                this[TaskTable.description] = desc
            }


            SchemaUtils.drop(TaskTable)
        }
    }
}