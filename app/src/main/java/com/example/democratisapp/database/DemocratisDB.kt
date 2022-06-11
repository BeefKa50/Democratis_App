package com.example.democratisapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.democratis.classes.*
import com.example.democratisapp.classes.AccountAndCommission
import com.example.democratisapp.classes.AccountAndProposition
import com.example.democratisapp.dao.AccountDao

@Database(entities = [Account::class, AccountAndCommission::class, AccountAndProposition::class,
    Amendment::class, Commission::class, GeneralAssembly::class,
    Paragraph::class, Proposition::class], version = 1, exportSchema = false)
public abstract class DemocratisDB : RoomDatabase() {

    abstract fun accountDao(): AccountDao

    companion object {
        @Volatile
        private var INSTANCE: DemocratisDB? = null

        fun getDatabase(context: Context): DemocratisDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DemocratisDB::class.java,
                    "democratis_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
