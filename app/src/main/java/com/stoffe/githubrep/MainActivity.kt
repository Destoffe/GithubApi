package com.stoffe.githubrep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.stoffe.githubrep.navigation.Navigation
import com.stoffe.githubrep.ui.theme.GithubRepTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GithubRepTheme {
              Navigation()
            }
        }
    }
}