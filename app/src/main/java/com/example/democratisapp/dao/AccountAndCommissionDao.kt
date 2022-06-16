package com.example.democratisapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.democratisapp.classes.AccountAndCommission
import com.example.democratisapp.classes.AccountAndProposition

@Dao
interface AccountAndCommissionDao {
    @Insert
    fun addNewMemberToCommission(accountAndCommission: AccountAndCommission)

    @Query("SELECT * FROM accountAndCommission")
    fun getAllAccountAndCommission() : List<AccountAndCommission>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putAccountAndCommissionObjects(accountAndCommission:List<AccountAndCommission>)
}