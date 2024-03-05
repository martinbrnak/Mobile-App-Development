package com.example.personalexpensetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.personalexpensetracker.databinding.ListItemExpenseBinding
import java.util.UUID
class ExpenseHolder(
    private val binding: ListItemExpenseBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(expense: Expense, onExpenseClicked: (expenseId: UUID) -> Unit) {
            binding.expenseTitle.text = expense.title
            binding.expenseDate.text = expense.date.toString()

            binding.root.setOnClickListener {
                onExpenseClicked(expense.id)
            }

            binding.expenseCost.text = "$ " + expense.cost.toString()

            binding.expenseTypeShow.text = expense.type
        }
    }
    class ExpenseListAdapter(
        private val expenses: List<Expense>,
        private val onExpenseClicked: (expenseId: UUID) -> Unit
    ) : RecyclerView.Adapter<ExpenseHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ExpenseHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ListItemExpenseBinding.inflate(inflater, parent, false)
            return ExpenseHolder(binding)
        }

        override fun onBindViewHolder(holder: ExpenseHolder, position: Int) {
            val expense = expenses[position]
            holder.bind(expense, onExpenseClicked)
        }

        override fun getItemCount() = expenses.size
    }