package com.example.democratisapp.recycler_adapters

import android.content.Context
import android.graphics.text.LineBreaker
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.democratis.classes.Paragraph
import com.example.democratis.classes.Proposition
import com.example.democratisapp.R
import com.example.democratisapp.database.DemocratisDB
import com.example.democratisapp.databinding.RecyclerItemParagraphBinding
import com.example.kotlindeezer.ui.RecyclerCommissionAdapter

class RecyclerParagraphAdapter(private val data: List<Paragraph>, val parentFragment: Fragment) : RecyclerView.Adapter<RecyclerParagraphAdapter.ParagraphViewHolder>(){
    companion object {
        private const val TAG = "ParagraphViewHolder"
        lateinit var paragraphs:List<Paragraph>
        lateinit var proposition:Proposition
    }

    inner class ParagraphViewHolder(val binding: RecyclerItemParagraphBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParagraphViewHolder {
        Log.d(TAG, "onCreateViewHolder")

        val binding = RecyclerItemParagraphBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ParagraphViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerParagraphAdapter.ParagraphViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder position=${position}")

        val paragraph: Paragraph = data[position]

        holder.binding.paragraphTitle.setText("Paragraphe " + paragraph.num.toString())
        holder.binding.paragraphContent.setText(paragraph.content)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.binding.paragraphContent.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }
    }
}