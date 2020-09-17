package com.example.searchkeyword

import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.searchkeyword.repository.LastFmRepository
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import org.junit.runners.JUnit4
//import org.mockito.Mockito.MOCKITO_CORE

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(JUnit4::class)
class ApiServiceTest {

    @Mock
    private val apiService = LastFmRepository(application = Application())



    @Before
    fun setUp(){
    }
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.searchkeyword", appContext.packageName)
    }

    @Test
    fun albumRequest(){

    }

}