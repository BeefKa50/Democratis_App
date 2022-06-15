package com.example.democratisapp.recycler_adapters

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
import com.example.democratis.classes.Paragraph
import com.example.democratis.classes.Proposition
import com.example.democratisapp.MainActivity
import com.example.democratisapp.R
import com.example.democratisapp.classes.AccountAndCommission
import com.example.democratisapp.database.DemocratisDB
import com.example.democratisapp.databinding.RecyclerItemCommissionsBinding
import com.example.democratisapp.databinding.RecyclerItemPropositionsBinding
import com.example.democratisapp.ui.commissions.CommissionsFragment
import java.util.concurrent.ConcurrentHashMap

class RecyclerPropositionAdapter(private val data: List<Proposition>, val parentFragment: Fragment) : RecyclerView.Adapter<RecyclerPropositionAdapter.PropositionViewHolder>(){
    companion object {
        private const val TAG = "PropositionViewHolder"
        lateinit var paragraphs:List<Paragraph>
    }

    inner class PropositionViewHolder(val binding: RecyclerItemPropositionsBinding) :
        RecyclerView.ViewHolder(binding.root)

    class ThreadGetPropositionParagraphs(var propositionId:Long, var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            paragraphs = db.paragraphDao().getPropositionParagraphs(propositionId)
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropositionViewHolder {
        Log.d(TAG, "onCreateViewHolder")

        val binding = RecyclerItemPropositionsBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return PropositionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerPropositionAdapter.PropositionViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder position=${position}")

        val proposition:Proposition = data[position]

        holder.binding.propositionName.setText(proposition.title)
        holder.binding.propositionSupports.setText(proposition.nbSupports.toString() + " soutien(s)")

        var th = ThreadGetPropositionParagraphs(proposition.propositionId,parentFragment.requireContext())
        th.start()
        th.join()

        var paragraph:Paragraph = paragraphs.get(0)
        holder.binding.propositionDescription.setText(paragraph.content)

//        holder.binding.card.setOnClickListener{
//            val args = bundleOf("album" to album.title, "id" to album.id.toString())
//            val navController = parentFragment.findNavController()
//            navController.navigate(R.id.action_AlbumFragment_to_TrackFragment, args)
//        }

    }
}