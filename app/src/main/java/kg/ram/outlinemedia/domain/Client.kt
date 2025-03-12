package kg.ram.outlinemedia.domain

import com.google.gson.annotations.SerializedName

data class Client(
    val ip: String?,
    val city: String?,
    val org: String?,
    val country: String?,
    @SerializedName("country_iso")
    val countryIso: String?,
)
