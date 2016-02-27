package com.jiandanbaoxian;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.jiandanbaoxian.ui.BaseActivity;

import junit.framework.TestResult;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void test()
    {
        int a = 1;
        int b = 2;
        assertEquals(a,b);
    }



}