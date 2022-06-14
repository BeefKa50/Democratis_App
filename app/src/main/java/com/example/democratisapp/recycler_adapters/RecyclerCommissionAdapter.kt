package com.example.kotlindeezer.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.democratis.classes.Commission
import com.example.democratisapp.databinding.RecyclerItemCommissionsBinding
import com.example.democratisapp.ui.commissions.CommissionsFragment


class RecyclerCommissionAdapter(private val data: List<Commission>, val parentFragment: Fragment) : RecyclerView.Adapter<RecyclerCommissionAdapter.CommissionViewHolder>(){
    companion object {
        private const val TAG = "CommissionViewAdapter"
    }

    inner class CommissionViewHolder(val binding: RecyclerItemCommissionsBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommissionViewHolder {
        Log.d(TAG, "onCreateViewHolder")

        val binding = RecyclerItemCommissionsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CommissionViewHolder(binding)
    }


    override fun onBindViewHolder(holder: RecyclerCommissionAdapter.CommissionViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder position=${position}")

        val commission: Commission = data[position]

        val media = commission.picture
        System.out.println("Picture : -------> " + media)
        try {
            CommissionsFragment.fragmentContext.let {
                Glide.with(it)
                    .load(media)
                    .into(holder.binding.commissionPicture) //imageView_album
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            Log.e(TAG, "Glide", e)
        }

        holder.binding.commissionName.text = commission.name
        holder.binding.commissionDescription.text = commission.desc


//        holder.binding.card.setOnClickListener{
//            val args = bundleOf("album" to album.title, "id" to album.id.toString())
//            val navController = parentFragment.findNavController()
//            navController.navigate(R.id.action_AlbumFragment_to_TrackFragment, args)
//        }


    }
}