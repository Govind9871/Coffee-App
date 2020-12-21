/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    /** quantity is a global variable*/
    int quantity = 1;
    public void submitOrder(View view) {
        CheckBox whippedCreamBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamBox.isChecked();

        CheckBox chocolate = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolate.isChecked();

        EditText nameObject = findViewById(R.id.name_text);
        String name = nameObject.getText().toString();

        int price = calculatePrice( hasChocolate, hasWhippedCream );
        String orderSummary = createOrderSummary(price, hasWhippedCream , hasChocolate , name);

        final String subject =getString(R.string.order_summary_email_subject, name);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"anurag741sinha@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    protected int calculatePrice(  boolean hasChocolate , boolean hasWhippedCream ){
         int Tp = 5;
         if( hasChocolate ){
             Tp += 2;
         }

        if( hasWhippedCream ){
            Tp += 1;
        }
        return quantity * Tp;
    }

    private String createOrderSummary(int price , boolean addWhippedCream, boolean addChocolate , String name ){
         String str = getString(R.string.order_summary_name, name );
         str += "\n" + getString(R.string.order_summary_whipped_cream , addWhippedCream);
         str += "\n" + getString(R.string.order_summary_chocolate, addChocolate );
         str += "\n" + getString(R.string.order_summary_quantity , quantity );
         str += "\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price) );
         str += "\n" + getString(R.string.thank_you);
         return str;
    }

    public void increment( View view ){
        if( quantity == 100 ){
            Toast.makeText(this, "You cannot have more than 100 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity) ;
    }

    public void decrement( View view ){
        if( quantity == 1 ){
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


}