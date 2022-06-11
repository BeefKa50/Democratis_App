package com.example.democratisapp.classes

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.democratis.classes.Account
import com.example.democratis.classes.Commission
import com.example.democratis.classes.Proposition

@Entity(tableName = "accountAndProposition",foreignKeys = [
    ForeignKey(entity = Account::class,
        parentColumns = arrayOf("accountId"),
        childColumns = arrayOf("accountId"),
        onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = Proposition::class,
        parentColumns = arrayOf("propositionId"),
        childColumns = arrayOf("propositionId"),
        onDelete = ForeignKey.CASCADE)])
data class AccountAndProposition(@PrimaryKey private var id:Long,
                                 private var accountId:Long,
                                 private var propositionId:Long)