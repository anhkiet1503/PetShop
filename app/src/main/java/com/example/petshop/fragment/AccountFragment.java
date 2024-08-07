package com.example.petshop.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petshop.R;
import com.example.petshop.adapter.AccountAdapter;
import com.example.petshop.dao.UserDAO;
import com.example.petshop.model.Account;

import java.util.ArrayList;

public class AccountFragment extends Fragment {
    private RecyclerView recyclerAccount;
    private UserDAO userDAO;

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,container,false);

        recyclerAccount = view.findViewById(R.id.recyclerAccount);

        userDAO = new UserDAO(getContext());
        ArrayList<Account> list = userDAO.getAcc();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerAccount.setLayoutManager(linearLayoutManager);
        AccountAdapter adapter = new AccountAdapter(getContext(),list,userDAO);
        recyclerAccount.setAdapter(adapter);

        return view;
    }
}
