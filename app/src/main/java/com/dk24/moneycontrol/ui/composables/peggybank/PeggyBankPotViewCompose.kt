package com.dk24.moneycontrol.ui.composables.peggybank

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dk24.moneycontrol.db.model.MPot
import com.dk24.moneycontrol.enums.DBOperationType
import com.dk24.moneycontrol.ui.composables.customcomponents.CircularProgressBarCompose
import com.dk24.moneycontrol.utilites.Constants
import com.dk24.moneycontrol.utilites.changeAlpha

@Composable
fun PeggyBankPotViewCompose(
    mPot: MPot,
    onClick: (MPot, DBOperationType) -> Unit
) {
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
                .background(
                    MaterialTheme.colorScheme.secondaryContainer.changeAlpha(.5f),
                    RoundedCornerShape(16.dp)
                )
                .clickable(onClick = {
                    if (!mPot.isCompleted) {
                        onClick(mPot, DBOperationType.EDIT)
                    }
                })
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
            IconButton(modifier = Modifier
                .defaultMinSize(minWidth = 48.dp, minHeight = 48.dp)
                .align(Alignment.TopEnd),
                onClick = {
                    onClick(mPot, DBOperationType.DELETE)
                }) {
                Icon(Icons.TwoTone.Delete, "")
            }
            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp),
                text = "${mPot.totalAmount}${Constants.currencyType} / ${mPot.savedAmount}${Constants.currencyType}",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}
