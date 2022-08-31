package com.example.retrofitcompose3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.retrofitcompose3.model.CryptoModel
import com.example.retrofitcompose3.service.CryptoAPI
import com.example.retrofitcompose3.ui.theme.RetrofitCompose3Theme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RetrofitCompose3Theme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {

    val cryptoModelList = remember { mutableStateListOf<CryptoModel>() }

    val retrofit = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CryptoAPI::class.java)

    val call = retrofit.getData()

    call.enqueue(object : Callback<List<CryptoModel>> {
        override fun onResponse(
            call: Call<List<CryptoModel>>,
            response: Response<List<CryptoModel>>
        ) {
            if (response.isSuccessful) {
                response.body()?.let {
                    cryptoModelList.addAll(it)
                }
            }
        }

        override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
            t.printStackTrace()
        }
    })

    Scaffold(
        modifier = Modifier.background(MaterialTheme.colors.background),
        topBar = { AppBar() }) {
        CryptoList(cryptoList = cryptoModelList)
        println(it)
    }
}

@Composable
fun CryptoList(cryptoList: List<CryptoModel>) {
    LazyColumn {
        items(cryptoList) { crypto ->
            CryptoRow(crypto = crypto)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CryptoRow(crypto: CryptoModel = CryptoModel("BTC", "999")) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = crypto.currency,
            style = MaterialTheme.typography.h4,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = crypto.price,
            style = MaterialTheme.typography.h5
        )
    }
}

@Composable
fun AppBar() {
    TopAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Retrofit Compose", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RetrofitCompose3Theme {
        MainScreen()
    }
}