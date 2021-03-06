package edu.up.cs371.textmod;

/**
 * class TextModActivity
 *
 * Allow text to be modified in simple ways with button-presses.
 */
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class TextModActivity extends ActionBarActivity {

    // array-list that contains our images to display
    private ArrayList<Bitmap> images;

    // instance variables containing widgets
    private ImageView imageView; // the view that shows the image


    Button upperButton;
    Button lowerButton;
    Button randLetterButton;
    Button jumbleButton;
    Button clearButton;


    Button noSpaceButton;
    private Button altCaseButton;
    private Button copyButton;
    private EditText editText;
    private Spinner spinner;

    /**
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // perform superclass initialization; load the layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_mod);

        Button reverse = (Button) findViewById(R.id.reverseButton);
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                Button reverse = (Button) findViewById(R.id.reverseButton);
                TextView editText = (TextView) findViewById(R.id.editText);
                String text = editText.getText().toString();
                String result = "";

                for (int i = (text.length() - 1); i >= 0; i--) {
                    result = (result + text.charAt(i));

                }
                editText.setText(result);

            }
        });

        // set instance variables for our widgets
        imageView = (ImageView)findViewById(R.id.imageView);
        editText = (EditText)findViewById(R.id.editText);
        upperButton = (Button) findViewById(R.id.button6);
        lowerButton = (Button) findViewById(R.id.button7);
        randLetterButton = (Button)findViewById(R.id.randLetterButton);
        altCaseButton = (Button) findViewById(R.id.buttonAltCase);
        jumbleButton = (Button) findViewById(R.id.jumbleButton);

        clearButton = (Button)findViewById(R.id.clearButton);

        Button copyButton = (Button)findViewById(R.id.copyButton);
        noSpaceButton = (Button)findViewById(R.id.noSpaceButton);

        // Set up the spinner so that it shows the names in the spinner array resources
        //
        // get spinner object
        spinner = (Spinner)findViewById(R.id.spinner);

        // get array of strings
        String[] spinnerNames = getResources().getStringArray(R.array.spinner_names);
        // create adapter with the strings
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, spinnerNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // bind the spinner and adapter
        spinner.setAdapter(adapter);

        // load the images from the resources
        //
        // create the arraylist to hold the images
        images = new ArrayList<Bitmap>();
        // get array of image-resource IDs
        TypedArray imageIds2 = getResources().obtainTypedArray(R.array.imageIdArray);
        // loop through, adding one image per string
        for (int i = 0; i < spinnerNames.length; i++) {
            // determine the index; use 0 if out of bounds
            int id = imageIds2.getResourceId(i,0);
            if (id == 0) id = imageIds2.getResourceId(0,0);
            // load the image; add to arraylist
            Bitmap img = BitmapFactory.decodeResource(getResources(), id);
            images.add(img);
        }

        // define a listener for the spinner
        spinner.setOnItemSelectedListener(new MySpinnerListener());

    }

    /**
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_text_mod, menu);
        return true;
    }

    /**
     * @see android.app.Activity#onOptionsItemSelected(android.view.MenuItem)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void clearText(View v){

            editText.setText(" ");
    }
    public void alternateCase(View v){

        String text = editText.getText().toString();
        String newText = "";

        for (int i=0; i<text.length(); i++){
            if(i%2==0) {
                newText = newText + text.toUpperCase().charAt(i);
            }
            else
            {
                newText = newText + text.toLowerCase().charAt(i);
            }

        }
        editText.setText(newText);
    }


    /**
     * class that handles our spinner's selection events
     */
    private class MySpinnerListener implements OnItemSelectedListener {

        /**
         * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(
         *                  android.widget.AdapterView, android.view.View, int, long)
         */
        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView,
                                   int position, long id) {
            // set the image to the one corresponding to the index selected by the spinner
            imageView.setImageBitmap(images.get(position));
        }

        /**
         * @see android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(
         *                  android.widget.AdapterView)
         */
        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
            // your code here
        }
    }

    public void upperButton(View v) {
        editText.setText(editText.getText().toString().toUpperCase());
    }

    public void lowerButton(View v) {
        editText.setText(editText.getText().toString().toLowerCase());
    }
    public void Copy(View selectedItemView){
        String newText = editText.getText().toString();
        String[] spinnerNames = getResources().getStringArray(R.array.spinner_names);

        newText = newText + spinnerNames[spinner.getSelectedItemPosition()];
        editText.setText(newText);

    }
    public void jumbleButton(View v){
        String text = editText.getText().toString();
        Random r = new Random();
        int q = r.nextInt(text.length());
        String first = text.substring(0,q);
        String last = text.substring(q,text.length());
        String product = last + first;
        editText.setText(product);


    }

    public void noWhiteSpace (View v) {
        String newText = editText.getText().toString();

        newText = newText.replaceAll("\\s", "");
        editText.setText(newText);
    }
    public void randLetter(View v){
        String aWord = editText.getText().toString();
        int aWordLen = aWord.length();
        Random r = new Random();
        int result = r.nextInt(aWordLen);
        String last;
        String first;

        char c = (char)(r.nextInt(26) + 'a');

        first = aWord.substring(0,result);
        last = aWord.substring(result,aWordLen);

        String endProduct = first + c + last;

        editText.setText(endProduct);
    }


}
