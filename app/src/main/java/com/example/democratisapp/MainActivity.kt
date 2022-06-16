package com.example.democratisapp

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.democratisapp.database.DemocratisDB
import com.example.democratisapp.databinding.ActivityMainBinding
import com.example.democratisapp.ui.commissions.CommissionsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object{
        var profileId: Long? = null
    }

    class ThreadPrepopulate(var context: Context): Thread() {
        public override fun run() {

            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)

            var accountsAndPropositions = db.accountAndPropositionDao().getAllAccountAndProposition()
            var accountsAndCommissions = db.accountAndCommissionDao().getAllAccountAndCommission()
            var propositionSupports = db.propositionSupportsDao().getAllPropositionSupports()

            db.commissionDao().insertDemocratie()
            db.commissionDao().insertEcologie()
            db.commissionDao().insertEconomie()
            db.commissionDao().insertJustice()
            db.commissionDao().insertSante()
            db.commissionDao().insertTravail()
            db.commissionDao().insertEgaliteHF()

            db.propositionDao().insertPropositionEcocide()
            db.propositionDao().insertPropositionForbidCorrida()

            db.paragraphDao().insertAbstractCorrida()
            db.paragraphDao().insertAbstractEcocide()

            db.paragraphDao().insertFirstParagraphEcocide()
            db.paragraphDao().insertSecondParagraphEcocide()
            db.paragraphDao().insertFirstParagraphCorrida()

            db.accountAndPropositionDao().putAccountAndPropositionObjects(accountsAndPropositions)
            db.accountAndCommissionDao().putAccountAndCommissionObjects(accountsAndCommissions)
            db.propositionSupportsDao().putPropositionSupports(propositionSupports)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.accueil, R.id.commissions, R.id.compte
            )
        )

        var th = ThreadPrepopulate(this)
        th.start()
        th.join()

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}