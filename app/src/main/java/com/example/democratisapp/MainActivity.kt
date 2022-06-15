package com.example.democratisapp

import android.content.Context
import android.os.Bundle
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

    class ThreadPrepopulateCommissions(var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)

            db.commissionDao().insertDemocratie()
            db.commissionDao().insertEcologie()
            db.commissionDao().insertEconomie()
            db.commissionDao().insertJustice()
            db.commissionDao().insertSante()
            db.commissionDao().insertTravail()
            db.commissionDao().insertEgaliteHF()
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

        var th = ThreadPrepopulateCommissions(this)
        th.start()
        th.join()

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}