package kg.ram.out_proxy.utils

import androidx.annotation.OptIn
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.okhttp.OkHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import okhttp3.OkHttpClient
import java.net.Proxy

@OptIn(UnstableApi::class)
fun ExoPlayer.playStream(streamUrl: String, proxy: Proxy, playWhenReady: Boolean = true): ExoPlayer {
    val client = OkHttpClient.Builder().proxy(proxy).build()
    val factory = OkHttpDataSource.Factory(client)
    val hlsMediaSource = HlsMediaSource.Factory(factory)
        .createMediaSource(MediaItem.fromUri(streamUrl))
    setMediaSource(hlsMediaSource)
    prepare()
    this.playWhenReady = playWhenReady
    return this
}