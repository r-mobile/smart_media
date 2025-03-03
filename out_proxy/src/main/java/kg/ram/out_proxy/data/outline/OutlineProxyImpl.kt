package kg.ram.out_proxy.data.outline

import kg.ram.out_proxy.domain.AppProxy
import kg.ram.out_proxy.domain.ConnectionStatus
import kg.ram.out_proxy.domain.ProxyConfig
import kotlinx.coroutines.delay
import mobileproxy.Mobileproxy
import mobileproxy.Proxy


class OutlineProxyImpl(config: OutlineConfigImpl) : AppProxy {

    private val _localHost = "localhost:0"
    private var _outlineProxy: Proxy? = null
    private var _connectionStatus: ConnectionStatus = ConnectionStatus.DISCONNECTED
    private var _streamDialer = Mobileproxy.newSmartStreamDialer(
        Mobileproxy.newListFromLines(config.getTargetHost()),
        config.getConfig(),
        Mobileproxy.newStderrLogWriter()
    )

    override suspend fun start() = try {
        _outlineProxy = Mobileproxy.runProxy(_localHost, _streamDialer)
        _connectionStatus = ConnectionStatus.CONNECTED
    } catch (e: Exception) {
        handleError(e)
    }

    override suspend fun stop() = try {
        _connectionStatus = ConnectionStatus.DISCONNECTED
    } catch (e: Exception) {
        handleError(e)
    }

    override suspend fun updateConfig(config: ProxyConfig<*>) {
        if (config !is OutlineConfigImpl) return

        _streamDialer = Mobileproxy.newSmartStreamDialer(
            Mobileproxy.newListFromLines(config.getTargetHost()),
            config.getConfig(),
            Mobileproxy.newStderrLogWriter()
        )


        _outlineProxy.let {
            stop()
            start()
        }

    }

    private fun handleError(e: Exception) {
        e.printStackTrace()
        _connectionStatus = ConnectionStatus.FAILED
    }

    override fun getHost() = _outlineProxy?.host()

    override fun getPort() = _outlineProxy?.port()?.toInt()

    override fun getConnectionStatus() = _connectionStatus
}