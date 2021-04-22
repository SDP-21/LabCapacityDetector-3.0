package c.derezzed.labcapacitydetector;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class History extends AppCompatActivity {

    Intent home_intent;
    Intent capacity_intent;
    Intent history_intent;
    public int infoArray[];
    public int minArray[];
    String current;
    String six;
    String twelve;
    String eighteen;
    String twentyFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int temp;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        // preliminary summary
        TextView text = (TextView) findViewById(R.id.historysummary);
        text.setVisibility(View.GONE);

        // intents


        home_intent = new Intent(this, MainActivity.class);
        capacity_intent = new Intent(this, Capacity.class);
        history_intent = new Intent(this, History.class);

        // helpers

        Bundle secondIntent = getIntent().getExtras();

        infoArray = secondIntent.getIntArray("value");

        // graphing

        GraphView graph = (GraphView) findViewById(R.id.graph);

        if (infoArray != null) {

            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(getDataPoint());

            graph.addSeries(series);

            graph.getViewport().setMinX(0);
            graph.getViewport().setMaxX(24);
            graph.getViewport().setMinY(0);
            graph.getViewport().setMaxY(20);

            graph.getGridLabelRenderer().setHorizontalAxisTitle("Last 24 Hours");
            graph.getGridLabelRenderer().setVerticalAxisTitle("Number of People");

            graph.getViewport().setYAxisBoundsManual(true);
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);

            if (infoArray[0] == 1) {
                TextView textsum = (TextView) findViewById(R.id.historytitle);
                textsum.setText("History of Lab Vacancy: Duda Hall");
            }

            // axis maker
            int currentTime = infoArray[53];
            int sixTime;
            int twelveTime;
            int eighteenTime;
            int twentyFourTime;

//            if (currentTime > 12) {
//                current = (String.valueOf(currentTime - 12) + " p.m.");
//            }
//            else {
//                current = (String.valueOf(currentTime) + " a.m.");
//            }

//            if (currentTime - 6 > 12) {
//                sixTime = (currentTime - 6) + Math.abs(currentTime - 12);
//                six = (String.valueOf(currentTime) + " p.m.");
//            }
//            else {
//                six = (String.valueOf(currentTime) + " a.m.");
//            }

            // string builder
            current = (String.valueOf(currentTime) + ":00");

            // six
            if (currentTime > 12) {
                sixTime = currentTime - 6;
            } else {
                sixTime = 24 - Math.abs(currentTime - 6);
            }
            six = Integer.toString(sixTime) + ":00";
            // twelve
            if (currentTime > 12) {
                twelveTime = currentTime - 12;
            } else {
                twelveTime = 24 - Math.abs(currentTime - 12);
            }
            twelve = Integer.toString(twelveTime) + ":00";
            // eighteen     - delete redundancy
            if (currentTime > 12) {
                eighteenTime = 24 - Math.abs(currentTime - 18);
            } else {
                eighteenTime = 24 - Math.abs(currentTime - 18);;
            }
            eighteen = Integer.toString(eighteenTime) + ":00";
            twentyFour = current;
            // twentyfour
//            if (currentTime < 12) {
//                SIXTime = currentTime - 24;
//            }
//            else {
//                sixTime = 24 - Math.abs(currentTime - 24);

            twentyFourTime = currentTime;

        }

        TextView currentView = (TextView) findViewById(R.id.nowTime);
        TextView sixView = (TextView) findViewById(R.id.sixTime);
        TextView twelveView = (TextView) findViewById(R.id.twelveTime);
        TextView eighteenView = (TextView) findViewById(R.id.eighteenTime);
        TextView twentyFourView = (TextView) findViewById(R.id.twentyFourTime);

        currentView.setText(current);
        sixView.setText(String.valueOf(six));
        twelveView.setText(String.valueOf(twelve));
        eighteenView.setText(String.valueOf(eighteen));
        twentyFourView.setText(String.valueOf(twentyFour));

        if (twentyFour == null) {
            sixView.setVisibility(View.INVISIBLE);
            twelveView.setVisibility(View.INVISIBLE);
            eighteenView.setVisibility(View.INVISIBLE);
            twentyFourView.setVisibility(View.INVISIBLE);
        }
        else{
            sixView.setVisibility(View.VISIBLE);
            twelveView.setVisibility(View.VISIBLE);
            eighteenView.setVisibility(View.VISIBLE);
            twentyFourView.setVisibility(View.VISIBLE);
        }



        // ideal time checker

//        int minArray[] = new int[]{infoArray[4], infoArray[6], infoArray[8],
//                infoArray[10], infoArray[12], infoArray[14], infoArray[16], infoArray[18],
//                infoArray[20], infoArray[22], infoArray[24]};
//
//        for (int i = 0; i < minArray.length; i++) {
//            for (int j = i + 1; j < minArray.length; j++) {
//                if (minArray[i] > minArray[j]) {
//                    temp = minArray[i];
//                    minArray[i] = minArray[j];
//                    minArray[j] = temp;
//                }
//            }
//        }
//        int min = minArray[0];




}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

        private DataPoint[] getDataPoint() {
        DataPoint [] dp = new DataPoint[] {
                new DataPoint (infoArray[3], infoArray[4]),
                new DataPoint (infoArray[5], infoArray[6]),
                new DataPoint (infoArray[7], infoArray[8]),
                new DataPoint (infoArray[9], infoArray[10]),
                new DataPoint (infoArray[11], infoArray[12]),
                new DataPoint (infoArray[13], infoArray[14]),
                new DataPoint (infoArray[15], infoArray[16]),
                new DataPoint (infoArray[17], infoArray[18]),
                new DataPoint (infoArray[19], infoArray[20]),
                new DataPoint (infoArray[21], infoArray[22]),
                new DataPoint (infoArray[23], infoArray[24]),
                new DataPoint (infoArray[25], infoArray[26]),
                new DataPoint (infoArray[27], infoArray[28]),
                new DataPoint (infoArray[29], infoArray[30]),
                new DataPoint (infoArray[31], infoArray[32]),
                new DataPoint (infoArray[33], infoArray[34]),
                new DataPoint (infoArray[35], infoArray[36]),
                new DataPoint (infoArray[37], infoArray[38]),
                new DataPoint (infoArray[39], infoArray[40]),
                new DataPoint (infoArray[41], infoArray[42]),
                new DataPoint (infoArray[43], infoArray[44]),
                new DataPoint (infoArray[45], infoArray[46]),
                new DataPoint (infoArray[47], infoArray[48]),
                new DataPoint (infoArray[49], infoArray[50]),
                new DataPoint (infoArray[51], infoArray[52]),
        };
        return(dp);
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