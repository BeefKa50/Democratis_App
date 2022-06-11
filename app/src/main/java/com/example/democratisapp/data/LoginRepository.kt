package com.example.democratisapp.data

import android.content.Context
import com.example.democratis.classes.Account
import com.example.democratisapp.data.model.LoggedInUser
import com.example.democratisapp.database.DemocratisDB

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {

    class ThreadLoginDB(var username:String,var password:String,
                        var context:Context): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            db.accountDao().createAccount(Account(username=this.username,mail="test@mail.com",
                password = this.password))
            var acc: Account = db.accountDao().getUserById(1)
            System.out.println("Account created !")
            System.out.println("Username : " + acc.username)
            System.out.println("Password : " + acc.password)
            System.out.println("Username : " + acc.mail)
        }
    }

    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    fun login(username: String, password: String, context: Context): Result<LoggedInUser> {
        // handle login
        val result = dataSource.login(username, password)

        if (result is Result.Success) {
            ThreadLoginDB(username,password,context).start()
            setLoggedInUser(result.data)
        }

        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}