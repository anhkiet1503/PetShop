package com.example.petshop.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petshop.R;
import com.example.petshop.dao.OrdersDAO;
import com.example.petshop.model.Orders;
import com.example.petshop.model.Product;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class OrdersUserAdapter extends RecyclerView.Adapter<OrdersUserAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Orders> list;
    private OrdersDAO ordersDAO;

    public OrdersUserAdapter(Context context, ArrayList<Orders> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_orders_user,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtUserAcc.setText(list.get(position).getUser());
        holder.txtProductUser.setText(list.get(position).getProduct());
        holder.txtQuantityBuyUser.setText("SL: " + String.valueOf(list.get(position).getQuantity()));

        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber =list.get(position).getTotal();
        String formattedNumber = formatter.format(myNumber);
        holder.txtTotalUser.setText(formattedNumber + " VND");

        holder.txtEditOrdersUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEditOrders(list.get(holder.getAdapterPosition()),list.get(holder.getAdapterPosition()).getUser());
            }
        });

        holder.txtDeleteOrdersUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDeleteOrdersUser(list.get(holder.getAdapterPosition()).getProduct(),list.get(holder.getAdapterPosition()).getOrdersID(),list.get(holder.getAdapterPosition()).getUser());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtUserAcc, txtProductUser, txtQuantityBuyUser, txtTotalUser, txtEditOrdersUser, txtDeleteOrdersUser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUserAcc = itemView.findViewById(R.id.txtUserAcc);
            txtProductUser = itemView.findViewById(R.id.txtProductUser);
            txtQuantityBuyUser = itemView.findViewById(R.id.txtQuantityBuyUser);
            txtTotalUser = itemView.findViewById(R.id.txtTotalUser);

            txtEditOrdersUser = itemView.findViewById(R.id.txtEditOrdersUser);
            txtDeleteOrdersUser = itemView.findViewById(R.id.txtDeleteOrdersUser);
        }
    }

    private void showDialogEditOrders(Orders orders, String username) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_orders,null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
        alertDialog.show();

        TextView txtProductNameUser = view.findViewById(R.id.txtProductNameUser);
        TextInputLayout quantityPrUser = view.findViewById(R.id.quantityPrUser);
        TextView txtTotalBuyUser = view.findViewById(R.id.txtTotalBuyUser);

        Button btn_editOrdersUser = view.findViewById(R.id.btn_editOrdersUser);
        Button btn_backOrdersUser = view.findViewById(R.id.btn_backOrdersUser);

        txtProductNameUser.setText(orders.getProduct());

        quantityPrUser.getEditText().setText(String.valueOf(orders.getQuantity()));

        int price = orders.getTotal() / orders.getQuantity();
        int quantityTemp = orders.getQuantity();

        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber =price;
        myNumber = myNumber * quantityTemp;
        String formattedNumber = formatter.format(myNumber);
        txtTotalBuyUser.setText(formattedNumber + " VND");

        quantityPrUser.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0) {
                    quantityPrUser.setError("Vui lòng nhập số lượng cần mua");

                    int quantityTemp1 = 0;
                    double myNumber1 =price;
                    myNumber1 = myNumber1 * quantityTemp1;
                    String formattedNumber = formatter.format(myNumber1);
                    txtTotalBuyUser.setText(formattedNumber + " VND");
                }else {
                    quantityPrUser.setError(null);

                    int quantityTemp2 = Integer.parseInt(charSequence.toString());
                    double myNumber2 =price;
                    myNumber2 = myNumber2 * quantityTemp2;
                    String formattedNumber = formatter.format(myNumber2);
                    txtTotalBuyUser.setText(formattedNumber + " VND");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_editOrdersUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = orders.getOrdersID();
                String productNameUser = txtProductNameUser.getText().toString();
                int quantityPrUser1 = Integer.parseInt(quantityPrUser.getEditText().getText().toString());

                if(String.valueOf(quantityPrUser1).equals("")) {
                    quantityPrUser.setError("Vui lòng nhập thông tin");
                    Toast.makeText(context, "Nhập thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    ordersDAO = new OrdersDAO(context);
                    Orders ordersEdit = new Orders(id,username,productNameUser,price*quantityPrUser1,quantityPrUser1);

                    boolean check = ordersDAO.editOrdersUser(ordersEdit);
                    if(check) {
                        Toast.makeText(context, "Chỉnh sửa sản phẩm thành công", Toast.LENGTH_SHORT).show();

                        list.clear();
                        list = ordersDAO.getOrdersUser(username);
                        notifyDataSetChanged();

                        alertDialog.dismiss();
                    }else {
                        Toast.makeText(context, "Chỉnh sửa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_backOrdersUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }

    private void showDialogDeleteOrdersUser(String product, int ordersID, String username) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xoá đơn hàng \"" + product + "\" không?");

        builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                ordersDAO = new OrdersDAO(context);
                boolean check = ordersDAO.delOrders(ordersID);
                if(check) {
                    Toast.makeText(context, "Xoá đơn hàng thành công", Toast.LENGTH_SHORT).show();

                    list.clear();
                    list = ordersDAO.getOrdersUser(username);
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(context, "Xoá đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Huỷ",null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
