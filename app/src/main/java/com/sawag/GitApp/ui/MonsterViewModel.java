package com.sawag.gitapp.ui;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sawag.gitapp.model.Monster;
import com.sawag.gitapp.network.RetrofitInstance;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MonsterViewModel extends ViewModel {

    private MutableLiveData<List<Monster>> monsters = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public LiveData<List<Monster>> getMonsters() {
        return monsters;
    }

    public LiveData<String> getError() {
        return error;
    }

    public MonsterViewModel() { // Constructor or an init method
        fetchMonsters();
    }

    public void fetchMonsters() {
        Call<List<Monster>> call = RetrofitInstance.getApiService().getMonsters();
        call.enqueue(new Callback<List<Monster>>() {
            @Override
            public void onResponse(Call<List<Monster>> call, Response<List<Monster>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    monsters.setValue(response.body());
                    Log.d("MonsterViewModel", "Fetched " + response.body().size() + " monsters: " + response.body());
                } else {
                    error.setValue("Failed to fetch monsters. Code: " + response.code());
                    Log.e("MonsterViewModel", "Error response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Monster>> call, Throwable t) {
                error.setValue("Failed to fetch monsters: " + t.getMessage());
                Log.e("MonsterViewModel", "Error fetching monsters", t);
            }
        });
    }
}
