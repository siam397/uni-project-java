package com.example.artsell;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class SignupActivityTest {

    @Rule
    public ActivityTestRule<SignupActivity> signupActivityActivityTestRule=new ActivityTestRule<SignupActivity>(SignupActivity.class);
    private SignupActivity signupActivity=null;
    Instrumentation.ActivityMonitor monitor=getInstrumentation().addMonitor(ProfileActivity.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        signupActivity=signupActivityActivityTestRule.getActivity();
    }

    @Test
    public void testSignUp(){
        onView(withId(R.id.username)).perform(typeText("ghost"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.email)).perform(typeText("littleghost@hk.com"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.password)).perform(typeText("hornetishot"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signupbtn)).perform(click());
        Activity profile=getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(profile);
        profile.finish();
    }

    @After
    public void tearDown() throws Exception {
        signupActivity=null;
    }
}