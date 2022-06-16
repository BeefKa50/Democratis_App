package com.example.democratisapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.democratis.classes.Proposition

@Dao
interface PropositionDao {
    @Query("REPLACE INTO proposition VALUES(1, " +
            "null," +
            "'Abolition de la corrida'," +
            "'15/06/2022'," +
            "'COLLECTING_SUPPORTS'," +
            "1," +
            "0," +
            "0," +
            "0," +
            "1," +
            "0)")
    fun insertPropositionForbidCorrida()

    @Query("REPLACE INTO proposition VALUES(2, " +
            "null," +
            "'Création d''un crime d''écocide'," +
            "'15/06/2022'," +
            "'COLLECTING_SUPPORTS'," +
            "1," +
            "0," +
            "0," +
            "0," +
            "1," +
            "0)")
    fun insertPropositionEcocide()

    @Query("SELECT * FROM Proposition ORDER BY nbSupports DESC")
    fun getAllPropositions() : List<Proposition>

    @Query("UPDATE proposition SET nbSupports = nbSupports+1 WHERE propositionId = :propositionId")
    fun incrementNbSupports(propositionId:Long)

    @Query("UPDATE proposition SET nbSupports = nbSupports-1 WHERE propositionId = :propositionId")
    fun decrementNbSupports(propositionId:Long)

    @Query("SELECT * FROM proposition WHERE propositionId = :propositionId")
    fun getPropositionById(propositionId:Long):Proposition

    @Query("SELECT * FROM proposition WHERE commissionId = :commissionId")
    fun getCommissionPropositions(commissionId:Long) : List<Proposition>

    @Insert
    fun insertNewProposition(proposition:Proposition) : Long
}