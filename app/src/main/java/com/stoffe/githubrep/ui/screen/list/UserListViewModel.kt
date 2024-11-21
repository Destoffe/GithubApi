package com.stoffe.githubrep.ui.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stoffe.githubrep.api.entities.SimpleUser
import com.stoffe.githubrep.repository.GithubRepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UserListViewModel
@Inject constructor(
    private val githubRepositoryImp: GithubRepositoryImp
) : ViewModel() {
    private val _users: MutableStateFlow<List<SimpleUser>> = MutableStateFlow(listOf())
    val users: Flow<List<SimpleUser>>
        get() = _users

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                githubRepositoryImp.getUsers().collect {
                    _users.value = it
                }
            }
        }
    }
}