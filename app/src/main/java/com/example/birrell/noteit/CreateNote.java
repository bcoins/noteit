package com.example.birrell.noteit;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;


public class CreateNote extends AppCompatActivity {
    EditText editText;
    TextView textView1;
    String FILE_NAME = "note1.txt";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        Button camera = (Button)findViewById(R.id.takePhoto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        editText = (EditText)findViewById(R.id.editNote);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent take = new Intent();
                take.setAction("android.media.action.IMAGE_CAPTURE");
                take.addCategory("android.intent.category.DEFAULT");
                File file = new File(Environment.getDataDirectory()+"/000.jpg");
                Uri uri = Uri.fromFile(file);
                take.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                startActivity(take);
            }


        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.done) {
            write(editText.getText().toString());
            Log.d("btn","done");
            Intent intent = new Intent();
            intent.putExtra("data_return",editText.getText().toString());
            setResult(RESULT_OK,intent);
            editText.setText("");
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void write(String content){
        try{
            FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(content.getBytes());
            fos.close();
            Toast.makeText(CreateNote.this,"Saved!",Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(CreateNote.this,"Error saving file!",Toast.LENGTH_SHORT).show();
        }
    }
    public String read(){
        String text="";
        try{
            FileInputStream fis = openFileInput(FILE_NAME);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            text = new String(buffer);
            fis.close();
            return text;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
