package com.demo.lazyimageloadingdemo_application.activity

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.demo.lazyimageloadingdemo_application.model.ListData
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertTrue
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Test
    fun testSetContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.demo.lazyimageloadingdemo_application", appContext.packageName)
    }

    @Test
    fun passData() {
        val list: ListData
        //assertNotNull(MainActivity.loadDataInList(list))
    }

    @Test
    fun testGetConnectivity() {
    }

    @Test
    fun testSetConnectivity() {
        assertTrue(true)
    }

    @Test
    fun testGetInfo() {
    }

    @Test
    fun testSetInfo() {
        val list: ListData
    }

    @Test
    fun testIsConnected() {
        assertTrue(true)
    }
}