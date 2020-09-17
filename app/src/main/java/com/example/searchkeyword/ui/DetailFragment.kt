package com.example.searchkeyword.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import com.example.searchkeyword.R
import com.example.searchkeyword.ui.MainFragment.Companion.URL
import kotlinx.android.synthetic.main.fragment_detail.*


/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailFragment : Fragment() {

    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(URL)
        }
        retainInstance = true
    }

    companion object {
        const val MAX_PROGRESS = 100
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webViewClient()
        web_view.webViewClient = WebViewClient()
        url?.let { web_view.loadUrl(it) }
        val webSettings = web_view.settings
        webSettings.javaScriptEnabled = true
    }

    // setting up an web view client
    private fun webViewClient() {
        web_view.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(
                view: WebView,
                newProgress: Int
            ) {
                super.onProgressChanged(view, newProgress)
                progress_bar!!.progress = newProgress
                if (newProgress < MAX_PROGRESS && progress_bar.visibility == ProgressBar.GONE) {
                    progress_bar.visibility = ProgressBar.VISIBLE
                }
                if (newProgress == MAX_PROGRESS) {
                    progress_bar.visibility = ProgressBar.GONE
                }
            }

        }
    }

}