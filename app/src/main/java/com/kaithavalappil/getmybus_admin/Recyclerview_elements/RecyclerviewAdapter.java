package com.kaithavalappil.getmybus_admin.Recyclerview_elements;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.kaithavalappil.getmybus_admin.R;
import com.kaithavalappil.getmybus_admin.views.Timeline;
import java.util.ArrayList;
import java.util.List;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.ViewHolder> {
    List<TimelineData> datas = new ArrayList<>();

    public RecyclerviewAdapter(List<TimelineData> datas) {
        this.datas = datas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.timeline_viewer , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.timeline_position.setHight(datas.get(position).getTimeline_pos());
        holder.timeline_position.setState(datas.get(position).getTimeline_state());
        holder.places.setText(datas.get(position).getLocation());
        holder.time_view.setText(datas.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView places,time_view;
        Timeline timeline_position;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time_view = itemView.findViewById(R.id.time_view);
            timeline_position = itemView.findViewById(R.id.timeline);
            places = itemView.findViewById(R.id.Place);

        }
    }
}
