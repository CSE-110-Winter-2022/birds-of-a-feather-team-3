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
public class ImageEntryTest {

    @Rule
    public ActivityScenarioRule<ImageLinkEntry> scenarioRule = new ActivityScenarioRule<>(ImageLinkEntry.class);
    @Test
    public void testInvalidLink() {
        ActivityScenario<ImageLinkEntry> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);

        scenario.onActivity(activity -> {
            Button enterButton = activity.findViewById(R.id.confirm_profile_picture_button);
            EditText linkEdit = activity.findViewById(R.id.image_link_edit_text);
            assertEquals(0, ShadowAlertDialog.getShownDialogs().size());
            linkEdit.setText("invalid link lol");
            enterButton.performClick();
            assertEquals(1, ShadowAlertDialog.getShownDialogs().size());
            //Sends warning
        });
    }

    @Test
    public void testValidLink() {
        ActivityScenario<ImageLinkEntry> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            Button enterButton = activity.findViewById(R.id.confirm_profile_picture_button);
            EditText linkEdit = activity.findViewById(R.id.image_link_edit_text);
            assertEquals(0, ShadowAlertDialog.getShownDialogs().size());
            linkEdit.setText("https://cdn.download.ams.birds.cornell.edu/api/v1/asset/303800251/1800");
            enterButton.performClick();
            assertEquals(0, ShadowAlertDialog.getShownDialogs().size());
            //No warning
        });
    }
    @Test
    public void testBlankLink() {
        ActivityScenario<ImageLinkEntry> scenario = scenarioRule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            Button enterButton = activity.findViewById(R.id.confirm_profile_picture_button);
            EditText linkEdit = activity.findViewById(R.id.image_link_edit_text);
            assertEquals(0, ShadowAlertDialog.getShownDialogs().size());
            linkEdit.setText("");
            enterButton.performClick();
            assertEquals(0, ShadowAlertDialog.getShownDialogs().size());
            //No warning
        });
    }
}
