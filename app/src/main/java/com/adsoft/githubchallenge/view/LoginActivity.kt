package com.adsoft.githubchallenge.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import androidx.databinding.DataBindingUtil
import com.adsoft.githubchallenge.R
import com.adsoft.githubchallenge.databinding.ActivityLoginBinding
import com.adsoft.githubchallenge.utils.OnTextChangedListener
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), OnTextChangedListener {
    companion object {
        const val LOGIN_INPUT_EXTRA_INTENT = "login_input_extra_input"
    }
    private lateinit var loginBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        githubLoginText.addTextChangedListener(this)
        checkLoginEditText()
        loginOnClick()
    }

    override fun onStop() {
        super.onStop()
        finish()
    }

    private fun checkLoginEditText() {
        if (shouldLoginButtonBeEnabled()) {
            loginBinding.loginButton.isEnabled = true
        }
    }

    private fun loginOnClick() {
        loginButton.setOnClickListener {
            Intent(this@LoginActivity, MainActivity::class.java).apply {
                putExtra(LOGIN_INPUT_EXTRA_INTENT, githubLoginText.text.toString())
                startActivity(this)
            }
        }
    }

    private fun shouldLoginButtonBeEnabled() =
        loginBinding.githubLoginText.text.isNotEmpty()

    override fun onTextChangedListener(s: Editable?) {
        checkLoginEditText()
    }
}
