package com.example.imartins.automacao_2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by imartins on 29/11/16.
 */


public  class ConfigGeralFragment extends Fragment implements View.OnClickListener {
    SharedPreferences sharedPref;
    private Button btnClick;
    private TextView ip_arduino;
    private TextView qtd_rele;

    private Boolean Luz = false;
    ProgressDialog progress ;//= new ProgressDialog(getActivity());
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public ConfigGeralFragment() {

    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ConfigGeralFragment newInstance(int sectionNumber) {


        ConfigGeralFragment fragment = new ConfigGeralFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_configgeral, container, false);


        sharedPref = getActivity().getSharedPreferences("AUTOMACAO",Context.MODE_PRIVATE);
        btnClick = (Button) rootView.findViewById(R.id.salvar_configgeral);
        btnClick.setOnClickListener(this);
        ip_arduino = (TextView) rootView.findViewById(R.id.ip_arduino) ;
        ip_arduino.setText(sharedPref.getString("ip_arduino",""));


        qtd_rele = (TextView) rootView.findViewById(R.id.qtd_rele) ;
        qtd_rele.setText(sharedPref.getString("qtd_rele",""));
        //text = (EditText) rootView.findViewById(R.id.msgText) ;
        //msg = (TextView) rootView.findViewById(R.id.msgTextView) ;
        //btnClick = (Button) rootView.findViewById(R.id.enviarButton);
        //btnClick.setOnClickListener(this);

       // btnLuzSala = (ImageView) rootView.findViewById(R.id.luz_sala);
      //  btnLuzSala.setOnClickListener(this);
       // TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));


        return rootView;
    }

    @Override
    public void onClick(View view) {
        progress =new ProgressDialog(getActivity());
        if (view == btnClick){

            progress.setMessage("Salvando alterações");
            progress.setCancelable(false);
            progress.show();

            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("ip_arduino",ip_arduino.getText().toString());
            editor.putString("qtd_rele",qtd_rele.getText().toString());
            editor.commit();

            Log.d("click","Salvando");
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                   //getActivity().finish();
                   startActivity(getActivity().getIntent().setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NO_HISTORY));
                    progress.dismiss();
                }
            }, 1000);
        }

    }/*
    secondActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
    secondActivity = new Intent(activity,MainActivity.class);
                activity.startActivity(secondActivity);*/




}