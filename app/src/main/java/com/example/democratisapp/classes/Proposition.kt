package com.example.democratis.classes

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import java.util.*

@Entity(tableName = "proposition",foreignKeys = [
    ForeignKey(entity = Account::class,
        parentColumns = arrayOf("accountId"),
        childColumns = arrayOf("authorId"),
        onDelete = CASCADE),
    ForeignKey(entity = Commission::class,
        parentColumns = arrayOf("commissionId"),
        childColumns = arrayOf("commissionId"),
        onDelete = CASCADE),
    ForeignKey(entity = GeneralAssembly::class,
        parentColumns = arrayOf("assemblyId"),
        childColumns = arrayOf("assemblyId"),
        onDelete = CASCADE)])
data class Proposition (@PrimaryKey(autoGenerate = true) val propositionId: Long,
                        @ColumnInfo(name = "picture") var picture:String?,
                        @ColumnInfo(name = "title") var title:String,
                        @ColumnInfo(name = "submitDate") var submitDate:String,
                        @ColumnInfo(name = "status") var status:String,
                        @ColumnInfo(name = "authorId") var authorId:Int,
                        @ColumnInfo(name = "nbSupports") var nbSupports:Int,
                        @ColumnInfo(name = "inFavorVotes") var inFavorVotes:Int,
                        @ColumnInfo(name = "againstVotes") var againstVotes:Int,
                        var commissionId:Int,
                        var assemblyId:Int)