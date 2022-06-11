package com.example.democratis.classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class Account (@PrimaryKey val accountId:Long,
               @ColumnInfo(name = "mail") private var mail:String,
               @ColumnInfo(name = "password") private var password:String)