package com.stoffe.githubrep.ui.screen.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.stoffe.githubrep.R
import com.stoffe.githubrep.api.entities.DetailedUser
import com.stoffe.githubrep.api.entities.Repository
import com.stoffe.githubrep.misc.Utils
import com.stoffe.githubrep.ui.components.SecondaryTextSmall
import com.stoffe.githubrep.ui.components.TextRowCouple
import com.stoffe.githubrep.ui.components.TextRowCoupleInverse

@Composable
fun UserDetailScreen(
    onNavigateBack: () -> Unit,
) {
    val viewModel = hiltViewModel<UserDetailViewModel>()
    val user by viewModel.user.collectAsState(null)
    val repositories by viewModel.repositories.collectAsState()

    AnimatedVisibility(
        visible = user != null && repositories.isNotEmpty(),
        enter = fadeIn() + expandHorizontally()
    ) {
        user?.let { nonNullUser ->
            UserDetailScreen(
                user = nonNullUser,
                repositories = repositories,
                onNavigateBack = onNavigateBack
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UserDetailScreen(
    user: DetailedUser,
    repositories: List<Repository>,
    onNavigateBack: () -> Unit,
) {
    val scrollState = rememberLazyListState()

    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        canScroll = { scrollState.canScrollForward || scrollState.canScrollBackward }
    )
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(text = user.name.toString())
                },
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                            contentDescription = "Back navigation"
                        )
                    }
                }
            )
        }) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 8.dp)
        ) {
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(
                            RoundedCornerShape(
                                topEnd = 16.dp,
                                topStart = 16.dp,
                                bottomEnd = 16.dp,
                                bottomStart = 16.dp
                            )
                        ),
                    model = user.avatarUrl,
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    Text(text = user.login)
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        TextRowCouple(
                            primaryText = user.followers.toString(),
                            secondaryText = "followers"
                        )
                        TextRowCouple(
                            primaryText = user.following.toString(),
                            secondaryText = "following"
                        )
                    }
                }
            }
            LazyColumn(
                modifier = Modifier.padding(horizontal = 8.dp),
                state = scrollState
            ) {
                items(repositories) { rep ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        onClick = { Utils.launchCustomTab(context, rep.htmlUrl) }
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            TextRowCoupleInverse(
                                primaryText = rep.name,
                                secondaryText = "Repository name:"
                            )
                            TextRowCoupleInverse(
                                primaryText = rep.language.toString(),
                                secondaryText = "Language:"
                            )
                            TextRowCoupleInverse(
                                primaryText = rep.stargazersCount.toString(),
                                secondaryText = "Stars:"
                            )
                            if (rep.description != null) {
                                SecondaryTextSmall(text = rep.description.toString())
                            }
                        }
                    }
                }
            }
        }
    }
}