package com.mangata.eventocodescanner.webservicepost;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.RequestQueue;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText nro_tarjeta;
    private TextView textResult;
    //private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        nro_tarjeta = (EditText) findViewById(R.id.editText);
        textResult = (TextView) findViewById(R.id.textResult);
        //pDialog = new ProgressDialog(MainActivity.this);
        //pDialog.setMax(100);
        //pDialog.setMessage("Its loading....");
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                getInfoBD(nro_tarjeta.getText().toString());
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void getInfoBD(final String codigo){
        textResult.setText("entro getinfo");
        final String URL = "https://sigeva.ucsm.edu.pe:444/ReportesService/ReporteConsultaHorarios.svc/ObtenerFotoAlumno";
        //String JSONString = "{'codigoPersona':'765','seguridad':'ABCDE'}";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("codigoPersona", "765");
        params.put("seguridad", "ABCDE");
        JSONObject JSONParams = new JSONObject(params);
        Log.d("TAG","Buscando");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,URL, JSONParams,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //converting the string to json array object
                            JSONArray ObtenerFotoAlumnoResult = response.optJSONArray("ObtenerFotoAlumnoResult");
                            Log.d("TAG",response.toString());
                            Log.d("TAG",ObtenerFotoAlumnoResult.toString());
                            JSONObject Result = ObtenerFotoAlumnoResult.getJSONObject(0);
                            String codigoPersona = Result.get("codigoPersona").toString();
                            textResult.setText(codigoPersona);
                            Log.d("test: ",ObtenerFotoAlumnoResult.toString());
                            //hideDialog();
                            //String imagenPerson = "http://appfia.com//Views//assets//resources//fotos_acreditados//12345678.jpg";
                            //Picasso.with(getApplicationContext()).load(imagenPerson).into(imageView);
                            /*
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                productList.add(new Product(
                                        product.getInt("id"),
                                        product.getString("title"),
                                        product.getString("shortdesc"),
                                        product.getDouble("rating"),
                                        product.getDouble("price"),
                                        product.getString("image")
                                ));
                            }
                            */
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("TAG","Error JSON");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", error.getMessage(), error);
                    }
                }) { //no semicolon or coma
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("Content-Type", "application/json; charset=utf-8");
                        return params;
                    }
                };
        Log.d("TAG","Fin");
        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }
    public void getInfoBD2(final String codigo){
        final String URL_PRODUCTS = "http://appfia.com/Views/script_getPersonabyNroTarjeta.php?nro_tarjeta="+codigo;
        //showDialog();
        Log.d("TAG","Entro");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_PRODUCTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);
                            JSONObject Persona = array.getJSONObject(0);
                            String imagenPerson = Persona.getString("image");
                            textResult.setText(imagenPerson);
                            Log.d("TAG",response.toString());
                            //lblText.setText(array.toString());
                            //hideDialog();
                            //String imagenPerson = "http://appfia.com//Views//assets//resources//fotos_acreditados//12345678.jpg";
                            //Picasso.with(getApplicationContext()).load(imagenPerson).into(imageView);
                            /*
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);

                                //adding the product to product list
                                productList.add(new Product(
                                        product.getInt("id"),
                                        product.getString("title"),
                                        product.getString("shortdesc"),
                                        product.getDouble("rating"),
                                        product.getDouble("price"),
                                        product.getString("image")
                                ));
                            }
                            */
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("TAG","Error JSON");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG","Error Volley ");
                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }
    /*private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }*/
}
