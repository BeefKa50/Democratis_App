package com.example.democratisapp.ui.commissions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.democratis.classes.Commission
import com.example.democratisapp.MainActivity
import com.example.democratisapp.database.DemocratisDB
import com.example.democratisapp.databinding.FragmentCommissionsBinding
import com.example.democratisapp.threads.UsefulThreads
import com.example.kotlindeezer.ui.RecyclerCommissionAdapter

class CommissionsFragment : Fragment() {

    private var _binding: FragmentCommissionsBinding? = null

    companion object{
        var commissions: List<Commission>? = null
        lateinit var fragmentContext:Context
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(CommissionsViewModel::class.java)

        _binding = FragmentCommissionsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        fragmentContext = this.requireContext()

        var th = UsefulThreads.ThreadGetAllCommissions(this.requireContext())
        th.start()
        th.join()

        _binding!!.commissions.layoutManager = LinearLayoutManager(this.context)
        _binding!!.commissions.adapter = commissions?.let { RecyclerCommissionAdapter(it,this, from="commissions") }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}