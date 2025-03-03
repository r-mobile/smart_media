package kg.ram.outlinemedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import kg.ram.AppState
import kg.ram.MainVm
import kg.ram.outlinemedia.domain.ConnectState
import kg.ram.outlinemedia.ui.components.ProgramList
import kg.ram.outlinemedia.ui.components.UserInfo
import kg.ram.outlinemedia.ui.components.VideoPlayer
import kg.ram.outlinemedia.ui.theme.OutlineMediaTheme

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val vm by viewModels<MainVm>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        vm.runProxy()
        setContent {
            OutlineMediaTheme {
                Scaffold(
                    topBar = { TopAppBar(title = { Text("Smart Streaming") }) },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding -> Content(vm) }
            }
        }
    }

    @Composable
    private fun Content(vm: MainVm) {
        val value = vm.state.value
        when {
            value.connectionStatus == ConnectState.CONNECTED -> MediaContent(value)
            else -> Text("Loading...")
        }
    }

    @Composable
    private fun MediaContent(vm: AppState) {
        Column(modifier = Modifier.fillMaxSize()) {
            VideoPlayer(
                vm.stream?.url,
                vm.stream?.title,
                vm.proxy
            )
            Spacer(modifier = Modifier.height(8.dp))

            ProgramList()
            Spacer(modifier = Modifier.height(4.dp))

            UserInfo()
        }
    }
}