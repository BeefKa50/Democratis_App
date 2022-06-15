package com.example.democratis.classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "amendment",foreignKeys = [
    ForeignKey(entity = Proposition::class,
        parentColumns = arrayOf("propositionId"),
        childColumns = arrayOf("propositionId"),
        onDelete = ForeignKey.CASCADE)])
data class Amendment (@PrimaryKey(autoGenerate = true) val ammendmentId :Long = 0,
                 @ColumnInfo(name="submitDate") var submitDate: String,
                 @ColumnInfo(name = "content") var content:String,
                 var propositionId:Int)