package com.shailesh.icewarptask.ui.channel.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shailesh.icewarptask.R
import com.shailesh.icewarptask.ui.channel.model.Group
import com.shailesh.icewarptask.ui.channel.model.GroupUiState
import com.shailesh.icewarptask.ui.channel.viewmodel.ChannelViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChannelsScreen(
    viewModel: ChannelViewModel,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit,
    onThemeChangeClick: () -> Unit
) {
    val items by viewModel.uiState.collectAsState()
    val isDarkTheme = isSystemInDarkTheme()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Back", fontSize = 16.sp, fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back"
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
                            contentDescription = "Change Theme"
                        )
                    }

                    IconButton(onClick = onLogoutClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.logout),
                            contentDescription = "Logout"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFF5F5F5))
            )
        },
        containerColor = Color(0xFFF5F5F5)
    )
    { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(
                paddingValues
            )
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
    }
}

@Composable
fun ChannelsContent(
    groupUiState: GroupUiState,
    onGroupClick: (Group) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .background(Color(0xFFF2F2F2), RoundedCornerShape(8.dp))
    ) {

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
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = if (groupUiState.isExpanded)
                    Icons.Default.KeyboardArrowUp
                else
                    Icons.Default.KeyboardArrowDown,
                contentDescription = null
            )
        }

        AnimatedVisibility(groupUiState.isExpanded) {
            when {
                groupUiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(16.dp)
                    )
                }

                else -> {
                    Column {
                        groupUiState.channelList.forEach {
                            Text(
                                text = it.name.toString(),
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}