package com.stoffe.githubrep.ui.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stoffe.githubrep.api.entities.DetailedUser
import com.stoffe.githubrep.api.entities.Repository
import com.stoffe.githubrep.navigation.Argument
import com.stoffe.githubrep.repository.GithubRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel
@Inject constructor(
    private val githubRepositoryImp: GithubRepositoryImp,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val userID: String? = savedStateHandle[Argument.USERNAME]
    val user: MutableStateFlow<DetailedUser?> = MutableStateFlow(null)
    val repositories: MutableStateFlow<List<Repository>> = MutableStateFlow(listOf())

    init {
        userID?.let { userID ->
            viewModelScope.launch {
                val response = githubRepositoryImp.getUser(userID)
                response.collect { user.value = it }

                val response2 = githubRepositoryImp.getRepositories(userID)
                response2.collect { repositories.value = it }
            }
        }
    }
}