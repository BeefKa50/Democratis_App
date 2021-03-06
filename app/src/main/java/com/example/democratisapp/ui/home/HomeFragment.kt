package com.example.democratisapp.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.democratis.classes.Proposition
import com.example.democratisapp.MainActivity
import com.example.democratisapp.database.DemocratisDB
import com.example.democratisapp.databinding.FragmentHomeBinding
import com.example.democratisapp.recycler_adapters.RecyclerPropositionAdapter
import com.example.democratisapp.threads.UsefulThreads
import com.example.democratisapp.ui.commissions.CommissionsFragment
import com.example.kotlindeezer.ui.RecyclerCommissionAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    companion object{
        lateinit var propositions:List<Proposition>
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var th = UsefulThreads.ThreadGetAllPropositions(this.requireContext())
        th.start()
        th.join()

        _binding!!.propositions.layoutManager = LinearLayoutManager(this.context)
        _binding!!.propositions.adapter = RecyclerPropositionAdapter(propositions,this,"home")

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}