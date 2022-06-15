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
data class Paragraph(@PrimaryKey(autoGenerate = true) val paragraphId:Long = 0,
                     @ColumnInfo(name = "num") var num:Int,
                     @ColumnInfo(name = "content") var content:String,
                     var propositionId:String)