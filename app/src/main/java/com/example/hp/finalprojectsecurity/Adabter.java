package com.example.hp.finalprojectsecurity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;



public class Adabter extends BaseAdapter {
    private Context context;
    private ArrayList<User> arrayList ;

    public Adabter(Context applicationContext, ArrayList<User> msgs) {
        this.context=applicationContext;
        this.arrayList=msgs;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private static class ViewHolder{
        TextView name,phoone,password;

    }
    ViewHolder viewHolder = null;


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.item,null);

            viewHolder = new ViewHolder();
            viewHolder.name =  view.findViewById(R.id.name);
            viewHolder.phoone = view.findViewById(R.id.phoone);
            viewHolder.password = view.findViewById(R.id.passwrod_item);

            view.setTag(viewHolder);
        }else {

            /* We recycle a View that already exists */
            viewHolder= (ViewHolder) view.getTag();
        }

        viewHolder.name.setText(arrayList.get(i).getName());
        viewHolder.phoone.setText(arrayList.get(i).getPhoone());
        viewHolder.password.setText(arrayList.get(i).getPassword());



        return view;
    }
}

