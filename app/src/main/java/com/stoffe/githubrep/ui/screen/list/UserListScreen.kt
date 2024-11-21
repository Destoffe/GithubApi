package com.stoffe.githubrep.ui.screen.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.stoffe.githubrep.api.entities.SimpleUser
import com.stoffe.githubrep.ui.components.PrimaryText

@Composable
fun UserListScreen(
    onUserClick: (String) -> Unit,
) {
    val viewModel = hiltViewModel<UserListViewModel>()
    val users by viewModel.users.collectAsState(initial = listOf())

    AnimatedVisibility(
        visible = users.isNotEmpty(),
        enter = fadeIn() + expandVertically()
    ) {
        UserListScreen(
            users = users,
            onUserClick = onUserClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserListScreen(
    users: List<SimpleUser>,
    onUserClick: (String) -> Unit,
) {
    val scrollState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        canScroll = { scrollState.canScrollForward || scrollState.canScrollBackward }
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = { Text(text = "Github Users") },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            state = scrollState,
        ) {
            items(users) { user ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    onClick = { onUserClick(user.login) }
                ) {
                    Row {
                        AsyncImage(
                            modifier = Modifier.size(64.dp),
                            model = user.avatarUrl,
                            contentDescription = "Image",
                            contentScale = ContentScale.Crop
                        )
                        PrimaryText(
                            modifier = Modifier.padding(16.dp),
                            text = user.login
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}