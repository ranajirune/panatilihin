package com.raineru.panatilihin.tempo

import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.raineru.panatilihin.ui.LabelViewModel
import com.raineru.panatilihin.ui.theme.PanatilihinTheme

@Composable
fun RememberRecomposeDemo(
    label: String,
    onLabelChange: (String) -> Unit
) {
    TextField(value = label, onValueChange = onLabelChange)
}

@Preview(showBackground = true)
@Composable
fun RememberRecomposeDemoPreview() {
    PanatilihinTheme {
        val labelViewModel: LabelViewModel = hiltViewModel()
//        val label by labelViewModel.label.collectAsStateWithLifecycle()

        /*RememberRecomposeDemo(
            label = label,
            onLabelChange = labelViewModel::updateLabel
        )*/
    }
}