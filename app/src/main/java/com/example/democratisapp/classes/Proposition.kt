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
data class Proposition (@PrimaryKey private val propositionId: Long,
                        @ColumnInfo(name = "title") private var title:String,
                        @ColumnInfo(name = "submitDate") private var submitDate:Date,
                        @ColumnInfo(name = "status") private var status:String,
                        @ColumnInfo(name = "authorId") private var authorId:Int,
                        @ColumnInfo(name = "nbSupports") private var nbSupports:Int,
                        @ColumnInfo(name = "inFavorVotes") private var inFavorVotes:Int,
                        @ColumnInfo(name = "againstVotes") private var againstVotes:Int,
                        private var commissionId:Int,
                        private var assemblyId:Int)