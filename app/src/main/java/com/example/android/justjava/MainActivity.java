/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 */
 package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.attr.name;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        if (quantity < 100)
        {
            quantity = quantity + 1;
        }
        else
        {
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();

        }
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity > 1)
        {
            quantity = quantity - 1;
        }
        else
        {
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText text = (EditText) findViewById(R.id.name_field);
        String fullName = text.getText().toString();
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        boolean hasChocolate = chocolateCheckbox.isChecked();
        //Log.v("MainActivity.java","Has whipped cream: " + hasWhippedCream);
        int price = calculatePrice(hasWhippedCream,hasChocolate);
        String message = createOrderSummary(fullName,price,hasWhippedCream,hasChocolate);
//        displayMessage(message);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + fullName);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }

    /**
     * Calculates the price of the order.
     *
     * @param quantity is the number of cups of coffee ordered
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;
        if (addWhippedCream)
        {
            basePrice = basePrice + 1;
        }
        if (addChocolate)
        {
            basePrice = basePrice + 2;
        }
        return quantity * basePrice;
    }

    /**
     * Create summary of the order.
     *
     * @param totalPrice is the price of the cups of coffee ordered
     * @param addWhippedCream indicates whether user wants whipped cream topping
     * @param addChocolate indicates whether user wants chocolate topping
     * @return order summary message
     */
    private String createOrderSummary(String name, int totalPrice, boolean addWhippedCream, boolean addChocolate){
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage = priceMessage + "\nAdd whipped cream? " + addWhippedCream;
        priceMessage = priceMessage + "\nAdd chocolate? " + addChocolate;
        priceMessage = priceMessage + "\nQuantity: " + quantity;
        priceMessage = priceMessage + "\nTotal: $" + totalPrice;
        priceMessage = priceMessage + "\n" + getString(R.string.thank_you);
        return priceMessage;
    }
}