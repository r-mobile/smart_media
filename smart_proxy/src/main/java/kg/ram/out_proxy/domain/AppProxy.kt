package kg.ram.out_proxy.domain

/**
 * App proxy interface.
 * The interface can be used to write custom implementations of a proxy.
 */
interface AppProxy {
    suspend fun start()
    suspend fun stop()
    suspend fun updateConfig(config: ProxyConfig<*>)
    fun getHost(): String?
    fun getPort(): Int?
    fun getConnectionStatus(): ConnectionStatus
}

