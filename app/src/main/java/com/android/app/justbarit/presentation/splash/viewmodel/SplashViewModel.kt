package com.android.app.justbarit.presentation.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.app.justbarit.data.remote.entity.Bar
import com.android.app.justbarit.data.remote.entity.Meta
import com.android.app.justbarit.domain.usecases.CheckBarExistsInLocalDatabaseUseCase
import com.android.app.justbarit.domain.usecases.DeleteBarsFromLocalDatabaseUseCase
import com.android.app.justbarit.domain.usecases.GetBarsFromLocalDatabaseUseCase
import com.android.app.justbarit.domain.usecases.SetBarsInLocalDatabaseUseCase
import com.android.app.justbarit.presentation.AppState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getBarsFromLocalDatabaseUseCase: GetBarsFromLocalDatabaseUseCase,
    private val setBarsInLocalDatabaseUseCase: SetBarsInLocalDatabaseUseCase,
    private val checkBarExistsInLocalDatabaseUseCase: CheckBarExistsInLocalDatabaseUseCase,
    private val deleteBarsFromLocalDatabaseUseCase: DeleteBarsFromLocalDatabaseUseCase,
    private val firebaseDatabase: FirebaseDatabase
) : ViewModel() {
    private val _barsResponse = MutableStateFlow<AppState>(AppState.Default)
    val barsResponse: Flow<AppState> get() = _barsResponse.asStateFlow()

    private val _meta = MutableStateFlow<AppState>(AppState.Default)
    val meta: Flow<AppState> get() = _meta.asStateFlow()


    fun flushDatabaseAndStall() {
        viewModelScope.launch(Dispatchers.IO) {
            flushBarsFromDatabase()
        }
    }

    fun shouldFetchFromRemote() {
        val metaRef: DatabaseReference = firebaseDatabase.getReference("meta")
        metaRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dataMap = dataSnapshot.value as HashMap<String, Boolean>
                val meta = Meta(flushDB = dataMap["flushDB"])
                viewModelScope.launch(Dispatchers.IO) {
                    _meta.emit(AppState.Success(meta))
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
            }
        })
    }

    fun fetchBarsFromRemote() {
        viewModelScope.launch(Dispatchers.IO) {
            if (checkIfBarsExistsInDatabase()) {
                loadBarsFromDatabase()
            } else {
                val barsRef: DatabaseReference = firebaseDatabase.getReference("bars")
                barsRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val listOfBars = arrayListOf<Bar>()
                        for (snapshot in dataSnapshot.children) {
                            val map = snapshot.value as HashMap<String, String>
                            listOfBars.add(Bar(map))
                        }
                        viewModelScope.launch(Dispatchers.IO) {
                            setBarsInLocalDatabaseUseCase(listOfBars)
                            _barsResponse.emit(AppState.Success(listOfBars))
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle error
                    }
                })
            }
        }

    }

    private fun loadBarsFromDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val barsList = getBarsFromLocalDatabaseUseCase
                _barsResponse.emit(AppState.Success(barsList))
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    private suspend fun flushBarsFromDatabase() {
        deleteBarsFromLocalDatabaseUseCase()
    }

    private suspend fun checkIfBarsExistsInDatabase(): Boolean {
        return checkBarExistsInLocalDatabaseUseCase()
    }

}