package com.example.passwordmanagerapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.passwordmanagerapp.data.repositories.RepositoryAccountImpl
import com.example.passwordmanagerapp.data.repositories.RepositoryWebsiteImpl
import com.example.passwordmanagerapp.domain.entities.Website
import com.example.passwordmanagerapp.domain.entities.WebsiteAccount
import com.example.passwordmanagerapp.ui.theme.PasswordManagerAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        val repositoryWebsite = RepositoryWebsiteImpl(this@MainActivity)
        val repositoryAccount = RepositoryAccountImpl()

        super.onCreate(savedInstanceState)
        setContent {
            val scope = CoroutineScope(Dispatchers.IO)
            val websiteAccount = WebsiteAccount("secret login", "secret password", "text")
            val website = Website(
                address = "https://favicon.yandex.net/favicon/yandex.com",
                name = "my website",
                accountList = arrayListOf()
            )
            repositoryAccount.addAccount(website, websiteAccount)

            scope.launch {
                //repositoryWebsite.addWebsite(website)

                val list = repositoryWebsite.websitesList
                list.collect{
                    Log.d("MATAG", it[0].accountList[0].cipherLogin)
                }
            }

            PasswordManagerAppTheme {





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
