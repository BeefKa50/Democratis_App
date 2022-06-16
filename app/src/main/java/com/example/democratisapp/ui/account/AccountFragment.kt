package com.example.democratisapp.ui.account

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.democratis.classes.Account
import com.example.democratis.classes.Commission
import com.example.democratisapp.MainActivity
import com.example.democratisapp.database.DemocratisDB
import com.example.democratisapp.databinding.FragmentAccountBinding
import com.example.democratisapp.threads.UsefulThreads
import com.example.democratisapp.ui.commissions.CommissionsFragment
import com.example.democratisapp.ui.login.LoginActivity
import com.example.kotlindeezer.ui.RecyclerCommissionAdapter

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object{
        var account: Account? = null
        lateinit var myCommissions:List<Commission>
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(AccountViewModel::class.java)

        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var th: UsefulThreads.ThreadGetUser? = null
        if(MainActivity.profileId != null) {
            th = UsefulThreads.ThreadGetUser(MainActivity.profileId!!, this.requireContext())
        }

        if (th != null) {
            th.start()
        }
        th?.join()
        _binding!!.pseudo.setText(account?.username)
        _binding!!.emailInput.setText(account?.mail)

        var th2 = UsefulThreads.ThreadGetUserCommissions(this.requireContext())
        th2.start()
        th2.join()

        _binding!!.commissions.layoutManager = LinearLayoutManager(this.context)
        _binding!!.commissions.adapter = AccountFragment.myCommissions?.let { RecyclerCommissionAdapter(it,this) }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}