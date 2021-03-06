package com.example.democratisapp.threads

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.example.democratis.classes.Account
import com.example.democratis.classes.Paragraph
import com.example.democratis.classes.Proposition
import com.example.democratisapp.MainActivity
import com.example.democratisapp.classes.AccountAndCommission
import com.example.democratisapp.classes.AccountAndProposition
import com.example.democratisapp.classes.PropositionSupports
import com.example.democratisapp.database.DemocratisDB
import com.example.democratisapp.recycler_adapters.RecyclerCommissionPropositionsAdapter
import com.example.democratisapp.recycler_adapters.RecyclerPropositionAdapter
import com.example.democratisapp.recycler_adapters.RecyclerPropositionAdapter.Companion.nbSupportsHome
import com.example.democratisapp.ui.account.AccountFragment
import com.example.democratisapp.ui.commission_propositions.CommissionPropositionsFragment
import com.example.democratisapp.ui.commissions.CommissionsFragment
import com.example.democratisapp.ui.home.HomeFragment
import com.example.democratisapp.ui.login.LoginActivity
import com.example.democratisapp.ui.proposition.AddPropositionFragment.Companion.newPropId
import com.example.kotlindeezer.ui.RecyclerCommissionAdapter

class UsefulThreads {
    class ThreadAddNewMemberToCommission(var accountId:Long,
                                         var commissionId:Long,
                                         var context: Context
    ): Thread() {
        public override fun run() {
            var th =
                MainActivity.profileId?.let { ThreadIsMemberOfCommission(it,commissionId,context) }
            th?.start()
            th?.join()
            if(RecyclerCommissionAdapter.isMemberOfCommission.get(commissionId) == false) {
                var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
                db.accountAndCommissionDao().addNewMemberToCommission(AccountAndCommission(accountId=accountId, commissionId = commissionId))
            }
        }
    }

    class ThreadDeleteMemberInCommission(var accountId:Long,
                                         var commissionId:Long,
                                         var context: Context
    ): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            var th =
                MainActivity.profileId?.let { ThreadIsMemberOfCommission(it,commissionId,context) }
            th?.start()
            th?.join()

