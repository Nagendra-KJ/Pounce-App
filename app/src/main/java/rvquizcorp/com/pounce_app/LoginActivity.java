package rvquizcorp.com.pounce_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText;
    private Button emailSignInButton;
    private TextView emailRegisterButton, forgotPasswordTextView;
    private FirebaseAuth firebaseAuth;
    private String email, password;
    private TextInputLayout textInputEmail,textInputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialise();
        emailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                getSignInDetails();
            }
        });
        emailRegisterButton.setOnClickListener((new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        }));
        forgotPasswordTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
            }
        });
        if(firebaseAuth.getCurrentUser()!=null)
            login();
    }

    private void getSignInDetails() {
        email = emailEditText.getText().toString().trim();
        password = passwordEditText.getText().toString();
        validate();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging you in");
        try {
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful())
                        login();
                    else
                       new Error().showError(LoginActivity.this);
                }
            });
        } catch (IllegalArgumentException e) {
            progressDialog.dismiss();
        }
    }

    private void initialise() {
        firebaseAuth = FirebaseAuth.getInstance();
        emailEditText = findViewById(R.id.emailEditText);
        emailSignInButton = findViewById(R.id.emailSignInButton);
        emailRegisterButton = findViewById(R.id.emailRegisterButton);
        passwordEditText = findViewById(R.id.passwordEditText);
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);
        textInputEmail=findViewById(R.id.emailInput);
        textInputPassword=findViewById(R.id.passwordInput);
    }
    private void validate()
    {
        if(email.isEmpty())
            textInputEmail.setError("Please enter your email");
        else
            textInputEmail.setError(null);
        if(password.isEmpty())
            textInputPassword.setError("Please enter your password");
        else
            textInputPassword.setError(null);
    }
    private void login()
    {
        Intent intent = new Intent(LoginActivity.this, MainScreen.class);
        intent.putExtra("display_name", Objects.requireNonNull(firebaseAuth.getCurrentUser()).getDisplayName());
        startActivity(intent);
        finish();
    }

}

