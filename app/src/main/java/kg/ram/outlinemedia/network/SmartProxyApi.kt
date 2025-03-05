package kg.ram.outlinemedia.network

import kg.ram.outlinemedia.domain.SmartProxyMediaResponse
import retrofit2.http.GET

interface SmartProxyApi {
    @GET("connection_info")
    suspend fun getSmartProxyMedia(): SmartProxyMediaResponse
}