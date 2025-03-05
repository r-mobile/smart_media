package kg.ram.outlinemedia.domain

import com.google.gson.annotations.SerializedName

data class SmartProxyMediaResponse(
    val client: Client? = null,
    @SerializedName("config")
    val proxy: AppProxyConfig? = null,
) {

    val stream: Stream?
        get() = proxy?.stream
}

