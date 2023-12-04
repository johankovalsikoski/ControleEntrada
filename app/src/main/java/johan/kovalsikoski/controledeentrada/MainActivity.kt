package johan.kovalsikoski.controledeentrada

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import johan.kovalsikoski.controledeentrada.feature.entranceControl.EntranceControlScreen
import johan.kovalsikoski.controledeentrada.feature.entranceControl.EntranceControlViewModel
import johan.kovalsikoski.controledeentrada.ui.theme.AppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme(useDarkTheme = false) {
                EntranceControlScreen(
                    viewModel = viewModel()
                )
            }
        }
    }
}
