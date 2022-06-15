package com.example.democratisapp.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import com.example.democratis.classes.*
import com.example.democratisapp.classes.AccountAndCommission
import com.example.democratisapp.classes.AccountAndProposition
import com.example.democratisapp.dao.*

@Database(entities = [Account::class, AccountAndCommission::class, AccountAndProposition::class,
    Amendment::class, Commission::class, GeneralAssembly::class,
    Paragraph::class, Proposition::class], version = 18, exportSchema = true)
public abstract class DemocratisDB : RoomDatabase() {

    abstract fun accountDao(): AccountDao
    abstract fun commissionDao(): CommissionDao
    abstract fun accountAndCommissionDao(): AccountAndCommissionDao
    abstract fun propositionDao():PropositionDao
    abstract fun paragraphDao():ParagraphDao

    companion object {
        @Volatile
        private var INSTANCE: DemocratisDB? = null

        fun getDatabase(context: Context): DemocratisDB {
            return INSTANCE ?: synchronized(this) {
                // A utiliser en cas de changement de version de la BDD
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DemocratisDB::class.java,
                    "democratis_database"
                ).fallbackToDestructiveMigration().build()

                // A utiliser dans le cas général
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    DemocratisDB::class.java,
//                    "democratis_database"
//                ).build()

                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
