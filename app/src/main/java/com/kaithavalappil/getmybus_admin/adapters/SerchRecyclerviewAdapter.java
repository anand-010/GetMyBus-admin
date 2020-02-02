package com.kaithavalappil.getmybus_admin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kaithavalappil.getmybus_admin.DataIntermediate.SerchRecyclerLData;
import com.kaithavalappil.getmybus_admin.R;

import java.util.List;

public class SerchRecyclerviewAdapter extends RecyclerView.Adapter<SerchRecyclerviewAdapter.ViewHolder> {
    List<SerchRecyclerLData> data;
    Context context;
    public SerchRecyclerviewAdapter(Context context, List<SerchRecyclerLData> serchRecyclerLData) {
        this.data = serchRecyclerLData;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.serch_ride_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(data.get(position).getRouteName());
        holder.from.setText(data.get(position).getFrom());
        holder.to.setText(data.get(position).getTo());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView name,from,to;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.route_name);
            from = itemView.findViewById(R.id.route_start);
            to = itemView.findViewById(R.id.route_end);
        }
    }
}
