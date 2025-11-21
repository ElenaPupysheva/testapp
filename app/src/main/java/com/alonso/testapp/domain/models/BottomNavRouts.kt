package com.alonso.testapp.domain.models

enum class BottomNavRoutes {
    Main,
    Trainings;

    companion object {
        fun isInEnum(someString: String): Boolean {
            return try {
                valueOf(someString)
                true
            } catch (_: Exception) {
                false
            }
        }
    }
}