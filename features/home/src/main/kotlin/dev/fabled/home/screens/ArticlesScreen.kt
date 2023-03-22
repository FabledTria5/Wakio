package dev.fabled.home.screens

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun ArticlesScreen(modifier: Modifier = Modifier, targetUrl: String) {
    Box(modifier = modifier) {
        AndroidView(
            factory = {
                WebView(it).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true

                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            view?.evaluateJavascript(
                                "document.getElementById('adDiv111').remove();" +
                                        "document.getElementsByClassName('adhesion')[0].remove(); " +
                                        "document.getElementsByClassName('video-block__primary-video')[0].remove();",
                                null
                            )
                        }
                    }

                    loadUrl(targetUrl)
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}