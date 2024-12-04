package com.example.lks2024;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.lks2024.model.Product;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DetailActivity extends AppCompatActivity {
   private String nama,des,img;
   private int id,stk,harg;
   private TextView judul,namabarang,deskrispi,stock,harga,totalharga;
   private ImageView gambar;
   private EditText countView;
   private Button kurang,tambah,addTocart;
   private int count = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.product_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.product_detail), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loadComponents();
        getData();
        loadCount();
        updateCartCount();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set item Home sebagai aktif
        bottomNavigationView.setSelectedItemId(R.id.home);

        // Set listener untuk navigasi item
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.home) {
                return true;
            } else if (id == R.id.cart) {
                startActivity(new Intent(DetailActivity.this, CardActivity.class));
                return true;
            } else if (id == R.id.history) {
                startActivity(new Intent(DetailActivity.this, HistoryActivity.class));
                return true;
            }
            return false;
        });

        kurang.setOnClickListener(v -> {
            if(this.count <= 1){
                Toast.makeText(this,"Pesanan Minimal 1",Toast.LENGTH_SHORT).show();
                return;
            }
            this.count--;
            loadCount();
        });
        tambah.setOnClickListener(v -> {
            if(this.count > this.stk){
                Toast.makeText(this,"Stock Tidak Mencukupi",Toast.LENGTH_SHORT).show();
                return;
            }
            this.count++;
            loadCount();
        });
        addTocart.setOnClickListener(v -> {
            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
            intent.putExtra("ProductName", nama);
            intent.putExtra("Productdes", des);
            intent.putExtra("ProductStock", stk);
            intent.putExtra("Productimage", img);
            intent.putExtra("Productid", id);
            intent.putExtra("Productharga", harg);
            intent.putExtra("count", count);
            startActivity(intent);
        });

    }
    private void loadCount(){
        countView.setText(String.valueOf(this.count));
        double total = this.count * this.harg;
        totalharga.setText("Total "+CartAdapter.getConvertUang((int) total));
    }
    private void getData(){
         nama = getIntent().getStringExtra("ProductName");
        harg = getIntent().getIntExtra("Productharga",0);
        img = getIntent().getStringExtra("Productimage");
        id = getIntent().getIntExtra("Productid",0);
        des = getIntent().getStringExtra("Productdes");
        stk = getIntent().getIntExtra("ProductStock",0);
             judul.setText(nama);
             namabarang.setText(nama);
             deskrispi.setText(des);
             harga.setText(CartAdapter.getConvertUang(harg));
             stock.setText("Stock : " + stk);
             Glide.with(this)
                     .load(img)
                     .into(gambar);
    }
    private void loadComponents(){
        judul = findViewById(R.id.judul);
        namabarang = findViewById(R.id.namabarang);
        deskrispi = findViewById(R.id.deskripsi);
        stock = findViewById(R.id.stock);
        harga = findViewById(R.id.hargasatuan);
        gambar = findViewById(R.id.gambar);
        kurang = findViewById(R.id.kurang);
        tambah = findViewById(R.id.tambah);
        addTocart = findViewById(R.id.addCart);
        countView = findViewById(R.id.count);
        totalharga = findViewById(R.id.totalharga);
    }
    private void updateCartCount() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        BadgeDrawable badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.cart);
        badgeDrawable.setNumber(MainActivity.getCounting());
        badgeDrawable.setVisible(MainActivity.getCounting() > 0);
    }
}
