// Mark Manahan

package cs4720.cs.virginia.edu.coreskills;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**

 Assignment Notes: For this activity, the user should be able to
 save and load the username and computing ID from SQLite.  Several
 helper classes (DatabaseHelper and Section) are included to make
 this a bit easier.

 NOTE: YOU MUST ONLY SHOW THE LAST RECORD FROM THE DATABASE.  i.e.
 the record that was most recently added!

 Reference:
 https://github.com/marksherriff/StorageExample
 https://developer.android.com/training/basics/data-storage/databases.html

 More references:
 https://developer.android.com/training/data-storage/sqlite.html

 */

public class SQLiteActivity extends AppCompatActivity {

    EditText nameEditText;
    EditText compIDEditText;

    TextView nameTextView;
    TextView compIDTextView;

    DatabaseHelper database_helper;

    String student_name, student_computing_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        nameEditText = (EditText) findViewById(R.id.nameEditText);
        compIDEditText = (EditText) findViewById(R.id.compIDEditText);

        nameTextView = (TextView) findViewById(R.id.nameTextView);
        compIDTextView = (TextView) findViewById(R.id.compIDTextView);

        database_helper = new DatabaseHelper(this); // initialize database helper
    }

    public void saveToDatabase(View view) {

        // Add code here to save to the database
        SQLiteDatabase database = database_helper.getWritableDatabase(); // initialize a writable/readable database

        ContentValues data = new ContentValues(); // create a new value map object

        student_name = nameEditText.getText().toString(); // pull name
        student_computing_id = compIDEditText.getText().toString(); // pull comp id

        data.put("compid", student_computing_id); // insert comp id into value map
        data.put("name", student_name); // insert name into value map

        long new_row_id = database.insert("person", null, data); // store data in value map in the "person" table in database

    }

    public void loadFromDatabase(View view) {

        // Add code here to load from the database
        SQLiteDatabase database = database_helper.getReadableDatabase(); // initialize a readable database

        String[] projection = {"compid", "name"}; // specify what columns to pull from in the database

        String order = "compid" + " DESC"; // specify the order in which pulled data should be ordered

        // make a query to the person table in the database, pulling from specified columns, having no WHERE conditions nor group/having conditions, according to specified ordering
        Cursor cursor = database.query("person", projection, null, null, null, null, order);

        while (cursor.moveToNext()) { // for everything pulled from the query until the last row has been reached

            student_computing_id = cursor.getString(cursor.getColumnIndexOrThrow("compid")); // pull the computing id from the query
            student_name = cursor.getString(cursor.getColumnIndexOrThrow("name")); // pull the name from the query

        }

        cursor.close(); // terminate cursor object

        nameTextView.setText(student_name); // update the name that is displayed on the screen
        compIDTextView.setText(student_computing_id); // update the name that is displayed on the screen

    }
}
