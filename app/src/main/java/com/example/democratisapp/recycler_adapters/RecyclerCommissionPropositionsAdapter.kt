package com.example.democratisapp.recycler_adapters

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.democratis.classes.Paragraph
import com.example.democratis.classes.Proposition
import com.example.democratisapp.MainActivity
import com.example.democratisapp.R
import com.example.democratisapp.classes.PropositionSupports
import com.example.democratisapp.database.DemocratisDB
import com.example.democratisapp.databinding.RecyclerItemCommissionPropositionsBinding
import java.util.concurrent.ConcurrentHashMap

class RecyclerCommissionPropositionsAdapter(private val data: List<Proposition>, val parentFragment: Fragment) : RecyclerView.Adapter<RecyclerCommissionPropositionsAdapter.CommissionPropositionsViewHolder>(){
    companion object {
        private const val TAG = "PropositionViewHolder"
        lateinit var paragraphs:List<Paragraph>
        var isSupporting:ConcurrentHashMap<Long, Boolean> = ConcurrentHashMap()
        var supportViews:ConcurrentHashMap<Long,TextView> = ConcurrentHashMap()
    }

    inner class CommissionPropositionsViewHolder(val binding: RecyclerItemCommissionPropositionsBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ThreadIsSupporting(var propositionId:Long, var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            isSupporting.put(propositionId,MainActivity.profileId?.let { db.propositionSupportsDao().isSupporting(it,propositionId) } == true)
        }
    }

    class ThreadGetPropositionParagraphs(var propositionId:Long, var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            paragraphs = db.paragraphDao().getPropositionParagraphs(propositionId)
        }
    }

    class ThreadAddSupport(var propositionId:Long, var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            MainActivity.profileId?.let { PropositionSupports(accountId= it, propositionId = propositionId) }
                ?.let { db.propositionSupportsDao().addSupport(it) }
        }
    }

    class ThreadDeleteSupport(var propositionId:Long, var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            MainActivity.profileId?.let { db.propositionSupportsDao().deleteSupport(it,propositionId) }
        }
    }

    class ThreadUpdateSupports(var propositionId:Long,
                               var context: Context): Thread() {
        @Synchronized private fun runOnUiThread(task:Runnable){
            Handler(Looper.getMainLooper()).post(task)
        }

        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)

            var nbMembers: Long = db.propositionSupportsDao().countSupports(propositionId)
            System.out.println("Nb soutiens : " + nbMembers)
            System.out.println("Texte actuel : " + supportViews.get(propositionId)?.text)

            runOnUiThread(Runnable {
                supportViews.get(propositionId)?.setText(nbMembers.toString() + " soutien(s)")
            })
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommissionPropositionsViewHolder {
        Log.d(TAG, "onCreateViewHolder")

        val binding = RecyclerItemCommissionPropositionsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CommissionPropositionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerCommissionPropositionsAdapter.CommissionPropositionsViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder position=${position}")

        val proposition: Proposition = data[position]

        supportViews.put(proposition.propositionId,holder.binding.propositionSupports)

        holder.binding.propositionName.setText(proposition.title)
        holder.binding.propositionSupports.setText(proposition.nbSupports.toString() + " soutien(s)")

        var th = ThreadGetPropositionParagraphs(proposition.propositionId,parentFragment.requireContext())
        th.start()
        th.join()

        var paragraph: Paragraph = paragraphs.get(0)
        holder.binding.propositionDescription.setText(paragraph.content)

        var thUpdate = ThreadUpdateSupports(proposition.propositionId,parentFragment.requireContext())
        thUpdate.start()
        thUpdate.join()

        var propositionId:Long = proposition.propositionId

        var th2 = ThreadIsSupporting(propositionId,parentFragment.requireContext())
        th2?.start()
        th2?.join()

        if(isSupporting.get(propositionId) == false || isSupporting.get(propositionId) == null  ){
            holder.binding.buttonSupport.setText("Soutenir")
            holder.binding.buttonSupport.setBackgroundColor(ContextCompat.getColor(parentFragment.requireContext(), R.color.purple_500))
        }
        else{
            holder.binding.buttonSupport.setText("Ne plus soutenir")
            holder.binding.buttonSupport.setBackgroundColor(ContextCompat.getColor(parentFragment.requireContext(), R.color.red))
        }

        holder.binding.buttonSupport.setOnClickListener{

            var th = ThreadIsSupporting(propositionId,parentFragment.requireContext())
            th?.start()
            th?.join()

            if(isSupporting.get(propositionId) == false  ) {
                var th2 = ThreadAddSupport(propositionId,parentFragment.requireContext())
                th2?.start()
                th2?.join()

                holder.binding.buttonSupport.setText("Ne plus soutenir")
                holder.binding.buttonSupport.setBackgroundColor(ContextCompat.getColor(parentFragment.requireContext(), R.color.red))
            }
            else{
                var th2 = ThreadDeleteSupport(propositionId,parentFragment.requireContext())
                th2?.start()
                th2?.join()

                holder.binding.buttonSupport.setText("Soutenir")
                holder.binding.buttonSupport.setBackgroundColor(ContextCompat.getColor(parentFragment.requireContext(), R.color.purple_500))
            }

            var thUpdate = ThreadUpdateSupports(propositionId,parentFragment.requireContext())
            thUpdate.start()
            thUpdate.join()
        }

        holder.binding.card.setOnClickListener{
            val args = bundleOf("id" to proposition.propositionId.toString())
            val navController = parentFragment.findNavController()
            navController.navigate(R.id.action_commissionPropositionsFragment_to_propositionFragment, args)
        }
    }
}