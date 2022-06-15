package com.example.democratisapp.dao

import androidx.room.Dao
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

    @Query("SELECT * FROM Proposition")
    fun getAllPropositions() : List<Proposition>

    //             "'Cette proposition a pour objectif de créer un nouveau crime d''écocide dans le droit français sanctionnant les entreprises participant activement au réchauffement planétaire. En effet, bien trop souvent, les grandes entreprises se sortent indemmes de situations dans lesquelles elles rejettent d''importantes quantités de CO2 dans l''atmosphère. En 2022, au vu de l''urgence climatique soulignée par les experts du GIEC, il est plus que temps d''agir.'," +
    //             "'Nous considérons que la corrida est une pratique est inhumaine et profondément malsaine. A des fins de divertissement, des animaux sont blessés injustement. Aucune tradition ne doit pouvoir mettre en jeu la vie d''un animal ou permettre de le blesser Interdisons la corrida.'," +
}