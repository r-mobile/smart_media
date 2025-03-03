package kg.ram.out_proxy.data.outline

import android.net.Uri
import com.google.gson.Gson
import kg.ram.out_proxy.domain.ProxyConfig

data class OutlineConfigImpl(
    val targetUrl: String,
    val dns: List<*>,
    val tls: List<*>
) : ProxyConfig<String>() {

    fun getTargetHost() = Uri.parse(targetUrl).host

    override fun getConfig() = Gson().toJson(this)

    companion object {
        fun default(defConfigJson: String): OutlineConfigImpl {
            return Gson()
                .fromJson(defConfigJson, OutlineConfigImpl::class.java)
                .copy(targetUrl = "http://dwamdstream102.akamaized.net/")
        }
    }
}