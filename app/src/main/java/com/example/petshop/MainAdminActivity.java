package com.example.petshop;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.petshop.fragment.AboutFragment;
import com.example.petshop.fragment.AccountFragment;
import com.example.petshop.fragment.LogoutFragment;
import com.example.petshop.fragment.OrdersAdminFragment;
import com.example.petshop.fragment.ProductAdminFragment;
import com.google.android.material.navigation.NavigationView;

public class MainAdminActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_admin_main);

        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolBar);
        navigationView = findViewById(R.id.navigationView);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        getSupportFragmentManager().beginTransaction().replace(R.id.linearLayout, new LogoutFragment()).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;
                if (item.getItemId() == R.id.mProduct) {
                    fragment = new ProductAdminFragment();
                } else if (item.getItemId() == R.id.mAccount) {
                    fragment = new AccountFragment();
                }else if (item.getItemId() == R.id.mOrders) {
                    fragment = new OrdersAdminFragment();
                }else if (item.getItemId() == R.id.mAbout) {
                    fragment = new AboutFragment();
                }else if (item.getItemId() == R.id.mLogOut) {
                    fragment = new LogoutFragment();
                }else {
                    fragment = new LogoutFragment();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.linearLayout, fragment).commit();

                getSupportActionBar().setTitle(item.getTitle());

                drawerLayout.closeDrawer(GravityCompat.START);

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }
}
