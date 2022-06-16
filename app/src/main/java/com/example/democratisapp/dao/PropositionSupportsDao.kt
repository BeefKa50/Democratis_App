package com.example.democratisapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
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
}