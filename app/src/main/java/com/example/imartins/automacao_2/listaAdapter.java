package com.example.imartins.automacao_2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.imartins.automacao_2.MainActivity.*;
import static com.example.imartins.automacao_2.MainActivity.KEY_THUMB_URL;

/**
 * Created by imartins on 08/05/17.
 */

public class listaAdapter extends BaseAdapter {
    private Activity activity;

    private ArrayList<HashMap <String,String>> data;
    private List<String> tags;
    private List<Boolean> statuses;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader;

    public listaAdapter(Activity a, ArrayList<HashMap<String, String>> d) {
        activity = a;
        data=d;
        tags = new ArrayList<String>();
        int size = data.size();
        for (int i = 0; i < size; i++) {
            tags.add("tag");
        }

        statuses = new ArrayList<Boolean>();

        for (int i = 0; i < size; i++) {
            statuses.add(false);
        }
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view;
        ViewHolder holder;
        HashMap<String, String> list = new HashMap<String, String>();
        list = data.get(position);

        if( convertView == null) {
            view = LayoutInflater.from(activity)
                    .inflate(R.layout.minhalista, parent, false);
            holder = new ViewHolder(view,position);

            view.setTag(holder);
            view.setEnabled(false);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

    //    Aluno aluno = (Aluno) getItem(position);
        holder.title.setText(list.get("title"));
        holder.subtitle.setText(list.get("subtitle"));

        return view;
    }


    /*public View getView(final int position,View convertView,ViewGroup parent) {
        final ViewHolder viewHolder;
        View vi=convertView;
        HashMap<String, String> list = new HashMap<String, String>();
        list = data.get(position);
        if(convertView==null) {
            vi = inflater.inflate(R.layout.minhalista,null);

            viewHolder = new ViewHolder();



            viewHolder.thumb_image = (ImageView) vi.findViewById(R.id.list_image);
            viewHolder.title = (TextView) vi.findViewById(R.id.title);
            viewHolder.subtitle = (TextView) vi.findViewById(R.id.subtitle);

            //viewHolder.enabled = vi.isEnabled();

            viewHolder.position = position;

            final HashMap<String, String> finalList = list;
            viewHolder.thumb_image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //tags.add(position, "pressionado");
                            if (!statuses.get(position)) {
                                statuses.add(position,true) ;

                            }else{
                                statuses.add(position,false) ;

                            }
                            Log.d("status",String.valueOf(statuses.get(position)));
                           // viewHolder.thumb_image.setImageResource(R.drawable.ic_menu_home);
                            Log.d("press","Titulo pressionado");
                            Log.d("position",String.valueOf(position));
                           // viewHolder.title.setText("Pressionado");

                            //System.out.println("DOWNLOAD PRESSED");
                            //viewHolder.downloadImageButton.setTag("downloaded");
                            //tags.add(position, "downloaded");
                        }
                    });



            // Setting all values in listview

            imageLoader = new ImageLoader();
            //   AsyncTask<Object, Void, ArrayList> image = imageLoader.execute(song.get(MainActivity.KEY_THUMB_URL),thumb_image);


       //     Drawable d = Drawable.createFromPath(song.get(MainActivity.KEY_THUMB_URL));



            vi.setTag(viewHolder);

            vi.setEnabled(false);

        }else{
            viewHolder= (ViewHolder)vi.getTag();

        }

        viewHolder.title.setText(list.get(MainActivity.KEY_TITLE));
        viewHolder.subtitle.setText(list.get(MainActivity.KEY_SUBTITLE));
       // viewHolder.thumb_image.setImageResource(Integer.parseInt(list.get(MainActivity.KEY_THUMB_URL)));

       // viewHolder.catlogTitle.setTypeface(regularDin);

        if (statuses.get(position)) {
            viewHolder.thumb_image.setImageResource(R.drawable.ic_menu_home);
            //viewHolder.thumb_image.setImageResource(Integer.parseInt(list.get(MainActivity.KEY_THUMB_URL)));
        } else {
            viewHolder.thumb_image.setImageResource(Integer.parseInt(list.get(MainActivity.KEY_THUMB_URL)));
          //  downloadImageButton.setImageResource(R.drawable.icon_download);
        }


        viewHolder.position = position;

        return vi;
    }*/
    static class ViewHolder{
        ImageView thumb_image;
        TextView title;
        TextView subtitle;
        int position;
        boolean enabled;

        public ViewHolder(View view,int position) {
            title = (TextView) view.findViewById(R.id.title);
            subtitle = (TextView) view.findViewById(R.id.subtitle);
            thumb_image = (ImageView) view.findViewById(R.id.list_image);
            this.position = position;
        }
    }
}

