package com.gabrielfreire.fitplanner.views

import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.gabrielfreire.fitplanner.R
import com.gabrielfreire.fitplanner.databinding.ActivityForgotPasswordBinding
import com.gabrielfreire.fitplanner.isEmailValid
import com.gabrielfreire.fitplanner.models.UiStates
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var forgotPasswordBinding: ActivityForgotPasswordBinding

    private val forgotPasswordUiState: MutableLiveData<UiStates> by lazy {
        MutableLiveData<UiStates>()
    }

    private var userEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forgotPasswordBinding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(forgotPasswordBinding.root)

        setObservables()
        initViewsAndSetListeners()
    }

    private fun setObservables() {
        forgotPasswordUiState.observe(this@ForgotPasswordActivity) {
            updateForgotPasswordUi()
        }
    }

    private fun initViewsAndSetListeners() {
        forgotPasswordUiState.value = UiStates.LOADED

        with(forgotPasswordBinding) {
            fabForgotPasswordBack.setOnClickListener {
                finish()
            }

            btnForgotPasswordRecover.setOnClickListener {
                checkEmail()
            }
        }
    }

    private fun updateForgotPasswordUi() {
        when (forgotPasswordUiState.value?.state) {
            UiStates.LOADED.state -> {
                with(forgotPasswordBinding) {
                    tilForgotPasswordEmail.isEnabled = true
                    tilForgotPasswordEmail.error = null
                    btnForgotPasswordRecover.isEnabled = true
                }
            }

            UiStates.LOADING.state -> {
                with(forgotPasswordBinding) {
                    tilForgotPasswordEmail.isEnabled = false
                    tilForgotPasswordEmail.error = null
                    btnForgotPasswordRecover.isEnabled = false
                }
            }

            UiStates.INVALID.state -> {
                with(forgotPasswordBinding) {
                    tilForgotPasswordEmail.isEnabled = true
                    tilForgotPasswordEmail.error = getString(R.string.invalid_email)
                    btnForgotPasswordRecover.isEnabled = true
                }
            }

            else -> {
                showGenericErrorDialog()
            }
        }
    }

    private fun checkEmail() {
        forgotPasswordUiState.value = UiStates.LOADING

        with(forgotPasswordBinding) {
            userEmail = tietForgotPasswordEmail.text.toString()

            if (userEmail.isNotEmpty() && userEmail.isEmailValid()) {
                showSentEmailDialog()
            } else {
                forgotPasswordUiState.value = UiStates.INVALID
            }
        }
    }

    private fun showSentEmailDialog() {
        showOneOptionDialog(
            getString(R.string.email_sent),
            getString(R.string.email_message_sent, userEmail),
            getString(R.string.positive_button_option)
        ) { dialog, _ ->
            dialog.dismiss()
            finish()
        }
    }

    private fun showGenericErrorDialog() {
        showOneOptionDialog(
            getString(R.string.generic_error_title),
            getString(R.string.generic_error_message),
            getString(R.string.positive_button_option)
        ) { dialog, _ ->
            dialog.dismiss()
        }
    }

    private fun showOneOptionDialog(
        title: String,
        message: String,
        buttonText: String,
        buttonAction: DialogInterface.OnClickListener
    ) {
        MaterialAlertDialogBuilder(this@ForgotPasswordActivity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(buttonText, buttonAction)
            .setCancelable(false)
            .show()
    }
}