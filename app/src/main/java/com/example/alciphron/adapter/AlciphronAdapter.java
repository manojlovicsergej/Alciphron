package com.example.alciphron.adapter;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alciphron.R;
import com.example.alciphron.modelalciphron.AlciphronModel;

import java.text.SimpleDateFormat;
import java.util.List;

public class AlciphronAdapter extends RecyclerView.Adapter<AlciphronAdapter.ViewHolder> {

    List<AlciphronModel> alciphronModelList;
    private ItemClickListener itemClickListener;

    public AlciphronAdapter(List<AlciphronModel> list,ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
        this.alciphronModelList = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name;
        private TextView description;
        private TextView finder;
        private TextView date;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            finder = itemView.findViewById(R.id.finder);
            date = itemView.findViewById(R.id.date);


        }

    }

    @NonNull
    @Override
    public AlciphronAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.single_alciphron_item,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlciphronAdapter.ViewHolder holder, int position) {

        holder.name.setText(alciphronModelList.get(position).getName());
        holder.description.setText(alciphronModelList.get(position).getDescription());
        holder.finder.setText(alciphronModelList.get(position).getFinder());
        holder.date.setText(alciphronModelList.get(position).getDate());

        holder.itemView.setOnClickListener(view -> {
            itemClickListener.onItemClickListener(alciphronModelList.get(position));
        });


    }

    @Override
    public int getItemCount() {
        return alciphronModelList.size();
    }

    public interface ItemClickListener{
        void onItemClickListener(AlciphronModel alciphronModel);
    }

}
