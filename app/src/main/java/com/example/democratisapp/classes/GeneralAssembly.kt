package com.example.democratis.classes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "generalAssembly",foreignKeys = [
    ForeignKey(entity = Commission::class,
        parentColumns = arrayOf("commissionId"),
        childColumns = arrayOf("commissionId"),
        onDelete = ForeignKey.CASCADE)])
data class GeneralAssembly(@PrimaryKey(autoGenerate = true) val assemblyId:Long = 0,
                      @ColumnInfo(name="startDate") var startDate:String,
                      @ColumnInfo(name = "endDate") var endDate:String,
                      var commissionId:Int)