package com.example.personalexpensetracker

import android.app.Application

class PersonalExpenseTrackerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ExpenseRepository.initialize(this)
    }
}