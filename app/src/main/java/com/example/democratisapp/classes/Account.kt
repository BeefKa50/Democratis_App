package com.example.democratis.classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class Account (@PrimaryKey(autoGenerate = true) val accountId:Long = 0,
                    @ColumnInfo(name = "username") var username:String,
                    @ColumnInfo(name = "mail") var mail:String,
                    @ColumnInfo(name = "password") var password:String){
}