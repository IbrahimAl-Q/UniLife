package com.example.unilife;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikhaellopez.circularimageview.CircularImageView;


public class SigInActivity extends AppCompatActivity {

   private CircularImageView circularImageView;
   private EditText email, password;
   private TextView signInIns;
   private FirebaseAuth mAuth;
   private Button loginbtn;
   private ImageButton imageButton;
   private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_create_account);
        getSupportActionBar().hide();

        email= findViewById(R.id.emailInput);
        password= findViewById(R.id.pass_field);
        loginbtn= findViewById(R.id.login_btn);
        signInIns= findViewById(R.id.createaAccountText);
        mAuth = FirebaseAuth.getInstance();
        imageButton= findViewById(R.id.exitImageButton);


        mAuthStateListener= new FirebaseAuth.AuthStateListener() {
            FirebaseUser mfirebaseuser= mAuth.getCurrentUser();
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
               if(mfirebaseuser !=null){
                   Toast.makeText(SigInActivity.this, "You are signed in",Toast.LENGTH_SHORT ).show();
                    startActivity(new Intent (SigInActivity.this, MainActivity.class));
               }
               else{
                   Toast.makeText(SigInActivity.this, "Please Sign in",Toast.LENGTH_SHORT ).show();

               }
            }
        };


        signInIns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SigInActivity.this, CreateAccActivity.class ));
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent (SigInActivity.this, LoginNregisterActivity.class));
            }
        });



        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailText= email.getText().toString();
                String passwordText= password.getText().toString();

                if (emailText.isEmpty()){
                    email.setError("Please provide email id");
                    email.requestFocus();
                }
                if (passwordText.isEmpty()){
                    password.setError("Please provide your password");
                    password.requestFocus();
                }
                if(passwordText.length()<6){
                    password.setError("your password should be 6 characters or more");
                    password.requestFocus();
                }

                if (emailText.isEmpty() && passwordText.isEmpty() ){
                    Toast.makeText(SigInActivity.this, "Fields are empty", Toast.LENGTH_SHORT);
                }

                if (!(emailText.isEmpty() && passwordText.isEmpty()) && passwordText.length()>6){

                    mAuth.signInWithEmailAndPassword(emailText, passwordText)
                            .addOnCompleteListener(SigInActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success
                                        Toast.makeText(SigInActivity.this, "Authentication succeeded.",
                                                Toast.LENGTH_SHORT).show();
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if (user !=null){
                                            startActivity(new Intent(SigInActivity.this, MainActivity.class));
                                        }
                                    } else {
                                        Toast.makeText(SigInActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });





        circularImageView= findViewById(R.id.circularImageView);
        circularImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectImage(SigInActivity.this);
            }
        });

    }

    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {

                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        circularImageView.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage =  data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                circularImageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }

    }
}
