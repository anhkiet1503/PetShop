package com.example.petshop.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.petshop.dao.ProductDAO;
import com.example.petshop.model.Orders;
import com.example.petshop.model.Product;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class ProductUserAdapter extends RecyclerView.Adapter<ProductUserAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Product> list;
    private OrdersDAO ordersDAO;
    private ProductDAO productDAO;

    public ProductUserAdapter(Context context, ArrayList<Product> list, ProductDAO productDAO) {
        this.context = context;
        this.list = list;
        this.productDAO =  productDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_product_user,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNamePr.setText(list.get(position).getFoodname());

        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber =list.get(position).getFoodprice();
        String formattedNumber = formatter.format(myNumber);
        holder.txtPricePr.setText(formattedNumber + " VND");

        holder.txtQuantityPr.setText("SL: " + String.valueOf(list.get(position).getFoodquantity()));

        // add new user orders
        holder.txtBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAddUserOrders(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNamePr, txtPricePr, txtQuantityPr, txtBuy;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNamePr = itemView.findViewById(R.id.txtNamePr);
            txtPricePr = itemView.findViewById(R.id.txtPricePr);
            txtQuantityPr = itemView.findViewById(R.id.txtQuantityPr);
            txtBuy = itemView.findViewById(R.id.txtBuy);
        }
    }

    private void showDialogAddUserOrders(Product product) {
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate((R.layout.dialog_add_orders),null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
        alertDialog.show();

        TextView txtProductName = view.findViewById(R.id.txtProductName);
        TextInputLayout quantityPr = view.findViewById(R.id.quantityPr);
        TextView txtTotalBuy = view.findViewById(R.id.txtTotalBuy);

        Button btn_addOrders = view.findViewById(R.id.btn_addOrders);
        Button btn_backOrders = view.findViewById(R.id.btn_backOrders);

        int quantityTemp = 0;

        txtProductName.setText(product.getFoodname());

        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber =product.getFoodprice();
        myNumber = myNumber * quantityTemp;
        String formattedNumber = formatter.format(myNumber);
        txtTotalBuy.setText(formattedNumber + " VND");

        quantityPr.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length() == 0) {
                    quantityPr.setError("Vui lòng nhập số lượng cần mua");

                    int quantityTemp1 = 0;
                    double myNumber1 =product.getFoodprice();
                    myNumber1 = myNumber1 * quantityTemp1;
                    String formattedNumber = formatter.format(myNumber1);
                    txtTotalBuy.setText(formattedNumber + " VND");
                }else {
                    quantityPr.setError(null);

                    int quantityTemp2 = Integer.parseInt(charSequence.toString());
                    double myNumber2 =product.getFoodprice();
                    myNumber2 = myNumber2 * quantityTemp2;
                    String formattedNumber = formatter.format(myNumber2);
                    txtTotalBuy.setText(formattedNumber + " VND");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_addOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("WELCOME",Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("userWelcome","");

                int quantityBuy = Integer.parseInt(quantityPr.getEditText().getText().toString());
                int newQuantity = product.getFoodquantity() - quantityBuy;

                if(quantityBuy > 0) {
                    Product productEdit = new Product(product.getIdfood(), product.getFoodname(), product.getFoodprice(), newQuantity);

                    boolean checkUpdate = productDAO.editProduct(productEdit);
                    if(checkUpdate) {
                        list.clear();
                        list = productDAO.getDS();
                        notifyDataSetChanged();
                    }

                    ordersDAO = new OrdersDAO(context);
                    Orders orders = new Orders(username,product.getFoodname(),product.getFoodprice()*quantityBuy,quantityBuy);

                    boolean check = ordersDAO.addOrdersUser(orders);
                    if(check) {
                        Toast.makeText(context, "Mua sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }else {
                        Toast.makeText(context, "Mua sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    quantityPr.setError("Vui lòng nhập số lượng cần mua");
                }
            }
        });

        btn_backOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
    }
}
