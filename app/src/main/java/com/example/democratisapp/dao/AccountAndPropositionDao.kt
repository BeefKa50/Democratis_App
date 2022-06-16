package com.example.democratisapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.democratisapp.classes.AccountAndProposition

@Dao
interface AccountAndPropositionDao {
    @Insert
    fun insertNewPropositionAccountLink(accountAndProposition: AccountAndProposition)

    @Query("SELECT * FROM accountAndProposition")
    fun getAllAccountAndProposition() : List<AccountAndProposition>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putAccountAndPropositionObjects(accountsAndPropositions:List<AccountAndProposition>)
}