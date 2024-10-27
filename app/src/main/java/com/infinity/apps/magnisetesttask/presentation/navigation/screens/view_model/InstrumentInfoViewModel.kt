package com.infinity.apps.magnisetesttask.presentation.navigation.screens.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.infinity.apps.magnisetesttask.domain.model.core.Response
import com.infinity.apps.magnisetesttask.domain.remote.repository.IAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstrumentInfoViewModel @Inject constructor(
    private val authRepository : IAuthRepository
) : ViewModel () {

    private suspend fun login() {
        val result = authRepository.getAuthResponse(
            username = "r_test@fintatech.com",
            password = "kisfiz-vUnvy9-sopnyv"
        )
        when (result) {
            is Response.Success -> {
                val authData = result.data
            }

            is Response.Error -> {

            }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            login()
        }
    }

}