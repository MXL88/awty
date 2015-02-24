package edu.washington.mxl.awty;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;


public class MainActivity extends ActionBarActivity {
    //EditText message = (EditText) findViewById(R.id.editText2);
    final int MESSAGE_ID = R.id.editText2;
    final int NUMBER_ID = R.id.editText;
    final int NUM_NAGS_ID = R.id.editText3;
    private PendingIntent pendingIntent;
    private static int PERIOD = 60000; //or whatever you need for repeating alarm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText message = (EditText) findViewById(MESSAGE_ID);
        final EditText number = (EditText) findViewById(NUMBER_ID);
        final EditText numNags = (EditText) findViewById(NUM_NAGS_ID);

        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enableButton(message, number, numNags);
            }
        });

        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enableButton(message, number, numNags);
            }
        });

        numNags.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enableButton(message, number, numNags);
            }
        });

        /* Retrieve a PendingIntent that will perform a broadcast */
        //Intent alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);

        final Button start = (Button) findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alIntent, 0);
                //Toast.makeText(MainActivity.this, message.getText().toString(), Toast.LENGTH_SHORT).show();
                if (start.getText().toString() == "Stop") {
                    Intent alIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
                    PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, alIntent, 0);
                    mgr.cancel(pendingIntent);
//                    pendingIntent.cancel();
                    start.setText("Start");
                } else {
                    int nags = Integer.parseInt(numNags.getText().toString());
                    if (nags > 0 && nags == (int)nags) {
                        Log.i("onClick", "nagNum is valid");
                        start.setText("Stop");
                        String mess = number.getText().toString() + ": " + message.getText().toString();
                        Log.i("onClick", mess);
                        Intent alIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
                        alIntent.putExtra("mess", mess);
                        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alIntent, 0);
                        mgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                SystemClock.elapsedRealtime() + 3000, nags * PERIOD, pendingIntent);
                    }
                }
            }
        });
    }

    private void enableButton(EditText message, EditText number, EditText numNags) {
        String mess = message.getText().toString();
        String num = number.getText().toString();
        String nags = numNags.getText().toString();

        if(!mess.matches("") && !num.matches("") && !nags.matches("")) {
            Button start = (Button) findViewById(R.id.start);
            start.setEnabled(true);
        }
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

