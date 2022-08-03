package com.example.application;

//import android.graphics.Color;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TimePicker;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.application.utils.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;
import com.luck.picture.lib.language.LanguageConfig;

import java.util.ArrayList;

// Page for users to add a task to their to-do list
public class addPage extends AppCompatActivity {
    Button button_done;
    Button button_back;
    Button button_camera;
    Button button_galley;
    EditText send_acts;
    EditText send_tips;
    //EditText send_duration;
    TimePicker time;
    String mSelectTime;
    ImageView iv;
    private String picUrl;
    String myDay;
    private Uri imageUri; // point to the image itself
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize variables
        send_acts = findViewById(R.id.set_activity);
        send_tips = findViewById(R.id.set_tips);
        button_done = findViewById(R.id.addDone);
        button_back = findViewById(R.id.back_button);
        //send_duration = findViewById(R.id.duration1);
        time = findViewById(R.id.timePicker);
        iv = findViewById(R.id.iv);
        storageRef = FirebaseStorage.getInstance().getReference("uploads");

        // Done button clicked
        button_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String day = getIntent().getStringExtra("date");
                myDay= day;
                Log.i(TAG, "onClick: "+day);
                String activity = send_acts.getText().toString();
                String tips = send_tips.getText().toString();
                //String stringDuration = send_duration.getText().toString();
                //int duration;
                if(activity.isEmpty()){
                    Toast.makeText(addPage.this, "Info missing!", Toast.LENGTH_SHORT).show();
                    return;
                }
                int hour = time.getHour();
                int minute = time.getMinute();
                String task_time = hour + ":" + minute;

                // anh
                // get the url to the image in storage and create the task and push to database
                if(imageUri != null){
                    StorageReference imageRef = storageRef.child(System.currentTimeMillis()
                            + "." + getFileExtension(imageUri)); // unique name for each photo

                    //putting a photo at that name, need success listener to get the url to that photo
                    imageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // create task inside and push our else it won't work
                                    Task task = new Task(activity,tips, hour, minute, false, uri.toString());
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("Tasks and Dates")
                                            .child(day)
                                            .child("NotComplete")
                                            .push().setValue(task);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(addPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(addPage.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) ;
                }
                else{
                    Toast.makeText(addPage.this, "No image selected", Toast.LENGTH_SHORT).show();
                    return;
                }

//                if (imageUri == null){
//                    return;
//                }
                //Task.taskArrayList.add(task);
                // Push input to Firebase
               // FirebaseDatabase.getInstance().getReference().child("Tasks and Dates").child(day).push().setValue(task);

                //add calender
                Utils.writeToCalendar(getApplicationContext(),activity,tips,day+" "+hour+":"+minute);

                Intent intent = new Intent(addPage.this, CalenderActivity.class);
                Toast.makeText(addPage.this, "Task set successfully!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        // Click to take picture
        findViewById(R.id.tv_take_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 openImageChooser();
            }
        });

    }

    //get the .jpg tail of the image
    // no need to understand what these codes mean
    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    //this method is called when we choose our image
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK && result != null && result.getData() != null){
                        imageUri = result.getData().getData(); // uri will point to the image selected
                        iv.setImageURI(imageUri); // display the image on the screen
                    }
                }
            }
    );

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");   // only choose images files
        intent.setAction(Intent.ACTION_GET_CONTENT);
        someActivityResultLauncher.launch(intent); // launch the method above

    }

    //    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        /*结果回调*/
//        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
//            if (data != null) {
//                PictureBean pictureBean = data.getParcelableExtra(PictureSelector.PICTURE_RESULT);
//
//                //使用 Glide 加载图片
//                Glide.with(this)
//                        .load(pictureBean.getUri())
//                        .apply(RequestOptions.centerCropTransform()).into(iv);
//                picUrl = pictureBean.getUri().toString();            }
//        }
//    }
}
