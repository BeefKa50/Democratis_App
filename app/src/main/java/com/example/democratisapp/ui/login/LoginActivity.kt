package com.example.democratisapp.ui.login

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import com.example.democratisapp.MainActivity
import com.example.democratisapp.databinding.ActivityLoginBinding

import com.example.democratisapp.R
import com.example.democratisapp.database.DemocratisDB
import com.example.democratisapp.ui.register.ui.login.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    companion object{
        private var loginSuccessful:Boolean = false
    }

    class ThreadCheckLogin(var username:String,var password:String,
                        var context: Context
    ): Thread() {
        public override fun run() {
            var db: DemocratisDB = DemocratisDB.getDatabase(this.context)
            System.out.println("Username : [" + username + "] ; password :[" + password + "]")
            loginSuccessful = db.accountDao().login(username,password)
            System.out.println("IDDDD ----->>>> " + id)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = binding.username
        val password = binding.password
        val login = binding.login
        val loading = binding.loading

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed()
            }
            if (loginResult.success != null) {
                var th:ThreadCheckLogin = ThreadCheckLogin(username.text.toString(),password.text.toString(),
                this)
                th.start()

                th.join()

                if(loginSuccessful) {
                    val intent = Intent(this, MainActivity::class.java);
                    startActivity(intent);
                    updateUiWithUser(loginResult.success)
                    finish()
                }
                else{
                    showLoginFailed()
                }
            }
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString(),
                            this.context
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                loginViewModel.login(username.text.toString(), password.text.toString(),
                    this.context)
            }

            binding.textView2?.setOnClickListener{
                val intent = Intent(this.context,RegisterActivity::class.java);
                startActivity(intent);
            }
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        Toast.makeText(
            applicationContext,
            "$welcome",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showLoginFailed() {
        Toast.makeText(applicationContext, "Echec de la connexion.", Toast.LENGTH_SHORT).show()
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}