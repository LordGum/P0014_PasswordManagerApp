package com.example.passwordmanagerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.passwordmanagerapp.presentation.enter.BaseScreen
import com.example.passwordmanagerapp.ui.theme.PasswordManagerAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PasswordManagerAppTheme {

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
