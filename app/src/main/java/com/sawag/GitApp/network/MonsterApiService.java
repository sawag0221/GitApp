package com.sawag.gitapp.network;

import com.sawag.gitapp.model.Monster;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface MonsterApiService {
    @GET("GitApp/monster_data.json")
    Call<List<Monster>> getMonsters(); // Retrofit Call for Java
}
