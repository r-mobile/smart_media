package kg.ram.outlinemedia.network

import kg.ram.outlinemedia.domain.SmartProxyMediaResponse
import kg.ram.outlinemedia.utils.Const
import retrofit2.http.GET

interface FallbackConfigApi {
    @GET("swob/${Const.DEMO_STREAM_UUID}.json")
    suspend fun getSmartProxyMedia(): SmartProxyMediaResponse
}