package kg.ram.out_proxy.utils

import android.webkit.WebView
import androidx.webkit.ProxyConfig
import androidx.webkit.ProxyController

fun WebView.startProxy(proxyHost: String?, proxyPort: Int?, onProxyConnected: (WebView) -> Unit) {
    val config = ProxyConfig.Builder()
        .addProxyRule("$proxyHost:$proxyPort")
        .addDirect()
        .build()

    ProxyController.getInstance()
        .setProxyOverride(
            config,
            { onProxyConnected(this) },
            { onProxyConnected(this) }
        )
}

fun WebView.stopProxy(onProxyDisconnected: (WebView) -> Unit) {
    ProxyController.getInstance().clearProxyOverride(
        { onProxyDisconnected(this) },
        { onProxyDisconnected(this) }
    )
}