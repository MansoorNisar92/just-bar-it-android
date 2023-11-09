package com.android.app.justbarit.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NetworkManager(private val context: Context) {
    private val _isConnected = MutableLiveData<Boolean>()
    val isConnected: LiveData<Boolean> get() = _isConnected

    init {
        checkNetworkStatus()
    }

    private fun checkNetworkStatus() {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (connectivityManager.activeNetwork == null){
                _isConnected.postValue(false)
            }else{
                val networkCapabilities = connectivityManager.activeNetwork ?: return
                val actNw =
                    connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return
                _isConnected.postValue(
                    actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                )
            }

        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo
            _isConnected.postValue(networkInfo != null && networkInfo.isConnected)
        }
    }
}