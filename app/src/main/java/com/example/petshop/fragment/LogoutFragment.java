package com.example.petshop.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.petshop.MainUserActivity;
import com.example.petshop.R;
import com.example.petshop.SignInActivity;

public class LogoutFragment extends Fragment {
    Button btn_logOut, btn_exit;
    Activity context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logout, container, false);

        context = getActivity();

        return view;
    }

    public void onStart() {
        super.onStart();

        btn_logOut = (Button) context.findViewById(R.id.btn_logOut);

        btn_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, SignInActivity.class));
            }
        });

        btn_exit = (Button) context.findViewById(R.id.btn_exit);

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
    }
}