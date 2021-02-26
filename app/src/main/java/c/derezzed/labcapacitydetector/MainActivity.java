package c.derezzed.labcapacitydetector;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import android.widget.PopupMenu;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;
import android.graphics.Color;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentChange.Type;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Query.Direction;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.ServerTimestamp;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.Source;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
public class MainActivity extends AppCompatActivity {
    private Button loaderButton;
    public static String personnel_count;
    public static String queue_count;
    public int infoArray[];
    public static String hour;
    Intent home_intent;
    Intent capacity_intent;
    Intent history_intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // intents

        home_intent = new Intent(this, MainActivity.class);
        capacity_intent = new Intent(this, Capacity.class);
        history_intent = new Intent(this, History.class);

        // helpers

        Bundle secondIntent = getIntent().getExtras();

        //infoArray = secondIntent.getIntArray("value");


        TextView dateText = findViewById(R.id.date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
        hour = hourFormat.format(new Date());
        String currentDate= sdf.format(new Date());
        dateText.setText(currentDate);

        loaderButton = (Button) findViewById(R.id.dudaButton);
        loaderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // queueTest = 5;
               // TextView textView = (TextView) findViewById(R.id.queue);
                TextView colorChanger = (TextView) findViewById(R.id.dudaTitle);
                colorChanger.setTextColor(Color.GREEN);

//                personnel_count = Integer.toString(infoArray[0]);
//                queue_count = Integer.toString(infoArray[1]);

                infoArray = getUpdateInfo();
                //capacity_intent.putExtra("value", "five");
                //textView.setText(queueTest);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public int[] getUpdateInfo() {
        // downloads information from cloud service to update app
        return infoArray = new int [] {1, 20, 2, 0, 2, 1, 2, 2, 0, 3, 0, 4, 0, 5, 0, 6, 0, 7, 0, 8, 0, 9, 1,
                                       10, 2, 11, 3, 12, 5, 13, 8, 14, 9, 15, 13, 16, 14, 17, 11, 18, 8, 19, 8, 20, 7, 21, 4, 22, 4, 23, 2, 24, 2, Integer.parseInt(hour)};
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_Home:
                //Intent intent = new Intent(this, MainActivity.class);
                home_intent.putExtra("value", infoArray);
               // home_intent.putExtra("queue", queue_count);
                startActivity(home_intent);
                break;

            case R.id.action_Capacity:
                //Intent capacity_intent = new Intent(this, Capacity.class);
                capacity_intent.putExtra("value", infoArray);
                //capacity_intent.putExtra("queue", queue_count);
                startActivity(capacity_intent);
                break;

            case R.id.action_History:
                //Intent history_intent = new Intent(this, History.class);
                history_intent.putExtra("value", infoArray);
               // history_intent.putExtra("queue", queue_count);
                startActivity(history_intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    public void getDocument() {
        // [START get_document]
        DocumentReference docRef = db.collection("cities").document("SF");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        // [END get_document]
    }
}
