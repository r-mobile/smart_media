package kg.ram.outlinemedia.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import kg.ram.outlinemedia.R
import kg.ram.outlinemedia.domain.TvSlot

@Composable
fun ProgramList(slots: List<TvSlot>?) {
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .border(border = BorderStroke(1.dp, Color.Gray), shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.baseline_live_tv_24),
                null
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = "TV programs:"
            )

        }
        Spacer(modifier = Modifier.height(4.dp))
        slots?.forEach { slot ->
            Text("${slot.name} (${slot.program})")
        }

    }
}