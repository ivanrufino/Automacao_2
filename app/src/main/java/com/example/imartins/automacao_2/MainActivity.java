package com.example.imartins.automacao_2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    List<String> opcoes;
    ArrayAdapter<String> adaptador;
    ListView lvOpcoes;
    listaAdapter adapter;
    MeuMenu meumenu ;//= new MeuMenu();
    static final String KEY_TITLE = "title";
    static final String KEY_THUMB_URL = "thumb_url";
    private final String KEY_ID = "id";
    static final String KEY_SUBTITLE = "subtitle";
    static boolean status = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        meumenu = new MeuMenu(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if(meumenu.verificaConexao()){
            Log.d("wifi","Ativo");

        }else{
            Log.d("wifi","Inativo");

            Snackbar.make(getWindow().getDecorView().getRootView(), "Você precisa de uma conexão com internet para utilizar este app", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        try {
            createMenu();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public void createMenu() throws JSONException {
        Intent secondActivity ;

        ArrayList<HashMap<String, String>> actionsList = new ArrayList<HashMap<String, String>>();
        String options = "[{'id':'1','title':'Acionadores','subtitle':'Lampadas e Tomadas','image':"+R.drawable.acionador+"},{'id':'2','title':'Alarmes','subtitle':'Alarmes e sensores','image':"+R.drawable.alarme+"},{'id':'3','title':'Sair','subtitle':'Sair do Sistema','image':"+R.drawable.sair+"}]";
        JSONArray array = new JSONArray(options);
        JSONObject obj;

        for(int i = 0; i < array.length(); ++i) {
            obj = array.getJSONObject(i);
            HashMap<String, String> map = new HashMap<String, String>();

            map.put(KEY_ID, obj.getString("id"));
            map.put(KEY_TITLE, obj.getString("title"));
            map.put(KEY_SUBTITLE, obj.getString("subtitle"));

            map.put(KEY_THUMB_URL,  obj.getString("image"));
            actionsList.add(map);
        }






        lvOpcoes=(ListView)findViewById(R.id.lvopcoes);

        // Getting adapter by passing xml data ArrayList
        adapter=new listaAdapter(this, actionsList);
        lvOpcoes.setAdapter(adapter);

        // Click event for single list row
        lvOpcoes.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent secondActivity ;
                switch (position){
                    case 0:
                        secondActivity = new Intent(MainActivity.this, AcionadorActivity.class);

                        startActivity(secondActivity);
                        break;
                    case 1:
                        Log.d("teste","Clicado no segund link");
                        break;

                    case 2:
                        finish();
                        break;
                }

            }
        });
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        meumenu.selectItem(item,this,this.status);
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        meumenu.selectItem(item,this,this.status);

        return true;
    }
    @Override
    public void onStop() {
        super.onStop();
        this.status = false;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.status = true;
    }
}
