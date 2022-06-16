package com.example.democratisapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.democratisapp.classes.AccountAndProposition

@Dao
interface AccountAndPropositionDao {
    @Insert
    fun insertNewPropositionAccountLink(accountAndProposition: AccountAndProposition)
}