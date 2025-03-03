package kg.ram.outlinemedia.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import kg.ram.out_proxy.utils.playStream
import java.net.Proxy

@Composable
fun VideoPlayer(
    url: String?,
    tvShowName: String?,
    proxy: Proxy?
) {
    if(url == null || tvShowName == null || proxy == null) return
    Column {
        AndroidView(
            factory = { context ->
                PlayerView(context).also { playerView ->
                    playerView.player = ExoPlayer.Builder(context)
                        .build()
                        .playStream(url, proxy)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp)
        )
        Text(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
            style = MaterialTheme.typography.titleLarge,
            text = tvShowName
        )
    }
}