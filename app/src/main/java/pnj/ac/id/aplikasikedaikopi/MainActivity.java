package pnj.ac.id.aplikasikedaikopi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pnj.ac.id.aplikasikedaikopi.adapter.KopiAdapter;
import pnj.ac.id.aplikasikedaikopi.model.KopiModel;

public class MainActivity extends AppCompatActivity {

    ProgressDialog loading;
    GridView gridView;
    KopiAdapter adapter;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = findViewById(R.id.spinerJenis);
        gridView = findViewById(R.id.gridView);
        adapter = new KopiAdapter(this, R.layout.item_layout);
        loading = new ProgressDialog(this);
        loading.setMessage("Loading..");
        gridView.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("Terselect : ", adapterView.getAdapter().getItem(i).toString());

                getData(adapterView.getAdapter().getItem(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    void getData(String jenis) {
        String type;
        if (jenis.equalsIgnoreCase("Kopi Panas")) {
            type = "hot";
        } else {
            type = "iced";
        }
        loading.show();


        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.sampleapis.com/coffee/"+type;
        Log.e("URL :", url);
        adapter.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Log.e("Response : ",response);
                        ArrayList<KopiModel> data = new ArrayList<>();
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for(int i=0; i<jsonArray.length(); i++) {
                                KopiModel model = new KopiModel();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                model.setId(jsonObject.getString("id"));
                                model.setDescription(jsonObject.getString("description"));
                                model.setImage(jsonObject.getString("image"));
                                model.setTitle(jsonObject.getString("title"));
                                data.add(model);

                            }

                            adapter.addAll(data);
                            adapter.notifyDataSetChanged();


                        }catch (JSONException ex) {

                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                        loading.dismiss();
                        Log.e("Response : ", ""+error.getMessage());
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);


    }
}