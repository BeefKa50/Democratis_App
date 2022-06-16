package com.example.kotlindeezer.ui

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.democratis.classes.Commission
import com.example.democratisapp.MainActivity
import com.example.democratisapp.R
import com.example.democratisapp.classes.AccountAndCommission
import com.example.democratisapp.database.DemocratisDB
import com.example.democratisapp.databinding.RecyclerItemCommissionsBinding
import com.example.democratisapp.threads.UsefulThreads
import com.example.democratisapp.ui.commissions.CommissionsFragment
import java.util.concurrent.ConcurrentHashMap


class RecyclerCommissionAdapter(private val data: List<Commission>, val parentFragment: Fragment) : RecyclerView.Adapter<RecyclerCommissionAdapter.CommissionViewHolder>(){
    companion object {
        private const val TAG = "CommissionViewAdapter"
        var isMemberOfCommission:HashMap<Long,Boolean> = HashMap<Long,Boolean>()
        var membersTextViews: ConcurrentHashMap<Long, TextView> = ConcurrentHashMap<Long,TextView>()
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

        membersTextViews.put(commission.commissionId,holder.binding.commissionMembers)

       var thUpdate = UsefulThreads.ThreadUpdateCommissionMembers(
           commission.commissionId,
           parentFragment.requireContext()
       )
       thUpdate.start()
       thUpdate.join()

        holder.binding.commissionName.text = commission.name
        holder.binding.commissionDescription.text = commission.desc

        var th =
            MainActivity.profileId?.let { it1 ->
                UsefulThreads.ThreadIsMemberOfCommission(
                    it1,
                    commission.commissionId,
                    parentFragment.requireContext()
                )
            }
        th?.start()
        th?.join()

        if(isMemberOfCommission.get(commission.commissionId) == false || isMemberOfCommission.get(commission.commissionId) == null  ){
            holder.binding.joinCommission.setText("Rejoindre")
            holder.binding.joinCommission.setBackgroundColor(ContextCompat.getColor(parentFragment.requireContext(), R.color.purple_500))
        }
        else{
            holder.binding.joinCommission.setText("Quitter")
            holder.binding.joinCommission.setBackgroundColor(ContextCompat.getColor(parentFragment.requireContext(), R.color.red))
        }

        holder.binding.joinCommission.setOnClickListener{
            var accountId: Long? = MainActivity.profileId
            var commissionId:Long = commission.commissionId

            var th =
                accountId?.let { it1 ->
                    UsefulThreads.ThreadIsMemberOfCommission(
                        it1,
                        commissionId,
                        parentFragment.requireContext()
                    )
                }
            th?.start()
            th?.join()

            if(isMemberOfCommission.get(commissionId) == false  ) {
                var th2 = accountId?.let { it1 ->
                    UsefulThreads.ThreadAddNewMemberToCommission(
                        it1,
                        commissionId,
                        parentFragment.requireContext()
                    )
                }
                th2?.start()
                th2?.join()

                holder.binding.joinCommission.setText("Quitter")
                holder.binding.joinCommission.setBackgroundColor(ContextCompat.getColor(parentFragment.requireContext(), R.color.red))
            }
            else{
                var th2 = accountId?.let { it1 ->
                    UsefulThreads.ThreadDeleteMemberInCommission(
                        it1,
                        commissionId,
                        parentFragment.requireContext()
                    )
                }
                th2?.start()
                th2?.join()

                holder.binding.joinCommission.setText("Rejoindre")
                holder.binding.joinCommission.setBackgroundColor(ContextCompat.getColor(parentFragment.requireContext(), R.color.purple_500))
            }

            var thUpdate = UsefulThreads.ThreadUpdateCommissionMembers(
                commission.commissionId,
                parentFragment.requireContext()
            )
            thUpdate.start()
            thUpdate.join()
        }

        holder.binding.card.setOnClickListener{
            var th =
                MainActivity.profileId?.let { it1 ->
                    UsefulThreads.ThreadIsMemberOfCommission(
                        it1,
                        commission.commissionId,
                        parentFragment.requireContext()
                    )
                }
            th?.start()
            th?.join()

            if(isMemberOfCommission.get(commission.commissionId) == true){
                val args = bundleOf("id" to commission.commissionId.toString())
                val navController = parentFragment.findNavController()
                navController.navigate(R.id.action_commissions_to_commissionPropositionsFragment, args)
            }
            else{
                Toast.makeText(parentFragment.requireContext(),
                    "Vous devez d'abord rejoindre la commission.", Toast.LENGTH_SHORT).show()
            }
        }

    }
}