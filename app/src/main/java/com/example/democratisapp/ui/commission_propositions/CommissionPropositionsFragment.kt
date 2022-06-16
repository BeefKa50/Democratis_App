package com.example.democratisapp.ui.commission_propositions

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.democratis.classes.Proposition
import com.example.democratisapp.R
import com.example.democratisapp.database.DemocratisDB
import com.example.democratisapp.databinding.FragmentCommissionPropositionsBinding
import com.example.democratisapp.databinding.FragmentPropositionBinding
import com.example.democratisapp.recycler_adapters.RecyclerCommissionPropositionsAdapter
import com.example.democratisapp.recycler_adapters.RecyclerParagraphAdapter
import com.example.democratisapp.ui.proposition.PropositionFragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CommissionPropositionsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CommissionPropositionsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentCommissionPropositionsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    class ThreadGetCommissionPropositions(var commissionId:Long, var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            propositions = db.propositionDao().getCommissionPropositions(commissionId)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCommissionPropositionsBinding.inflate(inflater, container, false)
        val id:String? = arguments?.getString("id")

        var th = id?.let { ThreadGetCommissionPropositions(it.toLong(),this.requireContext()) }
        th?.start()
        th?.join()

        _binding!!.commissionPropositions.layoutManager = LinearLayoutManager(this.context)
        _binding!!.commissionPropositions.adapter = RecyclerCommissionPropositionsAdapter(
            propositions,this)

        val root: View = binding.root
        return root
    }

    companion object {

        lateinit var propositions:List<Proposition>

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CommissionPropositionsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}