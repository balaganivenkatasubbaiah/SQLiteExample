package com.example.subbu.sqliteexample;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ImageView profilePic;
    private EditText editTextName,editTextNumber;
    private Button addRecord,getRecords;
    private static final int SELECT_PICTURE = 100;
    Uri selectedImageUri;
    DatabaseHandler db;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);
        editTextName = (EditText) findViewById(R.id.your_name);
        editTextNumber = (EditText) findViewById(R.id.mobile_number);
        addRecord = (Button) findViewById(R.id.add_record);
        getRecords = (Button) findViewById(R.id.get_records);

        listView = (ListView) findViewById(R.id.list_records);

        profilePic = (ImageView) findViewById(R.id.profile_pic);

        addRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                String name = editTextName.getText().toString().trim();
                String mobileNmuber = editTextNumber.getText().toString().trim();

                byte[] inputData = new byte[1024];
                try {
                    if(selectedImageUri!=null)
                    {
                        InputStream iStream = getContentResolver().openInputStream(selectedImageUri);
                        inputData = Utils.getBytes(iStream);
                    }
                    else
                    {
                        // get image from drawable

                        Bitmap image = BitmapFactory.decodeResource(getResources(),

                                R.mipmap.ic_launcher);

                          // convert bitmap to byte

                        ByteArrayOutputStream stream = new ByteArrayOutputStream();

                        image.compress(Bitmap.CompressFormat.JPEG, 100, stream);

                        inputData = stream.toByteArray();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                db.addContact(new Contact(name,mobileNmuber,inputData));

            }
        });

        getRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<Contact> allRecords = db.getAllContacts();

                CustomAdapter adapter = new CustomAdapter(MainActivity.this,allRecords);

                listView.setAdapter(adapter);

            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                 selectedImageUri = data.getData();

                if (null != selectedImageUri)
                {

                    profilePic.setImageURI(selectedImageUri);
                }
            }
        }
    }
}
