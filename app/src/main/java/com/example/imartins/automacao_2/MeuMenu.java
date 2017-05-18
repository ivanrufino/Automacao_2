package com.example.imartins.automacao_2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;

/**
 * Created by imartins on 11/05/17.
 */

public class MeuMenu {
    Context mContext;

    public MeuMenu(Context mContext){
        this.mContext=mContext;
    }

    public  boolean verificaConexao() {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }

    @SuppressWarnings("StatementWithEmptyBody")
   // @Override
    public boolean selectItem(MenuItem item,Activity activity,Boolean status) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent secondActivity;
        switch (id){
            case R.id.Acionadores:
                secondActivity = new Intent(mContext, AcionadorActivity.class);
                activity.startActivity(secondActivity);
                break;
            case R.id.nav_home:
                secondActivity = new Intent(mContext, MainActivity.class);
                activity.startActivity(secondActivity);
                break;
            case R.id.action_settings:
            case R.id.nav_manage:
                secondActivity = new Intent(mContext, ConfigActivity.class);
                activity.startActivity(secondActivity);
                break;
            case R.id.sair:
                activity.finish();
                break;
            default:
        }

       /* if (id == R.id.nav_home ) {
            secondActivity = new Intent(activity, MainActivity.class);
            activity.startActivity(secondActivity);
        } else if (id == R.id.nav_gallery ) {

        } else if (id == R.id.nav_slideshow ) {

        } else if (id == R.id.nav_manage ) {
            secondActivity = new Intent(activity, ConfigActivity.class);
            activity.startActivity(secondActivity);
        } else if (id == R.id.nav_share ) {

        } else if (id == R.id.sair) {
            activity.finish();
        }else if (id == R.id.action_settings) {
            secondActivity = new Intent(activity, ConfigActivity.class);
            activity.startActivity(secondActivity);

        }*/


        DrawerLayout drawer = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
