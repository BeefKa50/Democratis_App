package com.example.democratis.classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "commission")
data class Commission(@PrimaryKey(autoGenerate = true) val commissionId:Long,
                      @ColumnInfo(name="name") var name:String,
                      @ColumnInfo(name="desc") var desc:String)