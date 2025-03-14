package kg.ram.outlinemedia.ui.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.ram.outlinemedia.domain.Client
import kg.ram.outlinemedia.domain.ConnectState
import kg.ram.outlinemedia.domain.Stream
import kg.ram.outlinemedia.repo.MainRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.Proxy
import javax.inject.Inject

@HiltViewModel
class MainVm @Inject constructor(private val _repo: MainRepo) : ViewModel() {

    private val _state = MutableStateFlow(AppState())
    val state = _state.asStateFlow()

    fun fetchData() = viewModelScope.launch {
        _state.value = AppState()
        try {
            val data = _repo.getSmartProxyMedia()
            val proxy = _repo.updateProxyConfig(data.stream?.url, data.proxy)
            _state.value = AppState(
                isLoading = false,
                connectionStatus = ConnectState.CONNECTED,
                stream = data.stream,
                client = data.client,
                proxy = proxy
            )
        } catch (e: Exception) {
            handleException(e)
        }
    }

    private fun handleException(e: Exception) {
        _state.value = AppState(
            isLoading = false,
            connectionStatus = ConnectState.ERROR,
            error = e.message
        )
    }

    fun stopProxy() = viewModelScope.launch {
        try {
            _repo.stopProxy()
        } catch (e: Exception) {
            handleException(e)
        }
    }
}

data class AppState(
    val error: String? = null,
    val isLoading: Boolean = true,
    val connectionStatus: ConnectState = ConnectState.DISCONNECTED,
    val stream: Stream? = null,
    val client: Client? = null,
    val proxy: Proxy? = null
)