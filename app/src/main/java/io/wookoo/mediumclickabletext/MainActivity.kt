package io.wookoo.mediumclickabletext

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.LinkInteractionListener
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import io.wookoo.mediumclickabletext.ui.theme.MediumClickableTextTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MediumClickableTextTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        MultipleStyleTextWithLinks(modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }
}

@Composable
internal fun MultipleStyleTextWithLinks(modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current

    val prefix = stringResource(id = R.string.agreement_prefix)
    val privacyPolicy = stringResource(id = R.string.privacy_policy)
    val andText = stringResource(id = R.string.and)
    val terms = stringResource(id = R.string.terms_of_service)
    val suffix = stringResource(id = R.string.agreement_suffix)

    val listener = LinkInteractionListener { link ->
        if (link is LinkAnnotation.Clickable) {
            uriHandler.openUri(link.tag)
        }
    }

    val annotatedText = buildAnnotatedString {
        append("$prefix ")

        pushLink(
            link = LinkAnnotation.Clickable(
                tag = "https://google.com",
                styles = null,
                linkInteractionListener = listener
            )
        )
        withStyle(SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)) {
            append(privacyPolicy)
        }
        pop()

        append(" $andText ")

        pushLink(
            link = LinkAnnotation.Clickable(
                tag = "https://yandex.com",
                styles = null,
                linkInteractionListener = listener
            )
        )
        withStyle(SpanStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)) {
            append(terms)
        }
        pop()

        append(suffix)
    }

    Text(
        text = annotatedText,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier.padding(16.dp)
    )
}