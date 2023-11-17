package com.dk24.moneycontrol.ui.composables.customcomponents

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DropdownMenuExample() {
    var expanded by remember { mutableStateOf(false) }

    var selectedItem by remember { mutableStateOf("Option 1") }

    val items = listOf("Option 1", "Option 2", "Option 3")

    val density = LocalDensity.current.density
    val dropdownWidth = LocalDensity.current.run { 200.dp / density }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Main content

        Spacer(modifier = Modifier.height(16.dp))

        // Dropdown menu
        Box {
            BasicTextField(
                value = selectedItem,
                onValueChange = {},
                textStyle = LocalTextStyle.current.copy(color = LocalContentColor.current),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 48.dp)
                    .border(1.dp, Color.Gray, shape = MaterialTheme.shapes.small)
                    .clickable { expanded = true }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.widthIn(max = dropdownWidth)
            ) {
                items.forEach { item ->
                    DropdownMenuItem(
                        text = {
                            Text(text = item)
                        },
                        onClick = {
                            selectedItem = item
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DropdownMenuExamplePreview() {
    MaterialTheme {
        Surface {
            DropdownMenuExample()
        }
    }
}
