package com.sawag.gitapp.model;

import com.google.gson.annotations.SerializedName;

public class Monster {
    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("hp")
    private int hp; // Statsクラスから移動

    @SerializedName("image_url") // JSONのキーに合わせる
    private String imageUrl;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() { // ログ出力やデバッグ用
        return "Monster{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hp=" + hp +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
