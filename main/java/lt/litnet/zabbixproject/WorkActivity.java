package lt.litnet.zabbixproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.HttpException;
//import org.apache.commons.httpclient.methods.PutMethod;
import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

public class WorkActivity extends AsyncTask<Object, String, String>  {
    private static final String TAG = WorkActivity.class.getSimpleName();

    //private static String ZABBIX_API_URL = "https://watch.litnet.lt/api_jsonrpc.php";

    HttpsURLConnection urlConnection;

    private ProgressDialog dialog;


    @Override
    protected void onPreExecute() {
        dialog.setMessage("Please wait...");
        dialog.show();
        Log.d(TAG, "onPreExecute() working");
    }


    public WorkActivity(Activity activity) {
        dialog = new ProgressDialog(activity);
    }


    @Override
    protected String doInBackground(Object... params) {
        Log.d(TAG, "doInBackground() started");

        StringBuilder result = new StringBuilder();

        try {
            Log.d(TAG, "doInBackground() trying");
            URL url = new URL(ZabbixParams.getZabbix_url());

            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(20 * 1000);
            urlConnection.setReadTimeout(20 * 1000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json-rpc");

            JSONObject jsonParam = new JSONObject();
            jsonParam.put("jsonrpc", "2.0");
            //jsonParam.put("method", "apiinfo.version");
            jsonParam.put("method", ZabbixParams.getMethod());
            jsonParam.put("id", "1");

            JSONObject object = new JSONObject();
                   if (ZabbixParams.getAuth() == false) {

                       //    jsonParam.put("auth", "");
                       //    jsonParam.put("params", new JSONArray());

                       //          JSONArray array = new JSONArray();

                       if (ZabbixParams.getUsername() != "" && ZabbixParams.getPassword() != "") {
                           object.put("password", ZabbixParams.getPassword());
                           object.put("user", ZabbixParams.getUsername());
                           //          array.put(object);
                           //          array.put(object);
                           //      array.put(object.put("user", ZabbixParams.getUsername()));
                           //      array.put(object.put("password", ZabbixParams.getPassword()));

                       }
                   } else {
                       String token = ZabbixParams.getToken();
                       jsonParam.put("auth",token);
                       object=ZabbixParams.getParams();
                   }
                       Log.d(TAG, "JSON contents: " + jsonParam.put("params", object));

                       //     jsonParam.toString();

                       // jsonParam.put("params", array);

                       BufferedWriter out =
                               new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
                       out.write(jsonParam.toString());
                       out.close();

                       Log.d(TAG, "doInBackground() response code= " + urlConnection.getResponseCode());
                       Log.d(TAG, "url = " + url);

                       if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                           Log.d(TAG, "doInBackground() OK");

                           InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                           BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                           String line;
                           Log.d(TAG, "doInBackground() Before while");
                           while ((line = reader.readLine()) != null) {
                               result.append(line);
                           }
                       }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }



        Log.d(TAG, "rezultatas = "+result.toString());

        return result.toString();

    }

    @Override
    protected void onPostExecute(String result) {

        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        //Do something with the JSON string

    }

}