package io.github.hakunm.deepseekharness

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.v2.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.waitUntilAtLeastOneExists
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConnectScreenTest {
    @get:Rule
    val compose = createAndroidComposeRule<MainActivity>()

    @Test
    @OptIn(ExperimentalTestApi::class)
    fun firstLaunchUsesChineseAndShowsConnectionWorkflow() {
        val title = compose.activity.getString(R.string.connect_title)
        compose.waitUntilAtLeastOneExists(hasText(title), timeoutMillis = 5_000)
        assertTrue(title.any { Character.UnicodeScript.of(it.code) == Character.UnicodeScript.HAN })
        compose.onNodeWithText(title).assertIsDisplayed()
        compose.onNodeWithText(compose.activity.getString(R.string.server_address)).assertIsDisplayed()
        compose.onNodeWithText(compose.activity.getString(R.string.test_connection)).assertIsDisplayed()
        compose.onNodeWithText(compose.activity.getString(R.string.connect)).performScrollTo().assertIsDisplayed()

        compose.onNodeWithContentDescription(compose.activity.getString(R.string.language))
            .performClick()
        compose.waitUntilAtLeastOneExists(hasText("Connect to DeepSeek Harness"), timeoutMillis = 5_000)
        compose.onNodeWithText("Connect to DeepSeek Harness").assertIsDisplayed()
        compose.onNodeWithContentDescription("Language").performClick()
        compose.waitUntilAtLeastOneExists(hasText(title), timeoutMillis = 5_000)
    }
}
