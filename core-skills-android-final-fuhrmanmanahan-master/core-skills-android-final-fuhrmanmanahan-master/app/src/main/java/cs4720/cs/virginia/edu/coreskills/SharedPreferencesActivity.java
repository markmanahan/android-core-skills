// Mark Manahan

package cs4720.cs.virginia.edu.coreskills;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**

 Assignment Notes: For this activity, the user should be able to
 save and load the username and computing ID from SharedPreferences,
 the key/value store built into Android.

 Reference:
 https://github.com/marksherriff/StorageExample

 More references:
 https://developer.android.com/training/data-storage/shared-preferences.html
 https://stackoverflow.com/questions/3624280/how-to-use-sharedpreferences-in-android-to-store-fetch-and-edit-values?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
 https://stackoverflow.com/questions/4396376/how-to-get-edittext-value-and-display-it-on-screen-through-textview/4396400

 */

public class SharedPreferencesActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "CoreSkillsPrefsFile";

    EditText spnameEditText;
    EditText spcompIDEditText;

    TextView spnameTextView;
    TextView spcompIDTextView;

    SharedPreferences shared_preferences;
    SharedPreferences.Editor editor;

    String saved_student_id, saved_computing_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_preferences);

        spnameEditText = (EditText) findViewById(R.id.spnameEditText);
        spcompIDEditText = (EditText) findViewById(R.id.spcompIDEditText);

        spnameTextView = (TextView) findViewById(R.id.spnameTextView);
        spcompIDTextView = (TextView) findViewById(R.id.spcompIDTextView);

        // initialize shared preferences object associated with PREFS file in private mode
        shared_preferences = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

    }

    public void saveToSharedPreferences(View view) {

        // Add your code here to save
        editor = shared_preferences.edit(); // initialize editor object to allow editing on presf file
        editor.putString("student_name", spnameEditText.getText().toString()); // pull name, save by key "student_name"
        editor.putString("student_computing_id", spcompIDEditText.getText().toString()); // pull computing id, save by key "student_computing_id"
        editor.apply(); // commit changes

    }

    public void loadFromSharedPreferences(View view) {

        // Add your code here to load
        saved_student_id = shared_preferences.getString("student_name", null); // pull name from prefs
        saved_computing_id = shared_preferences.getString("student_computing_id", null); // pull comp id from presf

        spnameTextView.setText(saved_student_id); // update name displayed
        spcompIDTextView.setText(saved_computing_id); // update comp id displayed

    }
}