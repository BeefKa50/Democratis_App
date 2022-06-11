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
data class GeneralAssembly(@PrimaryKey private var assemblyId:Long,
                      @ColumnInfo(name="startDate") private var startDate:Date,
                      @ColumnInfo(name = "endDate") private var endDate:Date,
                      private var commissionId:Int)