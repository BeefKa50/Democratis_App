package com.example.democratisapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.democratis.classes.Account
import com.example.democratis.classes.Commission
import com.example.democratis.classes.Proposition

@Dao
interface AccountDao{
    @Query("SELECT * FROM  commission JOIN accountAndCommission ON commission.commissionId" +
            "= accountAndCommission.commissionId WHERE accountId = :accountId")
    fun getUserCommissions(accountId: Long): List<Commission>

    @Query("SELECT * FROM  proposition JOIN accountAndProposition ON proposition.propositionId" +
            "= accountAndProposition.propositionId WHERE accountId = :accountId")
    fun getUserPropositions(accountId: Long): List<Proposition>

    @Query("SELECT EXISTS(SELECT * FROM account WHERE username = :login AND " +
            "password = :password)")
    fun login(login:String, password:String): Boolean

    @Insert
    fun createAccount(account: Account) : Long

    @Query("SELECT * FROM account WHERE accountId = :accountId")
    fun getUserById(accountId: Long): Account

    @Query("DELETE FROM account")
    fun deleteAccounts()

    @Query("SELECT * FROM account")
    fun getAllAccounts() : List<Account>
}