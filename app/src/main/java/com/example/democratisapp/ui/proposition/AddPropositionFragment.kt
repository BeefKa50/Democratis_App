package com.example.democratisapp.ui.proposition

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.democratis.classes.Account
import com.example.democratis.classes.Paragraph
import com.example.democratis.classes.Proposition
import com.example.democratisapp.MainActivity
import com.example.democratisapp.MainActivity.Companion.profileId
import com.example.democratisapp.R
import com.example.democratisapp.classes.AccountAndProposition
import com.example.democratisapp.databinding.FragmentAddPropositionBinding
import com.example.democratisapp.databinding.FragmentPropositionBinding
import com.example.democratisapp.threads.UsefulThreads
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddPropositionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddPropositionFragment : Fragment() {

    private var _binding: FragmentAddPropositionBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true);
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddPropositionBinding.inflate(inflater, container, false)

        _binding!!.button.setOnClickListener {
            if(_binding!!.titleContent.text.toString() != "" &&
                _binding!!.abstractContent.text.toString() != "" &&
                _binding!!.paragraph1Content.text.toString() != "") {
                var newProposition: Proposition = profileId?.let {
                    arguments?.getString("id")?.toInt()?.let { it1 ->
                        Proposition(
                            picture = null,
                            title = binding.titleContent.text.toString(),
                            submitDate = Date().toString(),
                            status = "COLLECTING_SUPPORTS",
                            authorId = it.toInt(),
                            nbSupports = 0,
                            inFavorVotes = 0,
                            againstVotes = 0,
                            commissionId = it1,
                            assemblyId = 0
                        )
                    }
                }!!

                var thAddProp =
                    UsefulThreads.ThreadInsertNewProposition(newProposition, this.requireContext())
                thAddProp.start()
                thAddProp.join()

                var accountAndProposition:AccountAndProposition = AccountAndProposition(
                    accountId= MainActivity.profileId!!, propositionId = newPropId)

                var thAddLink =
                    UsefulThreads.ThreadInsertPropositionAccountLink(accountAndProposition,
                        this.requireContext())
                thAddLink.start()
                thAddLink.join()

                var abstract: Paragraph = Paragraph(
                    num = 0,
                    content = _binding!!.abstractContent.text.toString(),
                    propositionId = newPropId.toString()
                )

                var paragraph1: Paragraph = Paragraph(
                    num = 1,
                    content = _binding!!.paragraph1Content.text.toString(),
                    propositionId = newPropId.toString()
                )

                var paragraph2: Paragraph = Paragraph(
                    num = 2,
                    content = _binding!!.paragraph2Content.text.toString(),
                    propositionId = newPropId.toString()
                )

                var paragraph3: Paragraph = Paragraph(
                    num = 3,
                    content = _binding!!.paragraph3Content.text.toString(),
                    propositionId = newPropId.toString()
                )

                var thAddParagraph =
                    UsefulThreads.ThreadInsertNewParagraph(abstract, this.requireContext())
                thAddParagraph.start()
                thAddParagraph.join()

                thAddParagraph =
                    UsefulThreads.ThreadInsertNewParagraph(paragraph1, this.requireContext())
                thAddParagraph.start()
                thAddParagraph.join()

                if(_binding!!.paragraph2Content.text.toString() != "") {
                    thAddParagraph =
                        UsefulThreads.ThreadInsertNewParagraph(paragraph2, this.requireContext())
                    thAddParagraph.start()
                    thAddParagraph.join()
                }

                if(_binding!!.paragraph3Content.text.toString() != "") {
                    thAddParagraph =
                        UsefulThreads.ThreadInsertNewParagraph(paragraph3, this.requireContext())
                    thAddParagraph.start()
                    thAddParagraph.join()
                }

                val args = bundleOf("id" to id)
                val navController = this.findNavController()
                navController.navigate(R.id.action_addPropositionFragment_to_commissionPropositionsFragment, args)
            }
            else{
                Toast.makeText(this.requireContext(),
                    "Veuillez saisir au moins un titre, un abstract et un paragraphe.",
                    Toast.LENGTH_LONG).show()
            }
        }

        val root: View = binding.root
        return root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        var newPropId:Long = 0
    }
}