package com.example.democratisapp.ui.proposition

import android.content.Context
import android.graphics.text.LineBreaker
import android.os.Build
import android.os.Bundle
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.democratis.classes.Paragraph
import com.example.democratis.classes.Proposition
import com.example.democratisapp.database.DemocratisDB
import com.example.democratisapp.databinding.FragmentPropositionBinding
import com.example.democratisapp.recycler_adapters.RecyclerParagraphAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PropositionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PropositionFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    companion object {

        lateinit var paragraphs:List<Paragraph>
        lateinit var proposition: Proposition

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PropositionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    class ThreadPropositionById(var propositionId:Long,
                                var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            proposition = db.propositionDao().getPropositionById(propositionId)
        }
    }

    class ThreadGetPropositionParagraphs(var propositionId:Long, var context: Context): Thread() {
        public override fun run() {
            System.out.println("Début du thread de récupération des paragraphes...")
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            paragraphs = db.paragraphDao().getPropositionParagraphs(propositionId)
        }
    }

    private var _binding: FragmentPropositionBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentPropositionBinding.inflate(inflater, container, false)
        val id:String? = arguments?.getString("id")

        var th = id?.let { ThreadGetPropositionParagraphs(it.toLong(),this.requireContext()) }
        if (th != null) {
            th.start()
        }
        th?.join()

        _binding!!.propositionAbstractContent.setText(paragraphs.get(0).content)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            _binding!!.propositionAbstractContent.justificationMode = LineBreaker.JUSTIFICATION_MODE_INTER_WORD
        }
        _binding!!.paragraphs.layoutManager = LinearLayoutManager(this.context)
        _binding!!.paragraphs.adapter = RecyclerParagraphAdapter(paragraphs.subList(1,paragraphs.size),this)

        var th2 = id?.let { ThreadPropositionById(it.toLong(),this.requireContext()) }
        th2?.start()
        th2?.join()
        _binding!!.nbSupports.setText(proposition.nbSupports.toString() + " soutien(s)")

        val root: View = binding.root

        return root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}