package com.example.passwordmanagerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import com.example.passwordmanagerapp.presentation.AppApplication
import com.example.passwordmanagerapp.presentation.ViewModelFactory
import com.example.passwordmanagerapp.presentation.detail.DetailViewModel
import com.example.passwordmanagerapp.presentation.BaseScreen
import com.example.passwordmanagerapp.presentation.main.MainViewModel
import com.example.passwordmanagerapp.ui.theme.PasswordManagerAppTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var detailViewModel: DetailViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (application as AppApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component.inject(this)

        setContent {
            PasswordManagerAppTheme {
                mainViewModel = ViewModelProvider(LocalContext.current as ComponentActivity, viewModelFactory)[MainViewModel::class.java]
                detailViewModel = ViewModelProvider(LocalContext.current as ComponentActivity, viewModelFactory)[DetailViewModel::class.java]
                BaseScreen()
            }
        }
    }


//    private suspend fun loadPhoto(filename: String): Bitmap {
//        return withContext(Dispatchers.IO) {
//            val file = File(filesDir, "$filename.txt")
//            if(file.canRead() && file.isFile) {
//                val bytes = file.readBytes()
//                BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
//            } else throw RuntimeException("file fail")
//        }
//    }

}
