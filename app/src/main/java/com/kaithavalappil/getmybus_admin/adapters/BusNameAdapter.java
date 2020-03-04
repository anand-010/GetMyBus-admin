package com.kaithavalappil.getmybus_admin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.GeoPoint;
import com.kaithavalappil.getmybus_admin.R;

import java.util.ArrayList;
import java.util.List;

public class BusNameAdapter extends RecyclerView.Adapter<BusNameAdapter.ViewHolderf> {
    Context context;
    ViewHolderf viewHolder;
    int stops_no;
    public BusNameAdapter(Context context, int stops_no){
        this.context = context;
        this.stops_no = stops_no;
    }
    @NonNull
    @Override
    public ViewHolderf onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.busnameitem,parent,false);
        return new ViewHolderf(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderf holder, int position) {
        holder.count.setText(String.valueOf(position));
        this.viewHolder = holder;
    }
    @Override
    public int getItemCount() {
        return stops_no;
    }

    class ViewHolderf extends RecyclerView.ViewHolder{
        TextView count;
        EditText name;
        public ViewHolderf(@NonNull View itemView) {
            super(itemView);
            count = itemView.findViewById(R.id.Bus_name_count);
            name = itemView.findViewById(R.id.editTextStop);
        }
    }
}
