package com.example.imartins.automacao_2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
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
import java.util.HashMap;
import java.util.Map;

public class AcionadorActivity extends AppCompatActivity {
    SharedPreferences sharedPref;
    ListView lvOpcoes;
    listaAdapter adapter;
    String ret = null;
    Boolean[] estado = {null};
 //   Boolean estado_atual = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acionador);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //final CoordinatorLayout coordinatorLayout = new (CoordinatorLayout) findViewById(R.id.CLAcionador);
        sharedPref = getApplicationContext().getSharedPreferences("AUTOMACAO",Context.MODE_PRIVATE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        //getSupportActionBar().setTitle("Seu titulo aqui");

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Replace with your own action",Snackbar.LENGTH_LONG)
                        .setAction("Action",null).show();
            }
        });*/
        try {

            getAcionadores();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this,MainActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }
    @Override
    public void onBackPressed(){ //Botão BACK padrão do android
        startActivity(new Intent(this, MainActivity.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        finishAffinity(); //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }
    public void getAcionadores() throws JSONException {
        Intent secondActivity ;

        ArrayList<HashMap<String, String>> actionsList = new ArrayList<HashMap<String, String>>();
        //sharedPref = getSharedPreferences("com.example.imartins.automocao_2",MODE_PRIVATE);
        String rels = sharedPref.getString("reles","");
        int quant_rele = Integer.parseInt(sharedPref.getString("qtd_rele","1"));

        JSONArray array = new JSONArray(rels);
        JSONObject obj;

        for(int i = 0; i < quant_rele; ++i) {

                obj = array.getJSONObject(i);
                HashMap<String, String> map = new HashMap<String, String>();


                map.put("id",obj.getString("id"));
                map.put("title",obj.getString("alias"));
                map.put("subtitle",obj.getString("nome"));
                if(obj.getString("alias").equals("")) {
                    map.put("title",obj.getString("nome"));
                }

                map.put("thumb_url",String.valueOf(R.drawable.acionador2));
                actionsList.add(map);


        }
        lvOpcoes=(ListView)findViewById(R.id.lvacionadores);

        // Getting adapter by passing xml data ArrayList
        adapter=new listaAdapter(this, actionsList);
        lvOpcoes.setAdapter(adapter);

        // Click event for single list row
        lvOpcoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView,View view,int position,long id) {


         //       view.setBackgroundResource(R.color.colorInactive);

                Intent secondActivity ;
                ImageView  thumb_image = (ImageView) ((listaAdapter.ViewHolder)view.getTag()).thumb_image;
                if(view.isEnabled()){
                    thumb_image.setImageResource(R.drawable.acionador);
                    view.setBackgroundResource(R.color.colorActive);
                }else{
                    //thumb_image.setImageResource(R.drawable.acionador2);
                    view.setBackgroundResource(R.color.colorInactive);
                }


                chamarAcionador(id, view);;
               // String Retorno =


                switch (position){
                    case 0:

                        break;
                    case 1:
                      //  Log.d("teste","Clicado no segund link");
                        break;

                    case 2:
                      //-+  finish();
                        break;
                }

            }
        });
    }

    public void chamarAcionador(Long rele,final View view){
        RequestQueue queue = Volley.newRequestQueue(this);
        Bundle extras = getIntent().getExtras();
        String ip_arduino = sharedPref.getString("ip_arduino","");
        String url_estado =String.valueOf(!view.isEnabled());
        String url = "http://"+ip_arduino+"/arduino.php?rele="+ String.valueOf(rele)+"&estado="+ url_estado;

        final Activity context = this;

        final ProgressDialog progress = new ProgressDialog(context);
        progress.setMessage("Aguarde, por favor...");
        progress.setCancelable(false);
        progress.show();

        myWebServiceFun(url,view);

        progress.dismiss();
      //  Log.d("estadoatual2",String.valueOf(estadoAtual));

       /* StringRequest request = new StringRequest
                (Request.Method.GET, url, new Response.Listener<String>() {

                    public boolean estado_atual;

                    @Override
                    public void onResponse(String response) {
                        Log.d("RESPOSTA", response);
                        onSuccessResponse(response);
                        try {
                            JSONArray arrResponse = new JSONArray(response);
                            for(int i = 0; i < arrResponse.length();i++){
                                JSONObject json = (JSONObject)arrResponse.get(i);
                               Log.d("retorno",String.valueOf(json.getBoolean("estado")));

                                Snackbar.make(view, json.getString("msg"),Snackbar.LENGTH_LONG)
                                        .setAction("Action",null).show();
                                Log.d("estadoatual",String.valueOf(estado_atual));
                                this.estado_atual = true;
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        progress.dismiss();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String err = (error.getMessage()==null)?"Não foi possivel estabeler uma conexão com o ip informado":error.getMessage();
                        Snackbar.make(view, err,Snackbar.LENGTH_LONG)
                                .setAction("Action",null).show();
                        Log.d("ERRO",err);
                        progress.dismiss();
                    }
                    }
                );

        request.setRetryPolicy(new DefaultRetryPolicy(
                1000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(request);*/


        return ;
    }
    public interface VolleyCallback {
        void onSuccessResponse(String result);
    }
    public void getResponse(int method, String url, JSONObject jsonValue, final VolleyCallback callback) {
        final Context mCtx = this;

        RequestQueue queue = MySingleton.getInstance(mCtx).getRequestQueue();

        StringRequest strreq = new StringRequest(Request.Method.GET, url, new Response.Listener < String > () {

            @Override
            public void onResponse(String Response) {
                callback.onSuccessResponse(Response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                Toast.makeText(mCtx, e + "error", Toast.LENGTH_LONG).show();
            }
        })
        {
            // set headers
            @Override
            public Map< String, String > getHeaders() throws com.android.volley.AuthFailureError {
                Map < String, String > params = new HashMap < String, String > ();
                params.put("Authorization: Basic", "TOKEN");
                return params;
            }
        };
        MySingleton.getInstance(mCtx).addToRequestQueue(strreq);
    }

    public void myWebServiceFun(String url,final View view) {
    Log.d("URL",url);

        getResponse(Request.Method.GET, url, null,

                new VolleyCallback() {


                    @Override
                    public void onSuccessResponse(String result) {
                        try {
                            JSONArray arrResponse = new JSONArray(result);
                            for(int i = 0; i < arrResponse.length();i++){
                                JSONObject json = (JSONObject)arrResponse.get(i);
                                Log.d("retorno",String.valueOf(json.getBoolean("estado")));

                               /* Snackbar.make(view, json.getString("msg"),Snackbar.LENGTH_LONG)
                                        .setAction("Action",null).show();*/

                             //   ImageView  thumb_image = (ImageView) view.findViewById(R.id.list_image);
                             //  ImageView  thumb_image = (ImageView) ((listaAdapter.ViewHolder)view.getTag()).thumb_image;
                                view.setEnabled(json.getBoolean("estado"));
                                /*if(json.getBoolean("estado")){
                                    thumb_image.setImageResource(R.drawable.acionador);
                                    view.setBackgroundResource(R.color.colorActive);
                                }else{
                                    //thumb_image.setImageResource(R.drawable.acionador2);
                                    view.setBackgroundResource(R.color.colorInactive);
                                }*/



                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

}
