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

import java.util.concurrent.TimeUnit;

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
    Instrumentation.ActivityMonitor monitor=getInstrumentation().addMonitor(LandingPageActivity.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitor1=getInstrumentation().addMonitor(LoginActivity.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        signupActivity=signupActivityActivityTestRule.getActivity();
    }

    @Test
    public void testSignUp() throws InterruptedException {
        String[] arr={"mofiz","cj","hornet","ghost","ori","tommy","trevor"};
        for (int i=0;i<arr.length;i++){
            onView(withId(R.id.username)).perform(typeText(arr[i]));
            Espresso.closeSoftKeyboard();
            onView(withId(R.id.email)).perform(typeText(arr[i]+"@"+arr[i]+".com"));
            Espresso.closeSoftKeyboard();
            onView(withId(R.id.password)).perform(typeText(arr[i]));
            Espresso.closeSoftKeyboard();
            onView(withId(R.id.signupbtn)).perform(click());
            Activity landing=getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
            onView(withId(R.id.profile)).perform(click());
            onView(withId(R.id.logout)).perform(click());
            TimeUnit.SECONDS.sleep(5);
            Activity landing1=getInstrumentation().waitForMonitorWithTimeout(monitor1,5000);
            onView(withId(R.id.GoToSignUp)).perform(click());
            TimeUnit.SECONDS.sleep(5);
        }
//        assertNotNull(landing);
    }

    @After
    public void tearDown() throws Exception {
        signupActivity=null;
    }
}