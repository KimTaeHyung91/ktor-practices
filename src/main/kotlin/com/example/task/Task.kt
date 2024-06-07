package com.example.task

class Task(name: String, description: String) {
    var name = name
        private set

    var description = description
        private set

    fun modifyInfo(name: String?, description: String?) {
        if (name != null) {
            this.name = name
        }

        if (description != null) {
            this.description = description
        }
    }
}