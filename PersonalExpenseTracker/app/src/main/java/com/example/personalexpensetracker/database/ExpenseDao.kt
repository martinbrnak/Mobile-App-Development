package com.example.personalexpensetracker.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.personalexpensetracker.Expense
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expense")
    fun getExpenses(): Flow<List<Expense>>

    @Query("SELECT * FROM expense WHERE id=(:id)")
    suspend fun getExpense(id: UUID): Expense

    @Update
    suspend fun updateExpense(expense: Expense)

    @Insert
    suspend fun addExpense(expense: Expense)
}