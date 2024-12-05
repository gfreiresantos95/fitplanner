package com.gabrielfreire.fitplanner.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.gabrielfreire.fitplanner.R
import com.gabrielfreire.fitplanner.databinding.ActivityForgotPasswordBinding
import com.gabrielfreire.fitplanner.isEmailValid
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var forgotPasswordBinding: ActivityForgotPasswordBinding
    private var userEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forgotPasswordBinding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(forgotPasswordBinding.root)

        initViewsAndSetListeners()
    }

    private fun initViewsAndSetListeners() {
        with(forgotPasswordBinding) {
            fabForgotPasswordBack.setOnClickListener {
                finish()
            }

            btnForgotPasswordRecover.setOnClickListener {
                checkEmail()
            }
        }
    }

    private fun checkEmail() {
        with(forgotPasswordBinding) {
            tilForgotPasswordEmail.isEnabled = false
            tilForgotPasswordEmail.error = null
            btnForgotPasswordRecover.isEnabled = false

            userEmail = tietForgotPasswordEmail.text.toString()

            when {
                userEmail.isNotEmpty() && userEmail.isEmailValid() -> {
                    sendPasswordRecoveryEmail()
                }

                else -> {
                    tilForgotPasswordEmail.isEnabled = true
                    tilForgotPasswordEmail.error = getString(R.string.invalid_email)
                    btnForgotPasswordRecover.isEnabled = true
                }
            }
        }
    }

    private fun sendPasswordRecoveryEmail() {
        showSentEmailDialog()
    }

    private fun showSentEmailDialog() {
        MaterialAlertDialogBuilder(this@ForgotPasswordActivity)
            .setTitle(getString(R.string.email_sent))
            .setMessage(getString(R.string.email_message_sent, userEmail))
            .setPositiveButton(getString(R.string.positive_button_option)) { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .setCancelable(false)
            .show()
    }
}