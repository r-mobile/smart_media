package kg.ram.outlinemedia.mappers

import kg.ram.out_proxy.domain.StreamData
import kg.ram.out_proxy.domain.StreamType
import kg.ram.outlinemedia.domain.Stream
import kg.ram.outlinemedia.domain.StreamFormat

fun StreamFormat.toStreamType(): StreamType {
    return when (this) {
        StreamFormat.video -> StreamType.HLS
        StreamFormat.audio -> StreamType.AUDIO
    }
}

fun Stream.toStreamData() = StreamData(url, format?.toStreamType())