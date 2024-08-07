package com.example.petshop.fragment;

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
import com.example.petshop.dao.OrdersDAO;
import com.example.petshop.model.Orders;

import java.util.ArrayList;

public class OrdersAdminFragment extends Fragment {

    private RecyclerView recyclerOrders;
    private OrdersDAO ordersDAO;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders_admin,container,false);

        recyclerOrders = view.findViewById(R.id.recyclerOrdersAdmin);

        ordersDAO = new OrdersDAO(getContext());
        ArrayList<Orders> list = ordersDAO.getOrdersAdmin();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerOrders.setLayoutManager(linearLayoutManager);
        OrdersAdminAdapter adapter = new OrdersAdminAdapter(getContext(),list,ordersDAO);
        recyclerOrders.setAdapter(adapter);

        return view;
    }
}
