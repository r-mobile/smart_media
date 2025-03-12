package kg.ram.out_proxy.data.outline

import android.net.Uri
import com.google.gson.Gson
import kg.ram.out_proxy.domain.ProxyConfig
import kg.ram.out_proxy.utils.Utils

/**
 * Outline config implementation.
 * @param targetUrl - stream url.
 * @param dns - dns list.
 * @param tls - tls list.
 */
data class OutlineConfigImpl(
    val targetUrl: String,
    val dns: List<*>?,
    val tls: List<*>?
) : ProxyConfig<String>() {

    /**
     * Get target host from stream url.
     * @return host
     */
    fun getTargetHost() = Uri.parse(targetUrl).host

    /**
     * Get config as json string.
     * @return json string.
     */
    override fun getConfig() = Gson().toJson(this)

    companion object {

        /**
         * Create default outline config.
         * @param defaultUrl - default stream url.
         * @return default config.
         */
        fun default(defaultUrl: String): OutlineConfigImpl {
            return Gson()
                .fromJson(Utils.OUTLINE_DEFAULT_CONFIG, OutlineConfigImpl::class.java)
                .copy(targetUrl = defaultUrl)
        }
    }
}