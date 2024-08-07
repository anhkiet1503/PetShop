package com.example.petshop.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petshop.R;
import com.example.petshop.dao.OrdersDAO;
import com.example.petshop.model.Orders;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class OrdersAdminAdapter extends RecyclerView.Adapter<OrdersAdminAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Orders> list;
    private OrdersDAO ordersDAO;

    public OrdersAdminAdapter(Context context, ArrayList<Orders> list, OrdersDAO ordersDAO) {
        this.context = context;
        this.list = list;
        this.ordersDAO = ordersDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_orders_admin,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtUser.setText(list.get(position).getUser());
        holder.txtProduct.setText(list.get(position).getProduct());
        holder.txtQuantityBuy.setText("SL: " + String.valueOf(list.get(position).getQuantity()));

        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber =list.get(position).getTotal();
        String formattedNumber = formatter.format(myNumber);
        holder.txtTotal.setText(formattedNumber + " VND");

        holder.txtDeleteOrdersAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDeleteOrdersAdmin(list.get(holder.getAdapterPosition()).getProduct(),list.get(holder.getAdapterPosition()).getOrdersID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtUser, txtProduct, txtQuantityBuy, txtTotal, txtDeleteOrdersAdmin;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUser = itemView.findViewById(R.id.txtUser);
            txtProduct = itemView.findViewById(R.id.txtProduct);
            txtQuantityBuy = itemView.findViewById(R.id.txtQuantityBuy);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            txtDeleteOrdersAdmin = itemView.findViewById(R.id.txtDeleteOrdersAdmin);
        }
    }

    private void showDialogDeleteOrdersAdmin(String product, int ordersID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xoá đơn hàng \"" + product + "\" không?");

        builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean check = ordersDAO.delOrders(ordersID);
                if(check) {
                    Toast.makeText(context, "Xoá đơn hàng thành công", Toast.LENGTH_SHORT).show();

                    list.clear();
                    list = ordersDAO.getOrdersAdmin();
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
