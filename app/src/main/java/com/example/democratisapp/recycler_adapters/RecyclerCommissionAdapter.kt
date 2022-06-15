package com.example.kotlindeezer.ui

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.democratis.classes.Commission
import com.example.democratisapp.MainActivity
import com.example.democratisapp.R
import com.example.democratisapp.database.DemocratisDB
import com.example.democratisapp.databinding.RecyclerItemCommissionsBinding
import com.example.democratisapp.ui.commissions.CommissionsFragment


class RecyclerCommissionAdapter(private val data: List<Commission>, val parentFragment: Fragment) : RecyclerView.Adapter<RecyclerCommissionAdapter.CommissionViewHolder>(){
    companion object {
        private const val TAG = "CommissionViewAdapter"
        var isMemberOfCommission:HashMap<Long,Boolean> = HashMap<Long,Boolean>()
    }

    inner class CommissionViewHolder(val binding: RecyclerItemCommissionsBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ThreadAddNewMemberToCommission(var accountId:Long,
                                         var commissionId:Long,
                                         var context: Context): Thread() {
        public override fun run() {
            var th =
                MainActivity.profileId?.let { ThreadIsMemberOfCommission(it,commissionId,context) }
            th?.start()
            th?.join()
            if(isMemberOfCommission.get(commissionId) == false) {
                var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
                db.commissionDao().addMemberToCommission(accountId, commissionId)
            }
        }
    }

    class ThreadDeleteMemberInCommission(var accountId:Long,
                                         var commissionId:Long,
                                         var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            var th =
                MainActivity.profileId?.let { ThreadIsMemberOfCommission(it,commissionId,context) }
            th?.start()
            th?.join()

            if(isMemberOfCommission.get(commissionId) == true) {
                db.commissionDao().deleteMemberInCommission(accountId, commissionId)
            }
        }
    }

    class ThreadIsMemberOfCommission(var accountId:Long,
                                         var commissionId:Long,
                                         var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            isMemberOfCommission.put(commissionId,db.commissionDao().isMemberOfCommission(accountId,commissionId))
        }
    }

    class ThreadUpdateCommissionMembers(var commissionMembers:TextView,
                                         var commissionId:Long,
                                         var context: Context): Thread() {
        private fun runOnUiThread(task:Runnable){
            Handler(Looper.getMainLooper()).post(task)
        }

        public override fun run() {
            while(true) {
                var db: DemocratisDB = DemocratisDB.getDatabase(this.context)

                var nbMembers: Long = db.commissionDao().countCommissionMembers(commissionId)

                runOnUiThread(Runnable {
                    commissionMembers.setText(nbMembers.toString() + " membre(s)")
                })
                sleep(1000)
            }
        }
    }

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

        var thUpdate = ThreadUpdateCommissionMembers(holder.binding.commissionMembers,
            commission.commissionId,parentFragment.requireContext())
        thUpdate.start()

        holder.binding.commissionName.text = commission.name
        holder.binding.commissionDescription.text = commission.desc

        var th =
            MainActivity.profileId?.let { it1 -> ThreadIsMemberOfCommission(it1,commission.commissionId, parentFragment.requireContext()) }
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
                accountId?.let { it1 -> ThreadIsMemberOfCommission(it1,commissionId, parentFragment.requireContext()) }
            th?.start()
            th?.join()

            if(isMemberOfCommission.get(commissionId) == false  ) {
                var th2 = accountId?.let { it1 ->
                    ThreadAddNewMemberToCommission(
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
                var th2 = accountId?.let { it1 -> ThreadDeleteMemberInCommission(it1,commissionId,parentFragment.requireContext()) }
                th2?.start()
                th2?.join()

                holder.binding.joinCommission.setText("Rejoindre")
                holder.binding.joinCommission.setBackgroundColor(ContextCompat.getColor(parentFragment.requireContext(), R.color.purple_500))
            }
        }

//        holder.binding.card.setOnClickListener{
//            val args = bundleOf("album" to album.title, "id" to album.id.toString())
//            val navController = parentFragment.findNavController()
//            navController.navigate(R.id.action_AlbumFragment_to_TrackFragment, args)
//        }

    }
}