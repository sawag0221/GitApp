package com.sawag.gitapp.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast; // Toast をインポート
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sawag.gitapp.R;
// import com.example.yourapp.model.Monster; // MonsterクラスはAdapter内で使われるので直接は不要かも

public class MainActivity extends AppCompatActivity {

    private MonsterViewModel monsterViewModel;
    private MonsterAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView textViewError;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerViewMonsters);
        progressBar = findViewById(R.id.progressBar);
        textViewError = findViewById(R.id.textViewError);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // recyclerView.setHasFixedSize(true); // アイテムのサイズが固定の場合にパフォーマンス向上

        adapter = new MonsterAdapter(this);
        recyclerView.setAdapter(adapter);

        // ViewModelのインスタンスを取得
        monsterViewModel = new ViewModelProvider(this).get(MonsterViewModel.class);

        // 初期状態: プログレスバーを表示し、エラーメッセージとリストは非表示
        progressBar.setVisibility(View.VISIBLE);
        textViewError.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        // モンスターデータの監視
        monsterViewModel.getMonsters().observe(this, monsters -> {
            progressBar.setVisibility(View.GONE); // データ受信またはエラー発生でプログレスバー非表示
            if (monsters != null && !monsters.isEmpty()) {
                adapter.setMonsters(monsters);
                recyclerView.setVisibility(View.VISIBLE);
                textViewError.setVisibility(View.GONE);
            } else if (monsters != null && monsters.isEmpty() && monsterViewModel.getError().getValue() == null) {
                // データが空で、かつエラーメッセージもない場合 (正常に空のリストが返ってきた場合)
                textViewError.setText("No monsters found."); // 適切なメッセージに変更してください
                textViewError.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
            // エラーの場合は、error LiveData の observer で処理
        });

        // エラーメッセージの監視
        monsterViewModel.getError().observe(this, errorMessage -> {
            progressBar.setVisibility(View.GONE); // エラー発生でプログレスバー非表示
            if (errorMessage != null && !errorMessage.isEmpty()) {
                textViewError.setText("Error: " + errorMessage);
                textViewError.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE); // エラー時はリストを非表示
                Toast.makeText(MainActivity.this, "Error: " + errorMessage, Toast.LENGTH_LONG).show();
            } else {
                // エラーメッセージがクリアされた場合 (例えばリトライ成功時など)
                // textViewError.setVisibility(View.GONE); // 必要に応じて
            }
        });

        // (オプション) データを再取得するボタンなどを配置する場合
        // Button retryButton = findViewById(R.id.retryButton);
        // retryButton.setOnClickListener(v -> {
        //     progressBar.setVisibility(View.VISIBLE);
        //     textViewError.setVisibility(View.GONE);
        //     recyclerView.setVisibility(View.GONE);
        //     monsterViewModel.fetchMonsters();
        // });
    }
}