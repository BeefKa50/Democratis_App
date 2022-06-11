package com.example.democratis.classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "commission")
data class Commission(@PrimaryKey val commissionId:Long,
                      @ColumnInfo(name="name") private var name:String,
                      @ColumnInfo(name="desc") private var desc:String)