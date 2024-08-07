package com.example.petshop.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petshop.R;
import com.example.petshop.dao.UserDAO;
import com.example.petshop.model.Account;
import com.example.petshop.model.Product;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Account> list;
    private UserDAO userDAO;

    public AccountAdapter(Context context, ArrayList<Account> list, UserDAO userDAO) {
        this.context = context;
        this.list = list;
        this.userDAO = userDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_account,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtFullname.setText(list.get(position).getName());
        holder.txtUsername.setText(list.get(position).getUsername());
        holder.txtPassword.setText(list.get(position).getPassword());

        // edit account
        holder.txtEditAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogUpdateAcc(list.get(holder.getAdapterPosition()));
            }
        });

        // delete account
        holder.txtDeleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDeleteAcc(list.get(holder.getAdapterPosition()).getUsername());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtFullname, txtUsername, txtPassword, txtEditAcc, txtDeleteAcc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtFullname = itemView.findViewById(R.id.txtFullname);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtPassword = itemView.findViewById(R.id.txtPassword);
            txtEditAcc = itemView.findViewById(R.id.txtEditAcc);
            txtDeleteAcc = itemView.findViewById(R.id.txtDeleteAcc);
        }
    }

    private void showDialogUpdateAcc(Account account) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate((R.layout.dialog_edit_acc),null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
        alertDialog.show();

        TextInputLayout passAcc = view.findViewById(R.id.passAcc);
        TextInputLayout fullnameAcc = view.findViewById(R.id.fullnameAcc);

        Button btn_editAcc = view.findViewById(R.id.btn_editAcc);
        Button btn_backAcc = view.findViewById(R.id.btn_backAcc);

        passAcc.getEditText().setText(account.getPassword());
        fullnameAcc.getEditText().setText(account.getName());

        btn_editAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = account.getUsername();
                String password = passAcc.getEditText().getText().toString();
                String fullname = fullnameAcc.getEditText().getText().toString();

                if(password.length() == 0 || fullname.length() == 0) {
                    Toast.makeText(context, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    Account accountEdit = new Account(username,password,fullname);

                    boolean check = userDAO.editAccount(accountEdit);
                    if(check) {
                        Toast.makeText(context, "Chỉnh sửa sản phẩm thành công", Toast.LENGTH_SHORT).show();

                        list.clear();
                        list = userDAO.getAcc();
                        notifyDataSetChanged();

                        alertDialog.dismiss();
                    }else {
                        Toast.makeText(context, "Chỉnh sửa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_backAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    private void showDialogDeleteAcc(String username) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xoá tài khoản \"" + username + "\" không?");

        builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean check = userDAO.delAccount(username);
                if(check) {
                    Toast.makeText(context, "Xoá tài khoản thành công", Toast.LENGTH_SHORT).show();

                    list.clear();
                    list = userDAO.getAcc();
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(context, "Xoá tài khoản thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Huỷ",null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
