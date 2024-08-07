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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petshop.R;
import com.example.petshop.dao.ProductDAO;
import com.example.petshop.model.Product;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class ProductAdminAdapter extends RecyclerView.Adapter<ProductAdminAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Product> list;
    private ProductDAO productDAO;

    public ProductAdminAdapter(Context context, ArrayList<Product> list, ProductDAO productDAO) {
        this.context = context;
        this.list = list;
        this.productDAO = productDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_product_admin,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtName.setText(list.get(position).getFoodname());

        NumberFormat formatter = new DecimalFormat("#,###");
        double myNumber =list.get(position).getFoodprice();
        String formattedNumber = formatter.format(myNumber);
        holder.txtPrice.setText(formattedNumber + " VND");

        holder.txtQuantity.setText("SL: " + list.get(position).getFoodquantity());

        // edit product
        holder.txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEdit(list.get(holder.getAdapterPosition()));
            }
        });

        // delete product
        holder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogDelete(list.get(holder.getAdapterPosition()).getFoodname(),list.get(holder.getAdapterPosition()).getIdfood());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName, txtPrice, txtQuantity, txtEdit, txtDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtEdit = itemView.findViewById(R.id.txtEdit);
            txtDelete = itemView.findViewById(R.id.txtDelete);
        }
    }

    private void showDialogEdit(Product product) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate((R.layout.dialog_edit_product),null);
        builder.setView(view);

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable((new ColorDrawable(Color.TRANSPARENT)));
        alertDialog.show();

        TextInputLayout namePr = view.findViewById(R.id.namePr);
        TextInputLayout price = view.findViewById(R.id.pricePr);
        TextInputLayout quantity = view.findViewById(R.id.quantityPr);

        Button btn_editPr = view.findViewById(R.id.btn_editPr);
        Button btn_backPr = view.findViewById(R.id.btn_backPr);

        namePr.getEditText().setText(product.getFoodname());
        price.getEditText().setText(String.valueOf(product.getFoodprice()));
        quantity.getEditText().setText((String.valueOf(product.getFoodquantity())));

        btn_editPr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int idProduct = product.getIdfood();
                String nameProduct = namePr.getEditText().getText().toString();
                String priceProduct = price.getEditText().getText().toString();
                String quantityProduct = quantity.getEditText().getText().toString();

                if(nameProduct.length() == 0 || priceProduct.length() == 0 || quantityProduct.length() == 0) {
                    Toast.makeText(context, "Nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else {
                    Product productEdit = new Product(idProduct,nameProduct,Integer.parseInt(priceProduct),Integer.parseInt(quantityProduct));

                    boolean check = productDAO.editProduct(productEdit);
                    if(check) {
                        Toast.makeText(context, "Chỉnh sửa sản phẩm thành công", Toast.LENGTH_SHORT).show();

                        list.clear();
                        list = productDAO.getDS();
                        notifyDataSetChanged();

                        alertDialog.dismiss();
                    }else {
                        Toast.makeText(context, "Chỉnh sửa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
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

    private void showDialogDelete(String foodname,int idfood) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn xoá sản phẩm \"" + foodname + "\" không?");

        builder.setPositiveButton("Xoá", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                boolean check = productDAO.delProduct(idfood);
                if(check) {
                    Toast.makeText(context, "Xoá sản phẩm thành công", Toast.LENGTH_SHORT).show();

                    list.clear();
                    list = productDAO.getDS();
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(context, "Xoá sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("Huỷ",null);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}

