package com.example.democratisapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.democratis.classes.Account
import com.example.democratis.classes.Commission
import com.example.democratis.classes.Proposition

@Dao
interface AccountDao{
//    @Query("SELECT * FROM  commission WHERE commissionId = " +
//            "(SELECT * FROM accountAndCommission WHERE accountId = :accountId)")
//    fun getUserCommissions(accountId: Long): List<Commission>
//
//    @Query("SELECT * FROM  commission WHERE commissionId = " +
//            "(SELECT * FROM accountAndCommission WHERE accountId = :accountId)")
//    fun getUserPropositions(accountId: Long): List<Proposition>
//
//    @Query("SELECT accountId FROM  account WHERE mail = :login AND password = :password")
//    fun getUserPropositions(login:String, password:String): Long

    @Insert
    fun createAccount(account: Account)

    @Query("SELECT * FROM account WHERE accountId = :accountId")
    fun getUserById(accountId: Long): Account
}