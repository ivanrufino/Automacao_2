package com.example.imartins.automacao_2;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by imartins on 29/11/16.
 */
public class ReleFragment extends Fragment {
    Button btnShowLocation;
    LinearLayout formLL;
    SharedPreferences sharedPref;
    private Acionador rl;
    ProgressDialog progress ;
    private List<Acionador> reles = new ArrayList<Acionador>();

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;



    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public ReleFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ReleFragment newInstance(int sectionNumber) {
        ReleFragment fragment = new ReleFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);


        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_rele, container, false);
        sharedPref = getActivity().getSharedPreferences("AUTOMACAO",Context.MODE_PRIVATE);

        // TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        try {
            loadConf(rootView);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Button salvarConf = (Button) rootView.findViewById(R.id.salvar_reles);
        salvarConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarConf();
            }
        });
        formLL = (LinearLayout) rootView.findViewById(R.id.reles_list);
        hideLayoutNoUsed();
        return rootView;
    }
    public void loadConf(View R) throws JSONException {
        String rels = sharedPref.getString("reles","");

        JSONArray array = new JSONArray(rels);
        JSONObject rele;

        for(int i = 0; i < array.length(); ++i) {
            rele = array.getJSONObject(i);
            TextView rel = (TextView) R.findViewById(rele.getInt("id"));
            rel.setText(rele.getString("alias"));
            Log.d("asda",String.valueOf(rele.getInt("id")));
        }

    }
    public void hideLayoutNoUsed(){
        int qtd_rele = Integer.parseInt(sharedPref.getString("qtd_rele","1"));
        for (int i = qtd_rele; i < formLL.getChildCount()-1; i++) {
            LinearLayout subFrm = (LinearLayout) formLL.getChildAt(i);
            subFrm.setVisibility(LinearLayout.GONE);

        }

    }
    public void salvarConf(){
        progress =new ProgressDialog(getActivity());
        progress.setMessage("Salvando alterações");
        progress.setCancelable(false);
        progress.show();

        SharedPreferences.Editor editor = sharedPref.edit();

        JSONArray array = new JSONArray();
        JSONObject obj ;


        for (int i = 0; i < formLL.getChildCount()-1; i++) {
            obj = new JSONObject();
            rl=new Acionador();
            LinearLayout subFrm = (LinearLayout) formLL.getChildAt(i);

            Log.d("count", String.valueOf(i));

            View nome = subFrm.getChildAt(0);
            View alias = subFrm.getChildAt(1);
       //     View releon = subFrm.getChildAt(2);
            if (nome instanceof TextView) {
                TextView rele_tv = (TextView) nome;
                 rl.setNome(rele_tv.getText().toString());
                 Log.d("rels", rele_tv.getText().toString());




            }
            if (alias instanceof EditText) {
                EditText rele_ev = (EditText) alias;
                rl.setAlias(rele_ev.getText().toString());
                rl.setId(alias.getId());
                Log.d("rels", rele_ev.getText().toString());
            }

          /*  if (releon instanceof CheckBox) {
                CheckBox rele_tv = (CheckBox) releon;
                rl.setRele_on(rele_tv.isChecked());
                Log.d("rels", String.valueOf(rele_tv.isChecked()));
            }*/
            try {
                obj.put("nome", rl.getNome());
                obj.put("alias",rl.getAlias());
                obj.put("id",rl.getId());
                obj.put("rele_on",rl.getRele_on());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            array.put(obj);
            reles.add(rl);
        }

        String arrayReles = array.toString();


        editor.putString("reles",arrayReles);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                progress.dismiss();
            }
        }, 1000);
        editor.commit();

        Log.d("aqui",arrayReles);
        String rels = sharedPref.getString("reles","");// getInt(getString(R.string.saved_high_score), defaultValue);
        Log.d("Rels_json",rels);
        Log.d("teste","teste de click");

       /* try {
            loadConf();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }

}

