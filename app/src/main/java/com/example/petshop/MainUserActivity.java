package com.example.petshop;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

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
import com.example.petshop.fragment.OrdersUserFragment;
import com.example.petshop.fragment.ProductAdminFragment;
import com.example.petshop.fragment.ProductUserFragment;
import com.google.android.material.navigation.NavigationView;

public class MainUserActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_main);

        drawerLayout = findViewById(R.id.drawerLayoutUser);
        toolbar = findViewById(R.id.toolBarUser);
        navigationView = findViewById(R.id.navigationViewUser);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        getSupportFragmentManager().beginTransaction().replace(R.id.linearLayoutUser, new LogoutFragment()).commit();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;
                if (item.getItemId() == R.id.mUserProduct) {
                    fragment = new ProductUserFragment();
                }else if (item.getItemId() == R.id.mUserOrders) {
                    fragment = new OrdersUserFragment();
                }else if (item.getItemId() == R.id.mUserAbout) {
                    fragment = new AboutFragment();
                }else if (item.getItemId() == R.id.mUserLogOut) {
                    fragment = new LogoutFragment();
                }else {
                    fragment = new LogoutFragment();
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.linearLayoutUser, fragment).commit();

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
