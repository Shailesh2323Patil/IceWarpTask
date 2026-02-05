package com.shailesh.icewarptask.ui.channel.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shailesh.icewarptask.R
import com.shailesh.icewarptask.ui.channel.model.Group
import com.shailesh.icewarptask.ui.channel.model.GroupUiState
import com.shailesh.icewarptask.ui.channel.viewmodel.ChannelViewModel
import com.shailesh.icewarptask.ui.theme.titleColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChannelsScreen(
    viewModel: ChannelViewModel,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onThemeChangeClick: () -> Unit,
    isDarkTheme: Boolean
) {
    val items by viewModel.uiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState(false)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.channels_screen_title),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.titleColor
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back_button_description),
                            tint = MaterialTheme.colorScheme.titleColor
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onThemeChangeClick) {
                        Icon(
                            painter = painterResource(
                                id = if (isDarkTheme) {
                                    R.drawable.light_mode
                                } else {
                                    R.drawable.dark_mode
                                }
                            ),
                            contentDescription = stringResource(R.string.change_theme_button_description),
                            tint = MaterialTheme.colorScheme.titleColor
                        )
                    }

                    IconButton(onClick = onLogoutClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.logout),
                            contentDescription = stringResource(R.string.logout_button_description),
                            tint = MaterialTheme.colorScheme.titleColor
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp)
            ) {
                items(
                    items = items.groupList,
                    key = { it.id }
                ) { groupUiState ->
                    ChannelsContent(
                        groupUiState = groupUiState,
                        onGroupClick = { group -> viewModel.onCategoryClick(group) }
                    )
                }
            }

            if (isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun ChannelsContent(
    groupUiState: GroupUiState,
    onGroupClick: (Group) -> Unit
) {
    val isExpanded = groupUiState.isExpanded

    // Using theme colors for the collapsible view
    val containerColor = if (isExpanded) {
        MaterialTheme.colorScheme.secondaryContainer
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }

    val contentColor = if (isExpanded) {
        MaterialTheme.colorScheme.onSecondaryContainer
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isExpanded) 4.dp else 1.dp
        )
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onGroupClick(
                            Group(
                                id = groupUiState.id,
                                name = groupUiState.name
                            )
                        )
                    }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = groupUiState.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    color = if (isExpanded) contentColor else MaterialTheme.colorScheme.titleColor
                )

                Icon(
                    imageVector = if (isExpanded)
                        Icons.Default.KeyboardArrowUp
                    else
                        Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) stringResource(R.string.collapse_group_description) else stringResource(R.string.expand_group_description),
                    tint = if (isExpanded) contentColor else MaterialTheme.colorScheme.titleColor
                )
            }

            AnimatedVisibility(visible = isExpanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    HorizontalDivider(
                        modifier = Modifier.padding(bottom = 8.dp),
                        thickness = 0.5.dp,
                        color = contentColor.copy(alpha = 0.2f)
                    )

                    if (groupUiState.isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = contentColor,
                                strokeWidth = 2.dp
                            )
                        }
                    } else {
                        if (groupUiState.channelList.isEmpty()) {
                            Text(
                                text = stringResource(R.string.no_channels_available),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(vertical = 8.dp),
                                color = contentColor.copy(alpha = 0.6f)
                            )
                        } else {
                            groupUiState.channelList.forEach { channel ->
                                Text(
                                    text = channel.name.toString(),
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    color = contentColor
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
