package edu.liceo.fieldkit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import edu.liceo.fieldkit.ui.LiceoAccountApp
import edu.liceo.fieldkit.ui.theme.LiceoFieldKitTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LiceoFieldKitTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LiceoAccountApp()
                }
            }
        }
    }
}
