package com.example.petshop.fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petshop.R;
import com.example.petshop.adapter.ProductAdminAdapter;
import com.example.petshop.adapter.ProductUserAdapter;
import com.example.petshop.dao.ProductDAO;
import com.example.petshop.model.Product;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Locale;

public class ProductAdminFragment extends Fragment {
    private RecyclerView recyclerProductAdmin;
    private FloatingActionButton floatAdd;
    private ProductDAO productDAO;
    ArrayList<Product> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_admin,container,false);

        recyclerProductAdmin = view.findViewById(R.id.recyclerProductAdmin);
        floatAdd = view.findViewById(R.id.floatAdd);

        productDAO = new ProductDAO(getContext());
        loadData();

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAdd();
            }
        });

        TextInputLayout txtSearch;

        txtSearch = view.findViewById(R.id.txtSearchAdmin);

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
                    recyclerProductAdmin.setLayoutManager(linearLayoutManager);
                    ProductUserAdapter adapter = new ProductUserAdapter(getContext(),list, productDAO);
                    recyclerProductAdmin.setAdapter(adapter);
                }else {
                    String txt = txtSearch.getEditText().getText().toString().toUpperCase(Locale.ROOT);
                    boolean check = productDAO.CheckProduct(txt);
                    if(check) {
                        list.clear();
                        list = productDAO.getProduct(txt);

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        recyclerProductAdmin.setLayoutManager(linearLayoutManager);
                        ProductUserAdapter adapter = new ProductUserAdapter(getContext(),list, productDAO);
                        recyclerProductAdmin.setAdapter(adapter);
                    }else {
                        list.clear();

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        recyclerProductAdmin.setLayoutManager(linearLayoutManager);
                        ProductUserAdapter adapter = new ProductUserAdapter(getContext(),list, productDAO);
                        recyclerProductAdmin.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    private void loadData() {
        list = productDAO.getDS();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerProductAdmin.setLayoutManager(linearLayoutManager);
        ProductAdminAdapter adapter = new ProductAdminAdapter(getContext(),list,productDAO);
        recyclerProductAdmin.setAdapter(adapter);
    }
    private void showDialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_product,null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
        alertDialog.show();

        TextInputLayout namePr = view.findViewById(R.id.namePr);
        TextInputLayout price = view.findViewById(R.id.pricePr);
        TextInputLayout quantity = view.findViewById(R.id.quantityPr);

        Button btn_addPr = view.findViewById(R.id.btn_addPr);
        Button btn_backPr = view.findViewById(R.id.btn_backPr);

        btn_addPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameProduct = namePr.getEditText().getText().toString().toUpperCase(Locale.ROOT);
                String priceProduct = price.getEditText().getText().toString();
                String quantityProduct = quantity.getEditText().getText().toString();
                
                if(nameProduct.length() == 0 || priceProduct.length() == 0 || quantityProduct.length() == 0) {
                    Toast.makeText(getContext(), "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    Product product = new Product(nameProduct,Integer.parseInt(priceProduct),Integer.parseInt(quantityProduct));

                    boolean check = productDAO.addNewProduct(product);
                    if(check) {
                        Toast.makeText(getContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        alertDialog.dismiss();
                    }else {
                        Toast.makeText(getContext(), "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_backPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
}
