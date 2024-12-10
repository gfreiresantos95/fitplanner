package com.gabrielfreire.fitplanner.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gabrielfreire.fitplanner.Constants
import com.gabrielfreire.fitplanner.R
import com.gabrielfreire.fitplanner.models.UserType
import com.gabrielfreire.fitplanner.databinding.ActivityLoginBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding

    private var userType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        getUserType()
        initViewsAndSetListeners()
    }

    private fun getUserType() {
        userType = if (intent.hasExtra(Constants.USER_TYPE)) {
            intent.getStringExtra(Constants.USER_TYPE).toString()
        } else {
            ""
        }
    }

    private fun initViewsAndSetListeners() {
        with(loginBinding) {
            tvLoginUserType.text = getUserTypeText()
            tvLoginAppVersion.text = getAppVersion()

            fabLoginBack.setOnClickListener {
                finish()
            }

            btnLoginForgotPassword.setOnClickListener {
                goToForgotPassword()
            }

            btnLoginSignIn.setOnClickListener {
                goToHome()
            }

            btnLoginSignUp.setOnClickListener {
                when (userType) {
                    UserType.STUDENT.type -> {
                        showStudentSignUpDialog()
                    }

                    UserType.PERSONAL_TRAINER.type -> {

                    }

                    else -> {
                        showGenericErrorDialog()
                    }
                }
            }
        }
    }

    private fun getUserTypeText(): String {
        val type = when (userType) {
            UserType.STUDENT.type -> {
                getString(R.string.student)
            }

            UserType.PERSONAL_TRAINER.type -> {
                getString(R.string.personal_trainer)
            }

            else -> {
                getString(R.string.user)
            }
        }

        return type
    }

    private fun getAppVersion(): String {
        var appVersion: String

        try {
            val packageInfo = packageManager.getPackageInfo(packageName, 0)

            appVersion = packageInfo.versionName ?: getString(R.string.app_version_example)
        } catch (ex: Exception) {
            appVersion = getString(R.string.app_version_example)
        }

        return appVersion
    }

    private fun goToForgotPassword() {
        val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
        startActivity(intent)
    }

    private fun goToHome() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun showStudentSignUpDialog() {
        showOneOptionDialog(
            getString(R.string.greeting, getString(R.string.student)),
            getString(R.string.cannot_create_account_message),
            getString(R.string.positive_button_option)
        )
    }

    private fun showGenericErrorDialog() {
        showOneOptionDialog(
            getString(R.string.greeting, getString(R.string.user)),
            getString(R.string.generic_error_message),
            getString(R.string.positive_button_option)
        )
    }

    private fun showOneOptionDialog(title: String, message: String, buttonText: String) {
        MaterialAlertDialogBuilder(this@LoginActivity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(buttonText) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }
}