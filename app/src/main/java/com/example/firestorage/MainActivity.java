package com.example.firestorage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseStorage firebaseStorage;
    StorageReference parentRef;
    StorageReference childRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] permissions=new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                          Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ActivityCompat.requestPermissions(this,permissions,1);
        String file_path="storage/emulated/0/Download/test_image.jpg";
        File f=new File(file_path);
        Uri file= Uri.fromFile(f);


        firebaseStorage=FirebaseStorage.getInstance();
        parentRef=firebaseStorage.getReference();
        String fileLiveName=(System.currentTimeMillis()/1000)+"";
        Log.d("TAG", "fileLiveName: "+fileLiveName);
        String fileLivePath="uploads/images/"+fileLiveName;
        childRef=parentRef.child(fileLivePath);


        UploadTask uploadTask=childRef.putFile(file);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String downloadUrl= childRef.getDownloadUrl().toString();
                Log.d("TAG", "Success: "+downloadUrl);
            }
        }). addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "Failed: "+e);


            }
        });

    }
}









