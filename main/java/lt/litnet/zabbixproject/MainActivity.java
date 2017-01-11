package lt.litnet.zabbixproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = WorkActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final boolean auth = false;

        final Button button = (Button) findViewById(R.id.buttonid);
        final Context context = this;

        //ZabbixParams params = new ZabbixParams("URL",false,"","","apiinfo.version");
        // Sužinome zabbix versiją
/**        WorkActivity task = new WorkActivity(MainActivity.this);
        try {
            String  result = task.execute(params).get();

            String version = getParams(result,"result");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
 **/

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText uri = (EditText)findViewById(R.id.zabbix_uri);
                String zabbix_uri      =  uri.getText().toString()+"/api_jsonrpc.php";
               // Log.d(TAG, "zabbix_uri ="+zabbix_uri);
                EditText username = (EditText)findViewById(R.id.username);
                String zabbix_username      =  username.getText().toString();
               // Log.d(TAG, "zabbix_username ="+zabbix_username);

                TextView password = (EditText)findViewById(R.id.password);
                String zabbix_password      =  password.getText().toString();

               // Log.d(TAG, "zabbix_password ="+zabbix_password);

                //Sužinome zabbix versiją
                //ZabbixParams params = new ZabbixParams("https://watch.litnet.lt/api_jsonrpc.php",false,"","","apiinfo.version");
                ZabbixParams params = new ZabbixParams(zabbix_uri,false,"","","apiinfo.version");
                WorkActivity checkversion = new WorkActivity(MainActivity.this);
                try {
                    String  result = checkversion.execute(params).get();
                    String jsonversion = getParams(result,"jsonrpc");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //EditText password = (EditText)findViewById(R.id.password);
                //String zabbix_password      =  password.getText().toString();

                //ZabbixParams params = new ZabbixParams("URL", false, "", "", "user.login");
                params = new ZabbixParams(zabbix_uri, false, zabbix_username, zabbix_password, "user.login");
                String result = null;
                try {
                    WorkActivity task = new WorkActivity(MainActivity.this);
                    result = task.execute(params).get();
                    // Gauname auth token
                    String token = getParams(result, "result");
                    params.setToken(token);
                    if (token != null) {
                        Intent myIntent = new Intent(MainActivity.this, DashboardActivity.class);
                        myIntent.putExtra("params", (Serializable) params);
                        MainActivity.this.startActivity(myIntent);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {

                    try {
                        Alert(getParamsArray(result, "error", "data"),getParamsArray(result, "error", "message"),context);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }


                    /**
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);

                    try {
                        dlgAlert.setMessage(getParamsArray(result, "error", "data"));
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        dlgAlert.setTitle(getParamsArray(result, "error", "message"));
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();

                    dlgAlert.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                     **/

                }







/**
 WorkActivity task = new WorkActivity(MainActivity.this);
 try {
 String  result = task.execute(params).get();
 // Sužinome zabbix versiją
 String version = getParams(result,"result");

 } catch (InterruptedException e) {
 e.printStackTrace();
 } catch (ExecutionException e) {
 e.printStackTrace();
 } catch (JSONException e) {
 e.printStackTrace();
 }
 **/


                //String str_result= new JSONfunctions().execute("http://192.168.6.43/employees.php").get();
                //       new WorkActivity().execute("https://watch.litnet.lt/api_jsonrpc.php");
            }
        });
    }

    public String getParams(String result, String value) throws JSONException {
        JSONObject obj = new JSONObject(result);
        String output = obj.getString(value);
        Log.d(TAG, "result value "+output);
        return  output;
    }

    public String getParamsArray(String result, String value, String value2) throws JSONException {
        JSONObject obj = new JSONObject(result);
        String output = obj.getJSONObject(value).getString(value2);
        Log.d(TAG, "result value "+output);
        return  output;
    }

    public void  Alert (String message, String title, Context context){

        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(context);
        dlgAlert.setMessage(message);
        dlgAlert.setTitle(title);
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView password = (EditText)findViewById(R.id.password);
        password.setText("");
    }
}





