package com.bignerdranch.android.criminalintent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bignerdranch.android.criminalintent.databinding.ListItemCrimeBinding
import com.bignerdranch.android.criminalintent.databinding.ListItemSeriouscrimeBinding



open class CrimeParent(private val rootView: View) : RecyclerView.ViewHolder(rootView) {
    open fun bind(crime: Crime) {
    }

    class CrimeHolder(
        public val binding: ListItemCrimeBinding
    ) : CrimeParent(binding.root) {
        override fun bind(crime: Crime) {
            binding.crimeTitle.text = crime.title
            binding.crimeDate.text = crime.date.toString()

            binding.root.setOnClickListener {
                Toast.makeText(
                    binding.root.context,
                    "${crime.title} clicked!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    class SeriousCrimeHolder(
        public val binding: ListItemSeriouscrimeBinding
    ) : CrimeParent(binding.root) {
        override fun bind(crime: Crime) {
            binding.crimeTitle.text = crime.title
            binding.crimeDate.text = crime.date.toString()

            binding.root.setOnClickListener {
                Toast.makeText(
                    binding.root.context,
                    "${crime.title} clicked!, CAREFUL THIS IS SERIOUS CRIME",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }



class CrimeListAdapter(
    private val crimes: List<Crime>
) : RecyclerView.Adapter<CrimeParent>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : CrimeParent {

        val inflater = LayoutInflater.from(parent.context)

        if (viewType == 1) {
            val binding = ListItemSeriouscrimeBinding.inflate(inflater, parent, false)
            return SeriousCrimeHolder(binding)
        }
        else{
            val binding = ListItemCrimeBinding.inflate(inflater, parent, false)
            return CrimeHolder(binding)
        }
    }
    override fun onBindViewHolder(holder: CrimeParent, position: Int) {
        val crime = crimes[position]
        holder.bind(crime)

    }
    override fun getItemCount() = crimes.size

    override fun getItemViewType(position: Int): Int {
        if(crimes[position].requiresPolice){
            return 1
        }
        return 0
    }
}


}

