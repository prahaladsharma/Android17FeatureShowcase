package com.example.android17featureshowcase.feature.contact

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ContactScreen(viewModel: ContactViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val contact by viewModel.contact.collectAsState()

    val launcher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.PickContact()
        ) { uri ->
            uri?.let { viewModel.setContact(it) }
        }

    Column(modifier = Modifier.padding(16.dp)) {

        Button(onClick = { launcher.launch(null) }) {
            Text("Pick Contact")
        }

        contact?.let {
            Text("Selected: $it")
        }
    }
}