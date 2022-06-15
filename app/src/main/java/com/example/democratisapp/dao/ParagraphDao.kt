package com.example.democratisapp.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.democratis.classes.Paragraph

@Dao
interface ParagraphDao{
    @Query("INSERT INTO paragraph VALUES(2," +
            "0," +
            "'Cette proposition a pour objectif de créer un nouveau crime d''écocide dans le droit français sanctionnant les entreprises participant activement au réchauffement planétaire. En effet, bien trop souvent, les grandes entreprises se sortent indemmes de situations dans lesquelles elles rejettent d''importantes quantités de CO2 dans l''atmosphère. En 2022, au vu de l''urgence climatique soulignée par les experts du GIEC, il est plus que temps d''agir !'," +
            "2)")
    fun insertAbstractEcocide()

    @Query("INSERT INTO paragraph VALUES(3," +
            "1," +
            "'Les atteintes les plus graves commises intentionnellement à l’environnement seront passibles d’une peine maximale de 10 ans de prison et 4,5 millions d’euros d’amende (22,5 millions d’euros pour les personnes morales), voire une amende allant jusqu’à dix fois le bénéfice obtenu par l’auteur du dommage commis à l’environnement.'," +
            "2)")
    fun insertFirstParagraphEcocide()

    @Query("INSERT INTO paragraph VALUES(4," +
            "2," +
            "'Le fait d’avoir exposé l’environnement à un risque de dégradation durable de la faune, de la flore ou de l’eau en violant une obligation de sécurité ou de prudence pourra être sanctionné de 3 ans de prison et 250 000 euros d’amende. Contrairement au délit général de pollution, les sanctions pourront s’appliquer si le comportement est dangereux et que la pollution n’a pas eu lieu ;'," +
            "2)")
    fun insertSecondParagraphEcocide()

    @Query("INSERT INTO paragraph VALUES(1," +
            "0," +
            "'Nous considérons que la corrida est une pratique inhumaine et profondément malsaine. A des fins de divertissement, des animaux sont blessés injustement. Aucune tradition ne doit pouvoir mettre en jeu la vie d''un animal ou permettre de le blesser.'," +
            "1)")
    fun insertAbstractCorrida()

    @Query("INSERT INTO paragraph VALUES(5," +
            "1," +
            "'La dérogation actuelle inscrite à l''article 521-1 du code pénal qui accorde aux courses de taureaux et aux combats de coqs une dérogation à la pénalisation des sévices imposés aux animaux est supprimée. '," +
            "1)")
    fun insertFirstParagraphCorrida()

    @Query("SELECT * FROM paragraph WHERE propositionId = :propositionId")
    fun getPropositionParagraphs(propositionId:Long) : List<Paragraph>

}