package kg.ram.outlinemedia.domain

import java.util.Date

data class Slot(
    val id: Int?,
    val startDate: Date?,
    val endDate: Date?,
    val program: String?,
    val name: String?
)