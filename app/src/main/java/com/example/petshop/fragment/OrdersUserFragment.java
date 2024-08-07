package com.example.petshop.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petshop.R;
import com.example.petshop.adapter.OrdersAdminAdapter;
import com.example.petshop.adapter.OrdersUserAdapter;
import com.example.petshop.dao.OrdersDAO;
import com.example.petshop.model.Orders;

import java.util.ArrayList;

public class OrdersUserFragment extends Fragment {
    private RecyclerView recyclerOrdersUser;
    private OrdersDAO ordersDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders_user,container,false);

        recyclerOrdersUser = view.findViewById(R.id.recyclerOrdersUser);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("WELCOME", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("userWelcome","");

        ordersDAO = new OrdersDAO(getContext());
        ArrayList<Orders> list = ordersDAO.getOrdersUser(username);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerOrdersUser.setLayoutManager(linearLayoutManager);
        OrdersUserAdapter adapter = new OrdersUserAdapter(getContext(),list);
        recyclerOrdersUser.setAdapter(adapter);

        return view;
    }
}
