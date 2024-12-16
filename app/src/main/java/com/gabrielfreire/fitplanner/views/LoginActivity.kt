package com.gabrielfreire.fitplanner.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.gabrielfreire.fitplanner.Constants
import com.gabrielfreire.fitplanner.R
import com.gabrielfreire.fitplanner.models.UserType
import com.gabrielfreire.fitplanner.databinding.ActivityLoginBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding

    private val userType: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        setObservables()
        getUserType()
        initViewsAndSetListeners()
    }

    private fun setObservables() {
        userType.observe(this@LoginActivity) {
            setUserTypeTextAndSignUpAction()
        }
    }

    private fun getUserType() {
        userType.value = if (intent.hasExtra(Constants.USER_TYPE)) {
            intent.getStringExtra(Constants.USER_TYPE).toString()
        } else {
            ""
        }
    }

    private fun initViewsAndSetListeners() {
        with(loginBinding) {
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
        }
    }

    private fun setUserTypeTextAndSignUpAction() {
        when (userType.value) {
            UserType.STUDENT.type -> {
                loginBinding.tvLoginUserType.text = getString(R.string.student)
                loginBinding.btnLoginSignUp.setOnClickListener {
                    showStudentSignUpDialog()
                }
            }

            UserType.PERSONAL_TRAINER.type -> {
                loginBinding.tvLoginUserType.text = getString(R.string.personal_trainer)
                loginBinding.btnLoginSignUp.setOnClickListener {
                    goToSignUp()
                }
            }

            else -> {
                loginBinding.tvLoginUserType.text = getString(R.string.user)
                loginBinding.btnLoginSignUp.setOnClickListener {
                    showGenericErrorDialog()
                }
            }
        }
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

    private fun goToSignUp() {
        val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
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