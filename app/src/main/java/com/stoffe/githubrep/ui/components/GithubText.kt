package com.stoffe.githubrep.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SecondaryText(
    text: String,
) {
    Text(
        text = text,
        fontWeight = FontWeight.Light
    )
}

@Composable
fun SecondaryTextSmall(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        color = MaterialTheme.colorScheme.secondary
    )
}

@Composable
fun PrimaryText(
    modifier: Modifier = Modifier,
    text: String,
) {
    Text(
        modifier = modifier,
        text = text,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun TextRowCouple(
    primaryText: String,
    secondaryText: String,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        PrimaryText(text = primaryText)
        SecondaryText(text = secondaryText)
    }
}

@Composable
fun TextRowCoupleInverse(
    primaryText: String,
    secondaryText: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        SecondaryText(text = secondaryText)
        PrimaryText(text = primaryText)
    }
}