package com.example.democratisapp.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.democratis.classes.Paragraph

@Dao
interface ParagraphDao{
    @Query("INSERT INTO paragraph VALUES(2," +
            "1," +
            "'Cette proposition a pour objectif de créer un nouveau crime d''écocide dans le droit français sanctionnant les entreprises participant activement au réchauffement planétaire. En effet, bien trop souvent, les grandes entreprises se sortent indemmes de situations dans lesquelles elles rejettent d''importantes quantités de CO2 dans l''atmosphère. En 2022, au vu de l''urgence climatique soulignée par les experts du GIEC, il est plus que temps d''agir !'," +
            "2)")
    fun insertAbstractCorrida()

    @Query("INSERT INTO paragraph VALUES(1," +
            "1," +
            "'Nous considérons que la corrida est une pratique est inhumaine et profondément malsaine. A des fins de divertissement, des animaux sont blessés injustement. Aucune tradition ne doit pouvoir mettre en jeu la vie d''un animal ou permettre de le blesser.'," +
            "1)")
    fun insertAbstractEcocide()

    @Query("SELECT * FROM paragraph WHERE propositionId = :propositionId")
    fun getPropositionParagraphs(propositionId:Long) : List<Paragraph>

}