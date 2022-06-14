package com.example.democratisapp.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.democratis.classes.Commission

@Dao
interface CommissionDao {
    @Query("SELECT * from commission")
    fun getAllCommissions(): List<Commission>

    @Query("REPLACE INTO commission VALUES (1," +
            "'https://pixnio.com/free-images/2017/09/14/2017-09-14-06-01-11-1100x733.jpg', " +
            "'Écologie','La commission Écologie a pour vocation d''émettre des propositions concernant la protection des eaux, la biodiversité, le réchauffement climatique ou encore le bien-être animal.')")
    fun insertEcologie()

    @Query("REPLACE INTO commission VALUES (2," +
            "'https://www.lemondemoderne.media/wp-content/uploads/2021/04/32559700202_bab8814c4b_o-scaled-1.jpg', " +
            "'Démocratie', " +
            "'Ici sont débattues toutes les idées ayant trait à la démocratie et les institutions de manièren générale.')")
    fun insertDemocratie()

    @Query("REPLACE INTO commission VALUES (3," +
            "'https://www.rivesdelyon.fr/medias/2019/10/justice-2060093_1920.jpg', " +
            "'Justice', " +
            "'Les propositions débattues dans cette commission relèvent du domaine judiciaire, des modalités d''interpellation à la prison.')")
    fun insertJustice()

    @Query("REPLACE INTO commission VALUES (4," +
            "'https://live.staticflickr.com/4870/32236509828_5e09efd549_b.jpg', " +
            "'Travail', " +
            "'Cette commission a pour but principal de proposer des idées en lien avec le monde du travail. Les thèmes concernés sont multiples : chômage, salaires, embauche ou encore droits sociaux.')")
    fun insertTravail()

    @Query("REPLACE INTO commission VALUES (5," +
            "'https://lepetitjournal.com/sites/default/files/styles/main_article/public/2022-05/stock-tracker.jpg?itok=hFVSc1oj', " +
            "'Économie', " +
            "'Les propositions soumises dans la commission Économie sont relatives aux modèles économiques, à l''activité des entreprises, au commerce ou encore aux taxes et impôts')")
    fun insertEconomie()

    @Query("REPLACE INTO commission VALUES (6," +
            "'https://www.creapharma.ch/wp-content/uploads/2019/10/4-01-2018-Alexander-Raths.jpg', " +
            "'Santé', " +
            "'La commission Santé a pour objectif principal de cerner les enjeux relatifs à la santé publique et de proposer des améliorations ou des solutions, par exemple pour faire face à l''émergence d''une épidémie.')")
    fun insertSante()

    @Query("REPLACE INTO commission VALUES (7," +
            "'https://cdn.pixabay.com/photo/2013/04/06/09/20/man-101001_1280.jpg', " +
            "'Égalité Hommes-Femmes', " +
            "'Cette commission cherche à débattre d''idées pouvant améliorer l''égalité entre les hommes et les femmes dans l''ensemble des domaines de la société.')")
    fun insertEgaliteHF()

    @Query("SELECT EXISTS (SELECT * FROM COMMISSION WHERE name = :name)")
    fun commissionExists(name:String) : Boolean


}