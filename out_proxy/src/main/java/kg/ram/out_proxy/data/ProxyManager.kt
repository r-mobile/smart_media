package kg.ram.out_proxy.data

import kg.ram.out_proxy.domain.AppProxy
import kg.ram.out_proxy.domain.ProxyConfig
import java.net.InetSocketAddress
import java.net.Proxy

class ProxyManager(private val _proxy: AppProxy) {

    suspend fun start() {
        _proxy.start()
    }

    suspend fun stop() {
        _proxy.stop()
    }

    suspend fun updateConfig(config: ProxyConfig<*>) {
        _proxy.updateConfig(config)
    }

    fun getHost() = _proxy.getHost()

    fun getPort() = _proxy.getPort()

    fun getConnectionStatus() = _proxy.getConnectionStatus()

    fun getProxy(): Proxy {
        if (getHost().isNullOrEmpty() || getPort() == null) {
            throw IllegalStateException("Proxy is not initialized")
        }
        return Proxy(Proxy.Type.HTTP, InetSocketAddress(getHost(), getPort()!!))
    }
}