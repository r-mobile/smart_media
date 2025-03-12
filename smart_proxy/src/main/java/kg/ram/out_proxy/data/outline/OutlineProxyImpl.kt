package kg.ram.out_proxy.data.outline

import kg.ram.out_proxy.domain.AppProxy
import kg.ram.out_proxy.domain.ConnectionStatus
import kg.ram.out_proxy.domain.ProxyConfig
import mobileproxy.Mobileproxy
import mobileproxy.Proxy

/**
 * Outline proxy implementation.
 * @param config - outline config.
 */
class OutlineProxyImpl(config: OutlineConfigImpl) : AppProxy {

    private val _localHost = "localhost:0"
    private var _outlineProxy: Proxy? = null
    private var _connectionStatus: ConnectionStatus = ConnectionStatus.DISCONNECTED
    private var _streamDialer = Mobileproxy.newSmartStreamDialer(
        Mobileproxy.newListFromLines(config.getTargetHost()),
        config.getConfig(),
        Mobileproxy.newStderrLogWriter()
    )

    /**
     * Start outline proxy.
     */
    override suspend fun start() = try {
        _outlineProxy = Mobileproxy.runProxy(_localHost, _streamDialer)
        _connectionStatus = ConnectionStatus.CONNECTED
    } catch (e: Exception) {
        handleError(e)
    }

    /**
     * Stop outline proxy.
     */
    override suspend fun stop() = try {
        _connectionStatus = ConnectionStatus.DISCONNECTED
    } catch (e: Exception) {
        handleError(e)
    }

    /**
     * Update outline proxy config.After updating, proxy will be restarted.
     * @param config - new config.
     */
    override suspend fun updateConfig(config: ProxyConfig<*>) {
        if (config !is OutlineConfigImpl) return

        stop()
        _streamDialer = Mobileproxy.newSmartStreamDialer(
            Mobileproxy.newListFromLines(config.getTargetHost()),
            config.getConfig(),
            Mobileproxy.newStderrLogWriter()
        )
        start()
    }

    /**
     * Handle error.
     * @param exception - exception.
     */
    private fun handleError(exception: Exception) {
        exception.printStackTrace()
        _connectionStatus = ConnectionStatus.FAILED
    }

    /**
     * Get proxy host.
     * @return proxy host.
     */
    override fun getHost() = _outlineProxy?.host()

    /**
     * Get proxy port.
     * @return proxy port.
     */
    override fun getPort() = _outlineProxy?.port()?.toInt()

    /**
     * Get proxy connection status.
     * @return connection status.
     */
    override fun getConnectionStatus() = _connectionStatus
}