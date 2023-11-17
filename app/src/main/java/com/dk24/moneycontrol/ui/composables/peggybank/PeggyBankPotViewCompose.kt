package com.dk24.moneycontrol.ui.composables.peggybank

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.dk24.moneycontrol.db.model.MPot
import com.dk24.moneycontrol.enums.DBOperationType
import com.dk24.moneycontrol.ui.composables.customcomponents.CircularProgressBarCompose
import com.dk24.moneycontrol.utilites.Constants
import com.dk24.moneycontrol.utilites.changeAlpha

@Composable
fun PeggyBankPotViewCompose(
    mPot: MPot,
    onClick: (MPot, DBOperationType, ViewType) -> Unit
) {

    var isContextMenuVisible by remember {
        mutableStateOf(false)
    }
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val density = LocalDensity.current

    val dropdownItems = listOf(
        DropDownItem("Modify", DBOperationType.EDIT, ViewType.POT),
        DropDownItem("delete", DBOperationType.DELETE, ViewType.POT)
    )

    Surface(
        modifier = Modifier
            .padding(4.dp),
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .height(180.dp)
                .width(180.dp)
                .aspectRatio(1f)
                .onSizeChanged {
                    itemHeight = with(density) { it.height.toDp() }
                }
                .background(
                    MaterialTheme.colorScheme.secondaryContainer.changeAlpha(.5f),
                    RoundedCornerShape(16.dp)
                )
                .clickable(
                    onClick = {
                        if (!mPot.isCompleted) {
                            onClick(mPot, DBOperationType.ADD, ViewType.POTT)
                        }
                    }
                )
                .padding(28.dp)
        ) {
            val total = mPot.totalAmount
            val saved = mPot.savedAmount
            val percentage: Float = if (total != 0f && saved != 0f) saved.div(total) else 0f
            CircularProgressBarCompose(
                modifier = Modifier.fillMaxSize(),
                percentage = percentage,
                fillColor = MaterialTheme.colorScheme.inversePrimary,
                backgroundColor = MaterialTheme.colorScheme.inverseSurface,
                strokeWidth = 10.dp
            )
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = mPot.name,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )
        }
        Box(
            modifier = Modifier
                .height(180.dp)
                .width(180.dp)
        ) {
            Icon(
                Icons.TwoTone.MoreVert,
                "",
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.TopEnd)
                    .pointerInput(true) {
                        detectTapGestures(
                            onPress = {
                                isContextMenuVisible = true
                                pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                                val press = PressInteraction.Press(it)
                                interactionSource.emit(press)
                                tryAwaitRelease()
                                interactionSource.emit(PressInteraction.Release(press))
                            }
                        )
                    }
            )
            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp),
                text = "${mPot.totalAmount}${Constants.currencyType} / ${mPot.savedAmount}${Constants.currencyType}",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            DropdownMenu(
                expanded = isContextMenuVisible,
                onDismissRequest = {
                    isContextMenuVisible = false
                },
                offset = pressOffset.copy(
                    y = pressOffset.y - itemHeight + 30.dp,
                    x = pressOffset.x + 40.dp
                )
            ) {
                dropdownItems.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(text = it.text)
                        },
                        onClick = {
                            when (it.dbOperationType) {
                                DBOperationType.EDIT -> {
                                    onClick(mPot, it.dbOperationType, it.viewType)
                                }

                                DBOperationType.DELETE -> {
                                    onClick(mPot, it.dbOperationType, it.viewType)
                                }

                                else -> {
                                    // do nothing
                                }
                            }
                            isContextMenuVisible = false
                        })
                }
            }
        }
    }
}

data class DropDownItem(
    val text: String,
    val dbOperationType: DBOperationType,
    val viewType: ViewType
)

enum class ViewType {
    POT, POTT
}
