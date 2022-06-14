package com.example.democratisapp.ui.commissions

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.democratis.classes.Commission
import com.example.democratisapp.MainActivity
import com.example.democratisapp.database.DemocratisDB
import com.example.democratisapp.databinding.FragmentDashboardBinding
import com.example.democratisapp.ui.login.LoginActivity
import com.example.kotlindeezer.ui.RecyclerCommissionAdapter

class CommissionsFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    companion object{
        var commissions: List<Commission>? = null
        lateinit var fragmentContext:Context
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    class ThreadGetAllCommissions(var context: Context): Thread() {
        public override fun run() {
            System.out.println("Debut du thread get all commissions...")
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            commissions = db.commissionDao().getAllCommissions()
            System.out.println("Commissions : ")
            for(commission in commissions!!){
                System.out.println("Nom de la commission : " + commission.name)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(CommissionsViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        fragmentContext = this.requireContext()

        var th = ThreadGetAllCommissions(this.requireContext())
        th.start()
        th.join()

        _binding!!.commissions.layoutManager = LinearLayoutManager(this.context)
        _binding!!.commissions.adapter = commissions?.let { RecyclerCommissionAdapter(it,this) }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}