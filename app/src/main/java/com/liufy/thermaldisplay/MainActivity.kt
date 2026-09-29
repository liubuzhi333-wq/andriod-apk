package com.liufy.thermaldisplay

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : Activity() {
    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        enterImmersiveMode()

        webView = WebView(this).apply {
            setBackgroundColor(android.graphics.Color.TRANSPARENT)
            webViewClient = WebViewClient()
            webChromeClient = WebChromeClient()
            isVerticalScrollBarEnabled = false
            isHorizontalScrollBarEnabled = false
            overScrollMode = View.OVER_SCROLL_NEVER
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                allowFileAccess = true
                allowContentAccess = true
                loadsImagesAutomatically = true
                cacheMode = WebSettings.LOAD_DEFAULT
                mediaPlaybackRequiresUserGesture = false
                builtInZoomControls = false
                displayZoomControls = false
                setSupportZoom(false) // 页面自己处理双指缩放，避免 WebView 整页缩放抢手势
            }
            loadUrl("file:///android_asset/index.html")
        }
        setContentView(webView)
    }

    private fun enterImmersiveMode() {
        @Suppress("DEPRECATION")
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        )
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) enterImmersiveMode()
    }

    override fun onResume() {
        super.onResume()
        webView.onResume()
        webView.resumeTimers()
        enterImmersiveMode()
    }

    override fun onPause() {
        webView.onPause()
        super.onPause()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        // 展示终端模式：屏蔽返回键，避免误退出。
    }

    override fun onDestroy() {
        webView.destroy()
        super.onDestroy()
    }
}
