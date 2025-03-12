package kg.ram.outlinemedia.network

import kg.ram.outlinemedia.domain.SmartProxyMediaResponse
import kg.ram.outlinemedia.utils.Const
import retrofit2.http.GET

interface SmartProxyApi {
    @GET("v1/config/?stream=${Const.DEMO_STREAM_UUID}")
    suspend fun getSmartProxyMedia(): SmartProxyMediaResponse
}