package com.dk24.moneycontrol.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dk24.moneycontrol.db.entities.Pot
import com.dk24.moneycontrol.utilites.Constants
import com.dk24.moneycontrol.utilites.changeAlpha

@Composable
fun PeggyBankPotViewCompose(
    pot: Pot,
    onClick: (Pot) -> Unit
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
                .padding(6.dp)
                .background(
                    MaterialTheme.colorScheme.secondaryContainer.changeAlpha(.5f),
                    RoundedCornerShape(16.dp)
                )
                .padding(18.dp)
                .clickable(onClick = {
                    onClick(pot)
                })
        ) {
            val total = pot.totalAmount?.toFloat() ?: 1f
            val saved = pot.savedAmount?.toFloat() ?: 1f
            val percentage: Float = saved.div(total)
            CircularProgressBarCompose(
                modifier = Modifier.fillMaxSize(),
                percentage = percentage,
                fillColor = MaterialTheme.colorScheme.inversePrimary,
                backgroundColor = MaterialTheme.colorScheme.inverseSurface,
                strokeWidth = 10.dp
            )
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = pot.name.orEmpty(),
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
        }
        Box(
            modifier = Modifier
                .height(180.dp)
                .width(180.dp)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 12.dp),
                text = "${pot.totalAmount}${Constants.currencyType} / ${pot.savedAmount}${Constants.currencyType}",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}
