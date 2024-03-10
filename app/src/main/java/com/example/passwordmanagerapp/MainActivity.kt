package com.example.passwordmanagerapp

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.example.passwordmanagerapp.data.repositories.RepositoryMainImpl
import com.example.passwordmanagerapp.security.CryptoManager
import com.example.passwordmanagerapp.ui.theme.PasswordManagerAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasswordManagerAppTheme {
                val cryptoManager = CryptoManager()
                val repositoryMain = RepositoryMainImpl(
                    LocalContext.current.applicationContext as Application,
                    cryptoManager
                )

                val password = "Hello"
                var ans: Boolean
                val scope = rememberCoroutineScope()

                scope.launch {
                    repositoryMain.resetMasterPassword(password)
                }

                val otherPass = "GoodBay"
                scope.launch {
                    ans = repositoryMain.checkMasterPassword(otherPass)
                    Log.d("MATAG", ans.toString())
                }

                scope.launch {
                    ans = repositoryMain.checkMasterPassword(password)
                    Log.d("MATAG", ans.toString())
                }

            }
        }
    }
}
