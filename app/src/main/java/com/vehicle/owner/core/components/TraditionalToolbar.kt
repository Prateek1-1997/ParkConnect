package com.vehicle.owner.core.components

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.vehicle.owner.R
import com.vehicle.owner.ui.theme.primaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TraditionalToolbar(
    scrollBehavior: TopAppBarScrollBehavior,
    onBack: (Intent?) -> Unit,
    toolbarTitle: Int,
    toolbarTitleString: String? = null,
    actions: @Composable RowScope.() -> Unit = {},
) {
    TopAppBar(
        title = {
            toolbarTitleString?.let {
                Text(
                    it,
                    style = MaterialTheme.typography.titleMedium,
                    color = primaryColor
                )
            } ?: run {
                Text(
                    stringResource(id = toolbarTitle),
                    style = MaterialTheme.typography.titleMedium,
                    color = primaryColor
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { onBack(null) }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Go back"
                )
            }
        },
        scrollBehavior = scrollBehavior,
        actions = actions,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TraditionalToolbar(
    scrollBehavior: TopAppBarScrollBehavior,
    onBack: () -> Unit,
    toolbarTitle: String
) {
    TopAppBar(
        modifier = Modifier.background(Color.White),
        title = {
            Text(
                text = toolbarTitle,
                style = MaterialTheme.typography.titleMedium,
                color = primaryColor
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Go back"
                )
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ComposablePreview() {
    TraditionalToolbar(
        TopAppBarDefaults.pinnedScrollBehavior(),
        onBack = {},
        toolbarTitle = R.string.app_name,
        null
    )
}
