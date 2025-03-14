package kg.ram.outlinemedia.repo

import kg.ram.out_proxy.data.ProxyManager
import kg.ram.out_proxy.data.outline.OutlineConfigImpl
import kg.ram.outlinemedia.domain.AppProxyConfig
import kg.ram.outlinemedia.network.FallbackConfigApi
import kg.ram.outlinemedia.network.SmartProxyApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.Proxy
import javax.inject.Inject

class MainRepo @Inject constructor(
    private val _api: SmartProxyApi,
    private val _fallbackApi: FallbackConfigApi,
    private val _proxyManager: ProxyManager
) {

    suspend fun getSmartProxyMedia() = withContext(Dispatchers.IO) {
        try {
            _api.getSmartProxyMedia()
        } catch (e: Exception) {
            _fallbackApi.getSmartProxyMedia()
        }
    }

    suspend fun updateProxyConfig(url: String?, proxy: AppProxyConfig?): Proxy {
        proxy?.outline?.let {
            val outlineConfig = OutlineConfigImpl(url ?: "", it.dns, it.tls)
            _proxyManager.updateConfig(outlineConfig)
        }
        return _proxyManager.getProxy()
    }

    suspend fun stopProxy() {
        _proxyManager.stop()
    }

}