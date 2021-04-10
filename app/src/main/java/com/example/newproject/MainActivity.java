package com.example.newproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button btn;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //VIEWS
        imageView = findViewById(R.id.imageView);
        btn = findViewById(R.id.btn1);

        //handle button click
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check runtime permission
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                                == PackageManager.PERMISSION_DENIED){
                        // permission not granted, request it.
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else{
                        // permission already granted
                        pickImageFromGallery();
                    }
                }

                else{
                    //system os is less tha marsmallow
                    pickImageFromGallery();
                }
            }
        });
    }
    private void pickImageFromGallery(){
        //Intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }
    //handle result of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission was granted
                    pickImageFromGallery();
                }
                else{
                    //permission was denied
                    Toast.makeText(this,"Permission Denied..!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //handle result of runtime permission

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
         if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
             //set image to image view
             imageView.setImageURI(data.getData());
         }
    }
}