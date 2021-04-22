package c.derezzed.labcapacitydetector;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Capacity extends AppCompatActivity {
    public static int queueTest;
    Intent home_intent;
    Intent capacity_intent;
    Intent history_intent;
    public int infoArray[];
    private Button queueButton;
    private Button noqueueButton;
    private DocumentReference mDatabase2;
    EditText editText2;
    TextView queueText;
    TextView successText;
    public String queueName;
    public String first;
    public String second;
    public String third;
    public static int newVal;

    EditText editPersonnel;
    EditText editQueue;
    Button updatePersonnel;
    Button updateQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capacity);
        home_intent = new Intent(this, MainActivity.class);
        capacity_intent = new Intent(this, Capacity.class);
        history_intent = new Intent(this, History.class);

        editText2 = (EditText)findViewById(R.id.editText2);
        editPersonnel = (EditText) findViewById(R.id.editPersonnel);
        editQueue = (EditText) findViewById(R.id.editQueue);
        queueText = (TextView) findViewById(R.id.queueText);
        updatePersonnel = (Button) findViewById(R.id.personnelUpdate);
        updateQueue = (Button) findViewById(R.id.queueUpdate);
        successText = (TextView) findViewById(R.id.queueSuccess);

        editPersonnel.setVisibility(View.GONE);
        editQueue.setVisibility(View.GONE);
        editText2.setVisibility(View.GONE);
        queueText.setVisibility(View.GONE);
        updatePersonnel.setVisibility(View.GONE);
        updateQueue.setVisibility(View.GONE);
        successText.setVisibility(View.GONE);





        ImageView myImage = (ImageView) findViewById(R.id.dudapic);
        myImage.setVisibility(View.GONE);

        Bundle secondIntent = getIntent().getExtras();

        infoArray = secondIntent.getIntArray("value");
        //String q = secondIntent.getStringExtra("queue");

        if (infoArray != null) {

            queueText.setVisibility(View.VISIBLE);
            editText2.setVisibility(View.VISIBLE);
            myImage.setVisibility(View.VISIBLE);
            TextView personnel = (TextView) findViewById(R.id.personnel);
            //System.out.println(infoArray);

            personnel.setText("Personnel Count: " + infoArray[1]);

            TextView queue = (TextView) findViewById(R.id.queue);
            queue.setVisibility(View.GONE);

            if (infoArray[0] == 1) {
                TextView text = (TextView) findViewById(R.id.capacityselection);
                text.setText("Vacancy: Duda Hall");

                // for firebase
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                mDatabase2 = db.collection("appqueue").document("init");

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

                        first = (String) document.get("1");
                        second = (String) document.get("2");
                        third = (String) document.get("3");

                        int newVal = 0;
                        if (!(first.equals("Empty"))) {
                            newVal+=1;
                        }
                        if (!(second.equals("Empty"))) {
                            newVal+=1;
                        }
                        if (!(third.equals("Empty"))) {
                            newVal+=1;
                        }

                        if (infoArray[54] != 1) {
                            TextView queuenew = (TextView) findViewById(R.id.queueCounter);
                            queuenew.setText("Queue Count: " + Integer.toString(newVal));
                        }

                    }
                });

            }

            if (infoArray[54] == 1) {
                TextView adminDetection = (TextView) findViewById(R.id.adminDetection);
                adminDetection.setText("Admin Detected: Tap Values to Manually Change");
                personnel.setText("Personnel Count: ");
                //queue.setText("Queue Count: ");

                TextView queuenew = (TextView) findViewById(R.id.queueCounter);
                queuenew.setText("Queue Count: ");

                editQueue.setVisibility(View.VISIBLE);
                editPersonnel.setVisibility(View.VISIBLE);
                updatePersonnel.setVisibility(View.VISIBLE);
                updateQueue.setVisibility(View.VISIBLE);


                // FIREBASE PERSONNEL
                final FirebaseFirestore dbP = FirebaseFirestore.getInstance();
                mDatabase2 = dbP.collection("pplcount").document("data1");

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

                        // PERSONNEL BUTTON

                        updatePersonnel.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {
                                Map<String, Object> deletePers = new HashMap<>();
                                deletePers.put("Count", FieldValue.delete());
                                dbP.collection("pplcount").document("data1")
                                        .update(deletePers);


                                String persvalue= editPersonnel.getText().toString();
                                int finalValue=Integer.parseInt(persvalue);

                                Map<String, Object> AddPers = new HashMap<>();
                                AddPers.put("Count", FieldValue.arrayUnion(finalValue));
                                dbP.collection("pplcount").document("data1")
                                        .update(AddPers);

                            }
                        });

                    }
                });


                // FIREBASE QUEUE
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                mDatabase2 = db.collection("appqueue").document("init");

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

                        // QUEUE BUTTON
                        updateQueue.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View view) {

                                String quevalue= editQueue.getText().toString();
                                int finalValue=Integer.parseInt(quevalue);

                                Map<String, Object> queuelist = new HashMap<>();

                                if (finalValue == 0) {
                                    queuelist.put("1", "Empty");
                                    queuelist.put("2", "Empty");
                                    queuelist.put("3", "Empty");
                                    db.collection("appqueue").document("init")
                                            .update(queuelist);
                                }
                                if (finalValue == 1) {
                                    queuelist.put("2", "Empty");
                                    queuelist.put("3", "Empty");
                                    db.collection("appqueue").document("init")
                                            .update(queuelist);
                                }
                                if (finalValue == 2) {
                                    queuelist.put("3", "Empty");
                                    db.collection("appqueue").document("init")
                                            .update(queuelist);
                                }


                            }
                        });

                    }
                });




            }
        }


        // buttons

        queueButton = (Button) findViewById(R.id.queueButton);
        queueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queueButton.setEnabled(false);
                TextView queue = (TextView) findViewById(R.id.queue);
                queue.setVisibility(View.GONE);




                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                mDatabase2 = db.collection("appqueue").document("init");

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

                        first = (String) document.get("1");
                        second = (String) document.get("2");
                        third = (String) document.get("3");

