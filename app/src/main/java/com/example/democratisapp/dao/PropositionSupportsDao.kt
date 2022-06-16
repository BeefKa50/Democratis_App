package com.example.democratisapp.dao

import androidx.room.*
import com.example.democratisapp.classes.AccountAndProposition
import com.example.democratisapp.classes.PropositionSupports

@Dao
interface PropositionSupportsDao {
    @Insert
    fun addSupport(propositionSupports: PropositionSupports)

    @Query("DELETE FROM propositionSupports WHERE accountId = :accountId AND propositionId = :propositionId")
    fun deleteSupport(accountId:Long, propositionId: Long)

    @Query("SELECT EXISTS(SELECT * FROM propositionSupports WHERE propositionId = :propositionId AND accountId = :accountId)")
    fun isSupporting(accountId:Long, propositionId:Long):Boolean

    @Query("SELECT COUNT(*) FROM propositionSupports WHERE propositionId = :propositionId")
    fun countSupports(propositionId:Long):Long

    @Query("SELECT * FROM propositionSupports")
    fun getAllPropositionSupports() : List<PropositionSupports>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putPropositionSupports(propositionSupports:List<PropositionSupports>)
}