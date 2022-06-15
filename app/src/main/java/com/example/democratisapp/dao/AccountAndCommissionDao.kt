package com.example.democratisapp.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.democratisapp.classes.AccountAndCommission

@Dao
interface AccountAndCommissionDao {
    @Insert
    fun addNewMemberToCommission(accountAndCommission: AccountAndCommission)
}