            if(RecyclerCommissionAdapter.isMemberOfCommission.get(commissionId) == true) {
                db.commissionDao().deleteMemberInCommission(accountId, commissionId)
            }
        }
    }

    class ThreadIsMemberOfCommission(var accountId:Long,
                                     var commissionId:Long,
                                     var context: Context
    ): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            RecyclerCommissionAdapter.isMemberOfCommission.put(commissionId,db.commissionDao().isMemberOfCommission(accountId,commissionId))
        }
    }

    class ThreadUpdateCommissionMembers(var commissionId:Long,
                                        var context: Context
    ): Thread() {
        @Synchronized private fun runOnUiThread(task:Runnable){
            Handler(Looper.getMainLooper()).post(task)
        }

        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)

            var nbMembers: Long = db.commissionDao().countCommissionMembers(commissionId)

            runOnUiThread(Runnable {
                RecyclerCommissionAdapter.membersTextViews.get(commissionId)?.setText(nbMembers.toString() + " membre(s)")
            })
        }
    }

    class ThreadIsSupporting(var propositionId:Long, var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            RecyclerCommissionPropositionsAdapter.isSupporting.put(propositionId,MainActivity.profileId?.let { db.propositionSupportsDao().isSupporting(it,propositionId) } == true)
        }
    }

    class ThreadGetPropositionParagraphs(var propositionId:Long, var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            RecyclerCommissionPropositionsAdapter.paragraphs = db.paragraphDao().getPropositionParagraphs(propositionId)
        }
    }

    class ThreadAddSupport(var propositionId:Long, var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            MainActivity.profileId?.let { PropositionSupports(accountId= it, propositionId = propositionId) }
                ?.let { db.propositionSupportsDao().addSupport(it) }
        }
    }

    class ThreadDeleteSupport(var propositionId:Long, var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            MainActivity.profileId?.let { db.propositionSupportsDao().deleteSupport(it,propositionId) }
        }
    }

    class ThreadUpdateSupports(var propositionId:Long,
                               var context: Context): Thread() {
        @Synchronized private fun runOnUiThread(task:Runnable){
            Handler(Looper.getMainLooper()).post(task)
        }

        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)

            var nbMembers: Long = db.propositionSupportsDao().countSupports(propositionId)

            runOnUiThread(Runnable {
                RecyclerCommissionPropositionsAdapter.supportViews.get(propositionId)?.setText(nbMembers.toString() + " soutien(s)")
            })
        }
    }

    class ThreadGetPropositionParagraphs2(var propositionId:Long, var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            RecyclerPropositionAdapter.paragraphs = db.paragraphDao().getPropositionParagraphs(propositionId)
        }
    }

    class ThreadGetUser(var userId:Long,
                        var context: Context
    ): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            AccountFragment.account = db.accountDao().getUserById(userId)
        }
    }


    class ThreadGetUserCommissions(var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            AccountFragment.myCommissions = MainActivity.profileId?.let { db.accountDao().getUserCommissions(it) }!!
        }
    }

    class ThreadGetUserPropositions(var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            AccountFragment.myPropositions = MainActivity.profileId?.let { db.accountDao().getUserPropositions(it) }!!
        }
    }


    class ThreadGetCommissionPropositions(var commissionId:Long, var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            CommissionPropositionsFragment.propositions = db.propositionDao().getCommissionPropositions(commissionId)
        }
    }

    class ThreadGetAllCommissions(var context: Context): Thread() {
        public override fun run() {
            System.out.println("Debut du thread get all commissions...")
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            CommissionsFragment.commissions = db.commissionDao().getAllCommissions()
            System.out.println("Commissions : ")
            for(commission in CommissionsFragment.commissions!!){
                System.out.println("Nom de la commission : " + commission.name)
            }
        }
    }

    class ThreadGetAllPropositions(var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            HomeFragment.propositions = db.propositionDao().getAllPropositions()
        }
    }

    class ThreadCheckLogin(var username:String,var password:String,
                           var context: Context
    ): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            LoginActivity.loginSuccessful = db.accountDao().login(username,password)
            MainActivity.profileId = db.accountDao().getUser(username,password)
        }
    }

    class ThreadLoginDB(var username:String,var password:String,
                        var mail:String, var context: Context
    ): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            //db.accountDao().deleteAccounts()
            var id:Long = db.accountDao().createAccount(
                Account(
                    picture =null, username =this.username, mail =this.mail,
                    password = this.password)
            )
            var acc: Account = db.accountDao().getUserById(id)
        }
    }

    class ThreadCountSupport(var propositionId:Long, var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            nbSupportsHome = db.propositionSupportsDao().countSupports(propositionId)
        }
    }

    class ThreadInsertNewProposition(var proposition:Proposition, var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            newPropId = db.propositionDao().insertNewProposition(proposition)
        }
    }

    class ThreadInsertNewParagraph(var paragraph: Paragraph, var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            db.paragraphDao().insertNewParagraph(paragraph)
        }
    }

    class ThreadIncrementNbSupports(var propositionId:Long, var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            db.propositionDao().incrementNbSupports(propositionId)
        }
    }

    class ThreadDecrementNbSupports(var propositionId:Long, var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            db.propositionDao().decrementNbSupports(propositionId)
        }
    }

    class ThreadInsertPropositionAccountLink(var accountAndProposition: AccountAndProposition, var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            db.accountAndPropositionDao().insertNewPropositionAccountLink(accountAndProposition)
        }
    }

    class ThreadUpdateMail(var accountId:Long, var mail:String, var context: Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            db.accountDao().changeMail(accountId,mail)
        }
    }
}