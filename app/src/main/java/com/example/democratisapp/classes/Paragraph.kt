package com.example.democratis.classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "paragraph",foreignKeys = [
    ForeignKey(entity = Proposition::class,
        parentColumns = arrayOf("propositionId"),
        childColumns = arrayOf("propositionId"),
        onDelete = ForeignKey.CASCADE)])
data class Paragraph(@PrimaryKey private var paragraphId:Long,
                     @ColumnInfo(name = "num") private var num:Int,
                     @ColumnInfo(name = "content") private var content:String,
                     private var propositionId:String)