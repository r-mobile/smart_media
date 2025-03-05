package kg.ram.outlinemedia.domain

data class Stream(
    val format: StreamFormat?,
    val url: String?,
    val title: String?,
    val slots: List<TvSlot>?,
)