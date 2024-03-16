package com.example.passwordmanagerapp.presentation.enter

import androidx.lifecycle.ViewModel
import com.example.passwordmanagerapp.domain.usecases.enter.CheckFingerPrintUseCase
import com.example.passwordmanagerapp.domain.usecases.enter.CheckIsMasterPasswordNullUseCase
import com.example.passwordmanagerapp.domain.usecases.enter.CheckMasterPasswordUseCase
import com.example.passwordmanagerapp.domain.usecases.enter.ResetMasterPasswordUseCase
import javax.inject.Inject

class EnterViewModel @Inject constructor(
    private val checkMasterPasswordUseCase: CheckMasterPasswordUseCase,
    private val checkFingerPrintUseCase: CheckFingerPrintUseCase,
    private val resetMasterPasswordUseCase: ResetMasterPasswordUseCase,
    private val checkIsMasterPasswordNullUseCase: CheckIsMasterPasswordNullUseCase
): ViewModel() {

    fun checkIsNullMasterPassword(): Boolean {
        return checkIsMasterPasswordNullUseCase()
    }

    fun checkMasterPassword(password: String): Boolean {
        return checkMasterPasswordUseCase(password)
    }

    fun resetMasterPassword(password: String) {
        resetMasterPasswordUseCase(password)
    }
}