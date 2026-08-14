package io.github.hakunm.deepseekharness.ui

import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Dns
import androidx.compose.material.icons.outlined.Key
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.WifiFind
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import io.github.hakunm.deepseekharness.HarnessState
import io.github.hakunm.deepseekharness.HarnessViewModel
import io.github.hakunm.deepseekharness.R

@Composable
fun ConnectScreen(state: HarnessState, viewModel: HarnessViewModel) {
    var endpoint by remember { mutableStateOf(state.endpointDraft) }
    var code by remember { mutableStateOf("") }
    var deviceName by remember { mutableStateOf(Build.MODEL.ifBlank { "Android" }) }
    LaunchedEffect(state.endpointDraft) { endpoint = state.endpointDraft }

    Box(Modifier.fillMaxSize()) {
        IconButton(
            onClick = ::toggleLanguage,
            modifier = Modifier.align(Alignment.TopEnd).padding(12.dp),
        ) { Icon(Icons.Outlined.Language, stringResource(R.string.language)) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(horizontal = 24.dp, vertical = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(28.dp))
            BrandMark()
            Text(
                stringResource(R.string.connect_title),
                modifier = Modifier.padding(top = 24.dp),
                style = MaterialTheme.typography.displaySmall,
            )
            Text(
                stringResource(R.string.connect_subtitle),
                modifier = Modifier.padding(top = 8.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyLarge,
            )

            Column(
                Modifier.fillMaxWidth().widthIn(max = 520.dp).padding(top = 38.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
            ) {
                OutlinedTextField(
                    value = endpoint,
                    onValueChange = { endpoint = it; viewModel.setEndpointDraft(it) },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(stringResource(R.string.server_address)) },
                    placeholder = { Text("http://192.168.1.20:3090") },
                    leadingIcon = { Icon(Icons.Outlined.Dns, null) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
                    shape = MaterialTheme.shapes.medium,
                )
                if (endpoint.trim().startsWith("http://")) WarningBanner(stringResource(R.string.http_warning))
                OutlinedButton(
                    onClick = { viewModel.testConnection(endpoint) },
                    enabled = !state.busy && endpoint.isNotBlank(),
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = CircleShape,
                ) {
                    Icon(Icons.Outlined.WifiFind, null)
                    Text(stringResource(R.string.test_connection), modifier = Modifier.padding(start = 8.dp))
                }
                AnimatedVisibility(
                    visible = state.healthOk,
                    enter = fadeIn() + slideInVertically { it / 2 },
                ) {
                    Surface(color = MaterialTheme.colorScheme.secondaryContainer, shape = MaterialTheme.shapes.medium) {
                        Row(
                            Modifier.fillMaxWidth().padding(horizontal = 13.dp, vertical = 11.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(Icons.Outlined.CheckCircle, null, tint = MaterialTheme.colorScheme.secondary)
                            Text(
                                stringResource(R.string.connection_ok),
                                modifier = Modifier.padding(start = 10.dp),
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                            )
                        }
                    }
                }

                Row(
                    Modifier.fillMaxWidth().padding(vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    HorizontalDivider(Modifier.weight(1f), color = MaterialTheme.colorScheme.outlineVariant)
                    Text(
                        stringResource(R.string.device_pairing),
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        style = MaterialTheme.typography.labelLarge,
                    )
                    HorizontalDivider(Modifier.weight(1f), color = MaterialTheme.colorScheme.outlineVariant)
                }
                OutlinedTextField(
                    value = code,
                    onValueChange = { code = it.uppercase() },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(stringResource(R.string.pairing_code)) },
                    leadingIcon = { Icon(Icons.Outlined.Key, null) },
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                )
                OutlinedTextField(
                    value = deviceName,
                    onValueChange = { deviceName = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(stringResource(R.string.device_name)) },
                    leadingIcon = { Icon(Icons.Outlined.PhoneAndroid, null) },
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium,
                )
                Button(
                    onClick = { viewModel.pair(endpoint, code, deviceName) },
                    enabled = !state.busy && endpoint.isNotBlank() && code.isNotBlank() && deviceName.isNotBlank(),
                    modifier = Modifier.fillMaxWidth().height(54.dp),
                    shape = CircleShape,
                ) {
                    Icon(Icons.Outlined.Link, null)
                    Text(stringResource(R.string.connect), modifier = Modifier.padding(start = 8.dp))
                }
            }
            Spacer(Modifier.height(28.dp))
        }
    }
}

fun toggleLanguage() {
    val current = AppCompatDelegate.getApplicationLocales().toLanguageTags()
    val next = if (current.startsWith("en")) "zh-CN" else "en"
    AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(next))
}
