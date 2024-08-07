package com.example.petshop.fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petshop.R;
import com.example.petshop.adapter.ProductUserAdapter;
import com.example.petshop.dao.OrdersDAO;
import com.example.petshop.dao.ProductDAO;
import com.example.petshop.model.Orders;
import com.example.petshop.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Locale;

public class ProductUserFragment extends Fragment {
    private RecyclerView recyclerProductUser;
    private ProductDAO productDAO;
    ArrayList<Product> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_user,container,false);

        recyclerProductUser = view.findViewById(R.id.recyclerProductUser);

        productDAO = new ProductDAO(getContext());
        list = productDAO.getDS();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerProductUser.setLayoutManager(linearLayoutManager);
        ProductUserAdapter adapter = new ProductUserAdapter(getContext(),list, productDAO);
        recyclerProductUser.setAdapter(adapter);

        TextInputLayout txtSearch;

        txtSearch = view.findViewById(R.id.txtSearch);

        txtSearch.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0) {
                    list.clear();
                    list = productDAO.getDS();

                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    recyclerProductUser.setLayoutManager(linearLayoutManager);
                    ProductUserAdapter adapter = new ProductUserAdapter(getContext(),list, productDAO);
                    recyclerProductUser.setAdapter(adapter);
                }else {
                    String txt = txtSearch.getEditText().getText().toString().toUpperCase(Locale.ROOT);
                    boolean check = productDAO.CheckProduct(txt);
                    if(check) {
                        list.clear();
                        list = productDAO.getProduct(txt);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        recyclerProductUser.setLayoutManager(linearLayoutManager);
                        ProductUserAdapter adapter = new ProductUserAdapter(getContext(),list, productDAO);
                        recyclerProductUser.setAdapter(adapter);
                    }else {
                        list.clear();

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        recyclerProductUser.setLayoutManager(linearLayoutManager);
                        ProductUserAdapter adapter = new ProductUserAdapter(getContext(),list, productDAO);
                        recyclerProductUser.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }
}
