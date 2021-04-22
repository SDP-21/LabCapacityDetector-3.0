package c.derezzed.labcapacitydetector;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Color;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
//import com.google.firebase.referencecode.database.models.Post;
//import com.google.firebase.referencecode.database.models.User;

public class MainActivity extends AppCompatActivity {
    private Button loaderButton;
    private Button adminButton;
    private DocumentReference mDatabase;
    private DocumentReference mDatabase2;
    public String capacityUpdater;
    public String historyGetter;
    public static String personnel_count;
    public static String queue_count;
    public int infoArray[];
    public static String hour;
    public static String capOutput;
    public static int intCapOutput = 7;
    public String adminName;
    public int adminCounter = 0;
    Intent home_intent;
    Intent capacity_intent;
    Intent history_intent;
    TextView displayData;
    EditText mEdit;
    TextView adminText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // firebase tools
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        mDatabase = db.collection("pplcount").document("data1");
                // note: mdatabase multiple collections can be made for each person for an accurate
                // history reading

        displayData=new TextView(this);


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


        //capacity_intent.putExtra("value", "five");
        //textView.setText(queueTest);


        loaderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // queueTest = 5;
               // TextView textView = (TextView) findViewById(R.id.queue);
                TextView colorChanger = (TextView) findViewById(R.id.dudaTitle);
                colorChanger.setTextColor(Color.GREEN);

//                personnel_count = Integer.toString(infoArray[0]);
//                queue_count = Integer.toString(infoArray[1]);
                getUpdateInfo("test");

            }
        });

        adminButton = (Button) findViewById(R.id.adminButton);

        adminButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mEdit = (EditText)findViewById(R.id.editText1);
                adminName = mEdit.getText().toString();

                adminText=(TextView)findViewById(R.id.adminText);

                if (adminName.equals("sdp21team9")) {
                    mEdit.getText().clear();
                    mEdit.setHint("Welcome Admin!");
                    adminCounter = 1;
                }
                else {
                    mEdit.getText().clear();
                    mEdit.setHint("Incorrect Password");
                }

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void getUpdateInfo(String userId) {
        // downloads information from cloud service to update app

        mDatabase.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {


            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document = task.getResult();
                if (task.isSuccessful()) {

                    if (document.exists()) {
                        Log.d("firebase", "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d("firebase", "No such document");
                    }
                } else {
                    Log.d("firebase", "get failed with ", task.getException());
                }

                capacityUpdater = document.getData().toString();
                System.out.println(capacityUpdater);
                char[] ch = new char[capacityUpdater.length()];
                for (int i = 0; i < capacityUpdater.length(); i++) {
                    ch[i] = capacityUpdater.charAt(i);
                }


                char first = ch[8];
                char second = ch[9];

                String sfirst = String.valueOf(first);
                String sSecond = String.valueOf(second);


                if ((sSecond.equals("0")) || (sSecond.equals("1")) || (sSecond.equals("2")) || (sSecond.equals("3")) || (sSecond.equals("4")) || (sSecond.equals("5")) || (sSecond.equals("6")) || (sSecond.equals("7")) || (sSecond.equals("8"))|| (sSecond.equals("9"))) {
                    capOutput = sfirst + sSecond;
                }
                else {
                    capOutput = sfirst;
                }

                intCapOutput = Integer.parseInt(capOutput);

//                displayData=(TextView)findViewById(R.id.displayData);
                displayData.setText(capacityUpdater);



                // for firebase
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                mDatabase2 = db.collection("datetime").document("time1");


                mDatabase2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        if (task.isSuccessful()) {

                            if (document.exists()) {
                                Log.d("firebase", "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d("firebase", "No such document");
                            }
                        } else {
                            Log.d("firebase", "get failed with ", task.getException());
                        }

                        historyGetter = document.getData().toString();
                        System.out.println(historyGetter);

                        char[] ch2 = new char[historyGetter.length()];
                        for (int i = 0; i < historyGetter.length(); i++) {
                            ch2[i] = historyGetter.charAt(i);
                        }

                        infoArray = new int [] {1, intCapOutput, 2, 0, Character.getNumericValue(ch2[24]), 1, Character.getNumericValue(ch2[51]), 2, Character.getNumericValue(ch2[78]), 3, Character.getNumericValue(ch2[105]), 4, Character.getNumericValue(ch2[132]), 5, Character.getNumericValue(ch2[159]), 6, Character.getNumericValue(ch2[186]), 7, Character.getNumericValue(ch2[213]), 8, Character.getNumericValue(ch2[240]), 9, Character.getNumericValue(ch2[267]),
                                10, Character.getNumericValue(ch2[294]), 11, Character.getNumericValue(ch2[321]), 12, Character.getNumericValue(ch2[348]), 13, Character.getNumericValue(ch2[375]), 14, Character.getNumericValue(ch2[402]), 15, Character.getNumericValue(ch2[429]), 16, Character.getNumericValue(ch2[456]), 17, Character.getNumericValue(ch2[24]), 18, Character.getNumericValue(ch2[483]), 19, Character.getNumericValue(ch2[510]), 20, Character.getNumericValue(ch2[537]), 21, Character.getNumericValue(ch2[564]), 22, Character.getNumericValue(ch2[591]), 23, Character.getNumericValue(ch2[618]), 24, Character.getNumericValue(ch2[645]), Integer.parseInt(hour), adminCounter};
                                System.out.println(ch2[645]);
                    }
                });







            }
        });



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
}
