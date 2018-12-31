package rvquizcorp.com.pounce_app;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private Button buttonSubmit;
    private ImageView profilePicture;
    private TextView textFirstName, textLastName, textMobileNumber, textEmailAddress,textPassword;
    private DatabaseReference databaseUsers;
    private static int REQUEST_CAMERA=1,SELECT_FILE=0;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private User profile;
    private TextInputLayout textInputFirstName,textInputLastname,textInputMobileNumber,textInputEmail,textInputPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initialise();
        profile=new User();
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserInfo();
                if (formValidate())
                    registerNewUser();
            }
        });
    }

    private void getUserInfo() {
        profile.setFirstName(textFirstName.getText().toString());
        profile.setLastName(textLastName.getText().toString());
        profile.setMobileNumber(textMobileNumber.getText().toString());
        profile.setEmailAddress(textEmailAddress.getText().toString());
    }

    private void initialise() {
        buttonSubmit = findViewById(R.id.buttonSubmit);
        profilePicture = findViewById(R.id.profilePicture);
        textFirstName = findViewById(R.id.textFirstName);
        textLastName = findViewById(R.id.textLastName);
        textMobileNumber = findViewById(R.id.textMobileNumber);
        textEmailAddress = findViewById(R.id.textEmailAddress);
        textPassword = findViewById(R.id.textPassword);
        databaseUsers = FirebaseDatabase.getInstance().getReference("users");
        firebaseAuth=FirebaseAuth.getInstance();
        textInputFirstName=findViewById(R.id.textInputFirstName);
        textInputLastname=findViewById(R.id.textInputLastName);
        textInputMobileNumber=findViewById(R.id.textInputMobileNumber);
        textInputEmail=findViewById(R.id.textInputEmailAddress);
        textInputPassword=findViewById(R.id.textInputPassword);
    }

    private boolean formValidate()
    {
        if(profile.getFirstName().isEmpty())
            textInputFirstName.setError("Please enter your first name");
        else
            textInputFirstName.setError(null);
        if(profile.getLastName().isEmpty())
            textInputLastname.setError("Please enter your last name");
        else
            textInputLastname.setError(null);
        if(profile.getMobileNumber().isEmpty())
            textInputMobileNumber.setError("Please enter your mobile number");
        else
            textInputMobileNumber.setError(null);
        if(profile.getEmailAddress().isEmpty())
            textInputEmail.setError("Please enter your email");
        else
            textInputEmail.setError(null);
        if(textPassword.getText().toString().isEmpty())
            textInputPassword.setError("Please enter your password");
        else
            textInputPassword.setError(null);
        return !(profile.getEmailAddress().isEmpty()||profile.getFirstName().isEmpty()||profile.getLastName().isEmpty()||profile.getMobileNumber().isEmpty()
                || textPassword.getText().toString().isEmpty());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK)
        {
            if(requestCode==REQUEST_CAMERA)
            {
                Bundle bundle = data.getExtras();
                assert bundle != null;
                final Bitmap img=(Bitmap) bundle.get("data");
                profilePicture.setImageBitmap(img);
            }
            else if(requestCode==SELECT_FILE)
            {
                Uri imagePath=data.getData();
                profilePicture.setImageURI(imagePath);
            }
        }
    }
    private void selectImage()
    {
        final String[] options={"Camera","Gallery","Default","Cancel"};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Add Image");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i;
                switch(which)
                {
                    case 2:
                        profilePicture.setImageResource(R.mipmap.default_profile_pic);
                        break;
                    case 3:
                        dialog.dismiss();
                        break;
                    case 0:
                        i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(i,REQUEST_CAMERA);
                        break;
                    case 1:
                        i=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        i.setType("image/*");
                        startActivityForResult(Intent.createChooser(i,"Select File"),SELECT_FILE);
                }
            }
        });
        builder.show();
    }
    public void registerNewUser() {
        final ProgressDialog progressDialog=new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Registering new user");
        progressDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(profile.getEmailAddress(),textPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.hide();
                if(task.isSuccessful())
                {
                    progressDialog.setMessage("Uploading user details");
                    progressDialog.show();
                    Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    firebaseAuth.signInWithEmailAndPassword(profile.getEmailAddress(),textPassword.getText().toString());
                    user=firebaseAuth.getCurrentUser();
                    user.updateProfile(new UserProfileChangeRequest.Builder().setDisplayName(profile.getFirstName()).build());
                    user.sendEmailVerification();
                    Toast.makeText(RegisterActivity.this, "Please verify your email to continue", Toast.LENGTH_SHORT).show();
                    String Uid=user.getUid();
                    profile.setUserId(Uid);
                    databaseUsers.child(Uid).setValue(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful())
                                Toast.makeText(RegisterActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            else
                                new Error().showError(RegisterActivity.this);
                        }
                    });
                    finish();
                }
                else
                    new Error().showError(RegisterActivity.this);
            }
        });
    }
}
