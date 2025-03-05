package kg.ram.outlinemedia.domain

import com.google.gson.annotations.SerializedName
import java.util.Date

data class AppProxyConfig(
    val outline: OutlineConfig?,
    @SerializedName("updated_at") val updatedAt: Date?,
    val stream: Stream? = null,
)