package com.example.democratis.classes

class Account (private var username:String, private var password:String, private var mail:String, private var role:RoleEnum) {
    private var propositions:ArrayList<Proposition> = ArrayList<Proposition>();
}