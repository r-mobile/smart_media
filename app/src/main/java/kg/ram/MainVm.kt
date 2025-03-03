package kg.ram

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.ram.out_proxy.data.ProxyManager
import kg.ram.outlinemedia.domain.Client
import kg.ram.outlinemedia.domain.ConnectState
import kg.ram.outlinemedia.domain.Stream
import kg.ram.outlinemedia.domain.toState
import kotlinx.coroutines.launch
import java.net.Proxy
import javax.inject.Inject

@HiltViewModel
class MainVm @Inject constructor(
    private val _proxyManager: ProxyManager
) : ViewModel() {

    val state = mutableStateOf(AppState())

    fun runProxy() = viewModelScope.launch {
        _proxyManager.start()
        state.value = state.value.copy(
            isLoading = false,
            proxy = _proxyManager.getProxy(),
            connectionStatus = _proxyManager.getConnectionStatus().toState()
        )
    }
}

data class AppState(
    val isLoading: Boolean = true,
    val connectionStatus: ConnectState = ConnectState.DISCONNECTED,
    val stream: Stream? = null,
    val client: Client? = null,
    val proxy: Proxy? = null
)
