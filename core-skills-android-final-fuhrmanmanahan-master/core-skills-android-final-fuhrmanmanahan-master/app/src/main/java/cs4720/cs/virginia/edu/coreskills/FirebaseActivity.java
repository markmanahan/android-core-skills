package cs4720.cs.virginia.edu.coreskills;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;
import java.util.Map;

/**

Assignment Notes: This activity should pull data from Firebase based upon
the course requested in the EditText.  However, note that CS 4720 is
the only course in this database right now.  If you are going to use
.getReference() or .getChild(), you'll need to get "CS/4720" to get the
proper data entry.  Also, consider using the .addListenerForSingleValueEvent()
method for pulling data.

Reference this material for guidance:
https://firebase.google.com/docs/database/android/read-and-write

*/

public class FirebaseActivity extends AppCompatActivity {

    EditText courseEditText;
    TextView courseNameTextView;
    TextView instructorTextView;
    TextView locationTextView;

    String TAG = "FireBase";

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("CS/4720");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        courseEditText = (EditText) findViewById(R.id.fbcourseEditText);
        courseNameTextView = (TextView) findViewById(R.id.fbcourseNameTextView);
        instructorTextView = (TextView) findViewById(R.id.fbinstructorTextView);
        locationTextView = (TextView) findViewById(R.id.fblocationTextView);

    }

    public void downloadFirebaseData(View view) {
        // Add code here to pull the data from Firebase

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                Object returnedData = dataSnapshot.getValue();
                String returnedDataString = "" + returnedData;
                String[] courseInfo = returnedDataString.split(",");
                String location = courseInfo[0].split("=")[1];
                String instructor = courseInfo[1].split("=")[1];
                String courseName = courseInfo[2].split("=")[1];
                courseName = courseName.substring(0, courseName.length()-1);


                courseNameTextView.setText(courseName);
                locationTextView.setText(location);
                instructorTextView.setText(instructor);

                //Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Failed to read value
                Log.d(TAG, "Failed to read value.");
            }
        });

    }

    @IgnoreExtraProperties
    public class Course {

        public String courseName;
        public String instructor;
        public String location;

        public Course() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Course(String courseName, String instructor, String location) {
            this.courseName = courseName;
            this.instructor = instructor;
            this.location = location;
        }

    }

    private void writeNewCourse(String courseId, String courseName, String instructor, String location) {
        Course course = new Course(courseName, instructor, location);

        myRef.child("courses").child(courseId).setValue(course);

        //myRef.child("users").child(courseId).child("courseName").setValue("eCommerce");
    }


}
