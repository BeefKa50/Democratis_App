package com.example.democratis.classes

import java.util.*

class Proposition (private val id: Int, private var title:String, private var submitDate:Date, status:StatusEnum){
    private var paragraphs:ArrayList<Paragraph> = ArrayList<Paragraph>();
}