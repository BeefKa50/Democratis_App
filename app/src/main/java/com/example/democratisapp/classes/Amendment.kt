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
class Amendment (@PrimaryKey private val ammendmentId :Long,
                 @ColumnInfo(name="submitDate") private var submitDate: Date,
                 @ColumnInfo(name = "content") private var content:String,
                 private var propositionId:Int)