//                        int newVal = 0;
//                        if (!(first.equals("Null"))) {
//                            newVal+=1;
//                        }
//                        if (!(second.equals("Null"))) {
//                            newVal+=1;
//                        }
//                        if (!(third.equals("Null"))) {
//                            newVal+=1;
//                        }
//                        TextView queuenew = (TextView) findViewById(R.id.queueCounter);
//                        queuenew.setText("Queue Count: " + Integer.toString(newVal));

                        queueName = editText2.getText().toString();
                        Map<String, Object> queuelist = new HashMap<>();
                        if ((third.equals("Empty"))) {
                            queuelist.put("1", queueName);   //was "In Queue"
                            queuelist.put("2", first);
                            queuelist.put("3", second);
                            db.collection("appqueue").document("init")
                                    .update(queuelist);
                            newVal += 1;
//                            TextView queuenew = (TextView) findViewById(R.id.queueCounter);
//                            queuenew.setText("Queue Count: " + Integer.toString(newVal));
                            successText.setText("Added to Queue");
                            successText.setVisibility(View.VISIBLE);

                        }
                        else {
                            successText.setText("Queue Full");
                            successText.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });

        noqueueButton = (Button) findViewById(R.id.exitbutton);
        noqueueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noqueueButton.setEnabled(false);
                TextView queue = (TextView) findViewById(R.id.queue);
                queue.setVisibility(View.GONE);

//                int newVal = infoArray[2];
//                TextView queuenew = (TextView) findViewById(R.id.queueCounter);
//                queuenew.setText("Queue Count: " + Integer.toString(newVal));

                // for firebase
                final FirebaseFirestore db = FirebaseFirestore.getInstance();
                mDatabase2 = db.collection("appqueue").document("init");

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

                        first = (String) document.get("1");
                        second = (String) document.get("2");
                        third = (String) document.get("3");

                        newVal-=1;
//                        TextView queuenew = (TextView) findViewById(R.id.queueCounter);
//                        queuenew.setText("Queue Count: " + Integer.toString(newVal));

                        queueName = editText2.getText().toString();
                        Map<String, Object> queuelist = new HashMap<>();
                        queuelist.put("1", second);   //was "In Queue"
                        queuelist.put("2", third);
                        queuelist.put("3", "Empty");
                        db.collection("appqueue").document("init")
                                .update(queuelist);


                    }
                });

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
