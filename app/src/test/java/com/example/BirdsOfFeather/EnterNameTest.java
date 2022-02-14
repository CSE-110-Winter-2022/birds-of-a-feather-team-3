package com.example.BirdsOfFeather;

import static org.junit.Assert.assertEquals;

import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.shadows.ShadowAlertDialog;

@RunWith(AndroidJUnit4.class)
public class EnterNameTest {
    @Rule
    public ActivityScenarioRule<MainActivity> scenarioRule = new ActivityScenarioRule<>(MainActivity.class);
    @Test
    public void emptyName() {
        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            Button saveButton = activity.findViewById(R.id.save_name_button);
            EditText nameView = activity.findViewById(R.id.enter_name_view);
            assertEquals(0, ShadowAlertDialog.getShownDialogs().size());
            nameView.setText("");
            saveButton.performClick();
            assertEquals(1, ShadowAlertDialog.getShownDialogs().size());
            //Sends warning
        });
    }

    @Test
    public void nonEmptyName() {
        ActivityScenario<MainActivity> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            Button saveButton = activity.findViewById(R.id.save_name_button);
            EditText nameView = activity.findViewById(R.id.enter_name_view);
            assertEquals(0, ShadowAlertDialog.getShownDialogs().size());
            nameView.setText("Bob");
            saveButton.performClick();
            assertEquals(0, ShadowAlertDialog.getShownDialogs().size());
            //No warning
        });
    }
}
