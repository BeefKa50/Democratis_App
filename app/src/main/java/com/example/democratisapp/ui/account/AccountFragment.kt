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
import com.example.democratisapp.databinding.FragmentNotificationsBinding
import com.example.democratisapp.ui.commissions.CommissionsFragment
import com.example.democratisapp.ui.login.LoginActivity
import com.example.kotlindeezer.ui.RecyclerCommissionAdapter

class AccountFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object{
        var account: Account? = null
        lateinit var myCommissions:List<Commission>
    }

    class ThreadGetUser(var userId:Long,
                           var context: Context
    ): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            account = db.accountDao().getUserById(userId)
        }
    }


    class ThreadGetUserCommissions(var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            myCommissions = MainActivity.profileId?.let { db.accountDao().getUserCommissions(it) }!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(AccountViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var th: ThreadGetUser? = null
        if(MainActivity.profileId != null) {
            th = ThreadGetUser(MainActivity.profileId!!,this.requireContext())
        }

        if (th != null) {
            th.start()
        }
        th?.join()
        _binding!!.pseudo.setText(account?.username)
        _binding!!.emailInput.setText(account?.mail)

        var th2 = ThreadGetUserCommissions(this.requireContext())
        th2.start()
        th2.join()

        _binding!!.commissions.layoutManager = LinearLayoutManager(this.context)
        _binding!!.commissions.adapter = CommissionsFragment.commissions?.let { RecyclerCommissionAdapter(it,this) }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}