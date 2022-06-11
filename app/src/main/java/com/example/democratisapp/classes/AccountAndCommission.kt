package com.example.democratisapp.classes

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.democratis.classes.Account
import com.example.democratis.classes.Commission

@Entity(tableName = "accountAndCommission",foreignKeys = [
    ForeignKey(entity = Account::class,
        parentColumns = arrayOf("accountId"),
        childColumns = arrayOf("accountId"),
        onDelete = ForeignKey.CASCADE),
    ForeignKey(entity = Commission::class,
        parentColumns = arrayOf("commissionId"),
        childColumns = arrayOf("commissionId"),
        onDelete = ForeignKey.CASCADE)])
data class AccountAndCommission(@PrimaryKey(autoGenerate = true) val id:Long,
                                var accountId:Long,
                                var commissionId:Long)