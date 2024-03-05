package com.example.personalexpensetracker

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.personalexpensetracker.databinding.FragmentExpenseDetailBinding
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Date

class ExpenseDetailFragment : Fragment() {

        private var _binding: FragmentExpenseDetailBinding? = null
        private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

        private val args: ExpenseDetailFragmentArgs by navArgs()

        private val expenseDetailViewModel: ExpenseDetailViewModel by viewModels {
            ExpenseDetailViewModelFactory(args.expenseId)
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            _binding =
                FragmentExpenseDetailBinding.inflate(inflater, container, false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            binding.apply {
                expenseTitle.doOnTextChanged { text, _, _, _ ->
                    expenseDetailViewModel.updateExpense { oldExpense ->
                        oldExpense.copy(title = text.toString())
                    }
                }

                expenseCost.doOnTextChanged { text, _, _, _ ->
                    expenseDetailViewModel.updateExpense { oldExpense ->
                        val costText = text.toString()
                        val costValue = if (costText.isNotEmpty()) {
                            try {
                                costText.toDouble()
                            } catch (e: NumberFormatException) {
                                0.0 // or any default value you want to use when conversion fails
                            }
                        } else {
                            0.0 // or any default value you want to use when the text is empty
                        }
                        oldExpense.copy(cost = costValue)
                    }

                }


                expenseTypeShow.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        val selectedCategory = resources.getStringArray(R.array.expense_categories)[position]
                        // Update the expense based on the selected category
                        expenseDetailViewModel.updateExpense { oldExpense ->
                            oldExpense.copy(type  = selectedCategory)
                        }
                    }
                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        val selectedCategory = resources.getStringArray(R.array.expense_categories)[0]
                        expenseDetailViewModel.updateExpense { oldExpense ->
                            oldExpense.copy(type  = selectedCategory)
                        }
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    expenseDetailViewModel.expense.collect { expense ->
                        expense?.let { updateUi(it) }
                    }
                }
            }

            setFragmentResultListener(
                DatePickerFragment.REQUEST_KEY_DATE
            ) { _, bundle ->
                val newDate =
                    bundle.getSerializable(DatePickerFragment.BUNDLE_KEY_DATE) as Date
                expenseDetailViewModel.updateExpense { it.copy(date = newDate) }
            }
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

        private fun updateUi(expense: Expense) {
            binding.apply {
                if (expenseTitle.text.toString() != expense.title) {
                    expenseTitle.setText(expense.title)
                }
                expenseDate.text = expense.date.toString()
                expenseDate.setOnClickListener {
                    findNavController().navigate(
                        ExpenseDetailFragmentDirections.selectDate(expense.date)
                    )
                }
                expenseCost.setText(String.format("%.2f", expense.cost))
                val categoryIndex = resources.getStringArray(R.array.expense_categories).indexOf(expense.type)
                if (categoryIndex != -1) {
                    expenseTypeShow.setSelection(categoryIndex)
                }
            }
        }
    }