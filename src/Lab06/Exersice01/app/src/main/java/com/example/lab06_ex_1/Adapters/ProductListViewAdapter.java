package com.example.lab06_ex_1.Adapters;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lab06_ex_1.Models.Product;
import com.example.lab06_ex_1.R;

import java.util.ArrayList;

public class ProductListViewAdapter extends BaseAdapter {
    final ArrayList<Product> _productList;

    public ProductListViewAdapter(ArrayList<Product>  products) {
        _productList = products;
    }

    @Override
    public int getCount() {
        return _productList.size();
    }

    @Override
    public Object getItem(int i) {
        return _productList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return _productList.get(i).getProductId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parentView) {
        View productView;
        if (convertView == null) {
            productView = View.inflate(parentView.getContext(), R.layout.product_list_view_item, null);
        } else productView = convertView;

        Product product = (Product) getItem(position);
        ((TextView) productView.findViewById(R.id.productId)).setText(String.format("ID = %d", product.getProductId()));
        ((TextView) productView.findViewById(R.id.productName)).setText(String.format("Tên SP : %s", product.getName()));
        ((TextView) productView.findViewById(R.id.productPrice)).setText(String.format("Giá %d", product.getPrice()));


        return productView;
    }
}
