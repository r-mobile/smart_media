package kg.ram.outlinemedia.domain

import kg.ram.out_proxy.domain.ConnectionStatus

enum class ConnectState {
    CONNECTED, DISCONNECTED, ERROR
}

fun ConnectionStatus.toState() : ConnectState {
    return when(this) {
        ConnectionStatus.CONNECTED -> ConnectState.CONNECTED
        ConnectionStatus.DISCONNECTED -> ConnectState.DISCONNECTED
        ConnectionStatus.FAILED -> ConnectState.ERROR
    }
}