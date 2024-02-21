package com.example.lab06_ex_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lab06_ex_1.Adapters.ProductListViewAdapter;
import com.example.lab06_ex_1.Models.Product;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Product> productList = Product.ImportExampleProduct();
    private ListView productListView;
    private ProductListViewAdapter productListViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind the product list
        productListViewAdapter = new ProductListViewAdapter(productList);

        // Set the list via binding
        productListView = findViewById(R.id.productListView);
        productListView.setAdapter(productListViewAdapter);

        // On Product Item Clicked
        productListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = (Product) productListViewAdapter.getItem(position);
                Toast.makeText(MainActivity.this, product.getName(), Toast.LENGTH_LONG).show();
            }
        });

        // On Delete Product Clicked
        findViewById(R.id.deleteProductBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productList.size() > 0) {
                    int productIndex = 0;
                    productList.remove(productIndex);
                    productListViewAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}