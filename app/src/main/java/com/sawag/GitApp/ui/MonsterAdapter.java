package com.sawag.gitapp.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .listener(new RequestListener<Drawable>() { // RequestListenerの実装
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        // 画像の読み込みに失敗した場合の処理
                        Log.e("GlideError", "Load failed for " + model + ": " + (e != null ? e.getMessage() : "Unknown error"), e);
                        // e.logRootCauses("GlideError"); // GlideExceptionの詳細な原因をログに出力 [2]
                        return false; // falseを返すと .error() で指定したプレースホルダーが表示される
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        // 画像の読み込みに成功した場合の処理
                        Log.d("GlideSuccess", "Resource ready for " + model);
                        return false; // falseを返すと通常通り画像が表示される
                    }
                })
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
