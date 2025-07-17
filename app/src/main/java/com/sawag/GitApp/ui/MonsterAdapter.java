package com.sawag.gitapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.sawag.gitapp.R;
import com.sawag.gitapp.model.Monster;

import java.util.ArrayList;
import java.util.List;

public class MonsterAdapter extends RecyclerView.Adapter<MonsterAdapter.MonsterViewHolder> {

    private List<Monster> monsters = new ArrayList<>();
    private Context context;

    public MonsterAdapter(Context context) {
        this.context = context;
    }

    public void setMonsters(List<Monster> monsters) {
        this.monsters = monsters;
        notifyDataSetChanged(); // For simplicity, consider DiffUtil for better performance
    }

    @NonNull
    @Override
    public MonsterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_monster, parent, false);
        return new MonsterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MonsterViewHolder holder, int position) {
        Monster currentMonster = monsters.get(position);
        holder.textViewMonsterName.setText(currentMonster.getName());

        Glide.with(context)
                .load(currentMonster.getImageUrl())
                .placeholder(R.drawable.ic_launcher_background) // Optional placeholder
                .error(R.drawable.ic_launcher_foreground) // Optional error image
                .into(holder.imageViewMonster);
    }

    @Override
    public int getItemCount() {
        return monsters.size();
    }

    static class MonsterViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewMonster;
        TextView textViewMonsterName;
        TextView textViewMonsterType;

        public MonsterViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewMonster = itemView.findViewById(R.id.imageViewMonster);
            textViewMonsterName = itemView.findViewById(R.id.textViewMonsterName);
        }
    }
}
