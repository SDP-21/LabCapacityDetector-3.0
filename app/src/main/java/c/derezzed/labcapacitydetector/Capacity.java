package c.derezzed.labcapacitydetector;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import static c.derezzed.labcapacitydetector.MainActivity.*;

public class Capacity extends AppCompatActivity {
    public static int queueTest;
    Intent home_intent;
    Intent capacity_intent;
    Intent history_intent;
    public int infoArray[];
    private Button queueButton;
    private Button noqueueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capacity);
        home_intent = new Intent(this, MainActivity.class);
        capacity_intent = new Intent(this, Capacity.class);
        history_intent = new Intent(this, History.class);


        ImageView myImage = (ImageView) findViewById(R.id.dudapic);
        myImage.setVisibility(View.GONE);

        Bundle secondIntent = getIntent().getExtras();

        infoArray = secondIntent.getIntArray("value");
        //String q = secondIntent.getStringExtra("queue");

        if (infoArray != null) {
            myImage.setVisibility(View.VISIBLE);
            TextView personnel = (TextView) findViewById(R.id.personnel);
            personnel.setText("Personnel Count: " + infoArray[1]);

            TextView queue = (TextView) findViewById(R.id.queue);
            queue.setText("Queue Count: " + infoArray[2]);

            if (infoArray[0] == 1) {
                TextView text = (TextView) findViewById(R.id.capacityselection);
                text.setText("Capacity: Duda Hall");
            }
        }


        // buttons

        queueButton = (Button) findViewById(R.id.queueButton);
        queueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView queue = (TextView) findViewById(R.id.queue);
                queue.setVisibility(View.GONE);

                int newVal = infoArray[2] + 1;
                TextView queuenew = (TextView) findViewById(R.id.queueCounter);
                queuenew.setText("Queue Count: " + Integer.toString(newVal));

            }
        });

        noqueueButton = (Button) findViewById(R.id.exitbutton);
        noqueueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView queue = (TextView) findViewById(R.id.queue);
                queue.setVisibility(View.GONE);

                int newVal = infoArray[2];
                TextView queuenew = (TextView) findViewById(R.id.queueCounter);
                queuenew.setText("Queue Count: " + Integer.toString(newVal));

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
