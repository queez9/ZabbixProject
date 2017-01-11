package lt.litnet.zabbixproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.security.Timestamp;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

public class DashboardActivity extends AppCompatActivity {

    private static final String TAG = WorkActivity.class.getSimpleName();
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ZabbixParams params=(ZabbixParams) getIntent().getSerializableExtra("params");

        params.setAuth(true);

//        Log.d(TAG, "DashboardActivity token ="+token);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String result;

                params.setMethod("trigger.get");
                JSONObject settings = new JSONObject();
                JSONArray output = new JSONArray();
                JSONObject filter = new JSONObject();

                output.put("triggerid");
                output.put("description");
                output.put("priority");

                try {
                    settings.put("output",output);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    settings.put("filter",filter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    settings.put("sortfield","priority");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    settings.put("sortorder","DESC");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    settings.put("min_severity","3");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    settings.put("group","Linux servers - Litnet");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    settings.put("sortfield","lastchange");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                params.setParams(settings);


                WorkActivity task = new WorkActivity(DashboardActivity.this);
                try {
                    result = task.execute(params).get();

                    JSONObject obj = new JSONObject(result);
                    String output1 = obj.getString("result");

                    JSONArray output2 = new JSONArray(output1);;


                    //String output = obj.getString(value);

                    TextView scrolltext = (TextView)findViewById(R.id.scrolltext);
                    //scrolltext.setText("KAZKAS SUVEIKE");
                    //result = result.substring(0, Math.min(result.length(), 1000));

                    scrolltext.setText("");

                    String severity;
                    String description;
                    String time;
                    int size;

                    if (output2.length() > 10){
                        size = 10;
                    } else {
                        size = output2.length();
                    }


                    for (int i = 0; i < size; i++) {
                        JSONObject row = output2.getJSONObject(i);
                        //id = row.getString("");
                        severity = row.getString("priority");
                        description = row.getString("description");
                        time = row.getString("lastchange");
                        //String timeString = String.format("%02d",time);
                       // int timeSeconds = Integer.parseInt(time);
                      //  Date date = new Date(Integer.parseInt(time));

                        scrolltext.append(description+" "+severity+" "+time+"\n");

                    }



                    //scrolltext.setText(result);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }






//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

            }
        });
    }
}
