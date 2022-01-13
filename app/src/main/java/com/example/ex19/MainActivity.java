package com.example.ex19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @author        Etay Sabag
 * @version       1.0
 * @since         13/1/2021
 *
 * MainActivity class, the main screen.
 * The class works with the file "userInput.txt".
 */
public class MainActivity extends AppCompatActivity {
    Intent si;
    TextView textBox;
    EditText userInput;
    String line,strrd = null;

    FileOutputStream fos;
    OutputStreamWriter osw;
    BufferedWriter bw;

    FileInputStream fis;
    BufferedReader br;
    InputStreamReader isr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        si = new Intent(this,Credits.class);

        userInput = (EditText) findViewById(R.id.editText);
        textBox = (TextView) findViewById(R.id.textView);
        textBox.setMovementMethod(new ScrollingMovementMethod());
    }

    /**
     * The method will occur when the app is loaded.
     * The method will load all the data that was saved in the app back.
     */
    @Override
    protected void onStart(){
        super.onStart();
        textBox.setText(readFile("userInput.txt"));
    }

    /**
     * The method will occur when the app is closed or paused.
     * The method will save all the data the user wrote.
     */
    @Override
    protected void onStop() {
        super.onStop();
        saveBtn(null);
    }


    /**
     * The method reads the 'userInput.txt' file.
     *
     * @param file the name of the file
     * @return the file content
     */
    public String readFile(String file){
        try {
            fis = openFileInput("userInput.txt");
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer sb = new StringBuffer();

            line = br.readLine();
            while (line != null) {
                sb.append(line+'\n');
                line = br.readLine();
            }
            strrd=sb.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return strrd;
    }

    /**
     * The method will write to the file.
     *
     * @param file the name of the file
     * @param textToWrite the text that will be written in the file
     */
    public void writeFile(String file, String textToWrite){
        try {
            fos = openFileOutput(file,MODE_PRIVATE);
            osw = new OutputStreamWriter(fos);
            bw = new BufferedWriter(osw);
            bw.write(textToWrite);
            bw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The method adds the user input to the 'userInput.txt' file.
     *
     * @param view
     */
    public void saveBtn(View view) {
        writeFile("userInput.txt",readFile("userInput.txt")+userInput.getText().toString());
        textBox.setText(readFile("userInput.txt"));
    }

    /**
     * The method clears the 'userInput.txt' file.
     *
     * @param view
     */
    public void resetBtn(View view) {
        writeFile("userInput.txt","");
        textBox.setText(readFile("userInput.txt"));
    }

    /**
     * The method saves the current state and exits the app
     *
     * @param view
     */
    public void exitBtn(View view) {
        saveBtn(null);
        finish();
    }





    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuCredits){
            startActivityForResult(si, 1);
        }
        return true;
    }



}