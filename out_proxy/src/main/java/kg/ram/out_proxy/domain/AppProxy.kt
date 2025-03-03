package kg.ram.out_proxy.domain

interface AppProxy {
    suspend fun start()
    suspend fun stop()
    suspend fun updateConfig(config: ProxyConfig<*>)
    fun getHost(): String?
    fun getPort(): Int?
    fun getConnectionStatus(): ConnectionStatus
}

