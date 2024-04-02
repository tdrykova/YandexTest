package com.tatry.yandextest.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.tatry.yandextest.App
import com.tatry.yandextest.data.UserRepositoryImpl
import com.tatry.yandextest.domain.model.devices.action.DeviceListModel
import com.tatry.yandextest.domain.model.devices.answer.DeviceActionsAnswerModel
import com.tatry.yandextest.domain.model.devices.get_device_state.GetDeviceStateResponse
import com.tatry.yandextest.domain.model.devices.user_info.DeviceModel
import com.tatry.yandextest.domain.model.devices.user_info.UserInfoModel
import com.tatry.yandextest.domain.usecase.CashDeviceListUseCase
import com.tatry.yandextest.domain.usecase.GetDeviceListUseCase
import com.tatry.yandextest.domain.usecase.GetDeviceStateUseCase
import com.tatry.yandextest.domain.usecase.UploadUserInfoUseCase
import com.tatry.yandextest.domain.usecase.PostDevicesActionsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "MainFragment555"

class YandexViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(YandexViewModel::class.java)) {
            val repo = UserRepositoryImpl(App.INSTANCE)
            val useCase1 = UploadUserInfoUseCase(repo)
            val useCase4 = CashDeviceListUseCase(repo)
            val useCase5 = GetDeviceListUseCase(repo)
            val useCase2 = GetDeviceStateUseCase(repo)
            val useCase3 = PostDevicesActionsUseCase(repo)

            return YandexViewModel(useCase1, useCase4, useCase5, useCase2, useCase3) as T
        }
        throw RuntimeException("Unknown class name")
    }
}

class YandexViewModel(
    private val uploadUserInfoUseCase: UploadUserInfoUseCase,
    private val cashDeviceListUseCase: CashDeviceListUseCase,
    private val getDeviceListUseCase: GetDeviceListUseCase,
    private val getDeviceStateUseCase: GetDeviceStateUseCase,
    private val postDevicesActionsUseCase: PostDevicesActionsUseCase
) : ViewModel() {

    private val token = "Bearer y0_AgAEA7qkJBRwAAtNHQAAAAD7NOpOAABZXzInfHtFAoIVc4SUjPlw0bda8g"
    private var _userInfo = MutableStateFlow(UserInfoModel())
    var userInfo = _userInfo.asStateFlow()

    private var _deviceList = MutableStateFlow<MutableList<DeviceModel>>(mutableListOf())
    var deviceList = _deviceList.asStateFlow()

    private var _userToken = MutableStateFlow("token")
    var userToken = _userToken.asStateFlow()

    private var _devState = MutableStateFlow(GetDeviceStateResponse())
    var devState = _devState.asStateFlow()

    private var _devAction = MutableStateFlow(DeviceActionsAnswerModel())
    var devAction = _devAction.asStateFlow()

//    private var _characterList = MutableStateFlow<List<CharacterModel>>(mutableListOf())
//    var characterList = _characterList.asStateFlow()

    private var _state = MutableStateFlow<ProgressState>(ProgressState.Success)
    var state = _state.asStateFlow()

    init {
        getDeviceList()
    }

    init {
//        viewModelScope.launch {
//            _state.value = ProgressState.Loading
//            try {
//                _userInfo.value = getUserInfoUseCase.getUserInfo(token)
//                Log.d(TAG, "user info: ${_userInfo.value}")
//            } catch (t: Throwable) {
//                Log.e(TAG, "${t.message}", t)
//            }
//            _state.value = ProgressState.Success
//        }
    }

    fun getUserInfo() {

    }

    fun setToken(token: String) {
        viewModelScope.launch {
            _userToken.value = token
        }

    }



    fun getDeviceState(token: String, devId: String) {
        viewModelScope.launch {
            _devState.value = getDeviceStateUseCase.getDeviceStateUseCase(token, devId)
            Log.d(
                YandexFragment.TAG, " getDeviceState ${_devState.value.toString()}"
            )
        }
    }

    fun postAction(token: String, deviceList: DeviceListModel) {
        viewModelScope.launch {
            _devAction.value = postDevicesActionsUseCase.postDevicesActions(
                token = token,
                deviceList = deviceList)
        }
    }

    private fun getDeviceList() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                _state.value = ProgressState.Loading
                cashDeviceListUseCase(uploadUserInfoUseCase(token).deviceList)
                getDeviceListUseCase().toMutableList()
            }.fold(
                onSuccess = {_deviceList.value = it},
                onFailure = {
                    Log.e(TAG, "${it.message}", it)
                }
            )
            _state.value = ProgressState.Success
        }
    }
}