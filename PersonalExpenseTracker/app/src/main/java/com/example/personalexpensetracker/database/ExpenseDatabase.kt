package com.example.personalexpensetracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.personalexpensetracker.Expense


@Database(entities = [Expense::class], version = 3)
@TypeConverters(ExpenseTypeConverters::class)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}

    val migration_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE Expense ADD COLUMN type TEXT NOT NULL DEFAULT ''"
            )
            database.execSQL("ALTER TABLE Expense DROP COLUMN isSolved")
        }
    }
    val migration_2_3 = object : Migration(2, 3){
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE Expense ADD COLUMN cost DOUBLE NOT NULL DEFAULT 0.0"
            )
        }
    }
