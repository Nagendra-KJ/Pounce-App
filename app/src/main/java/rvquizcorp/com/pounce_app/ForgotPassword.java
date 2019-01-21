package rvquizcorp.com.pounce_app;
/*This class deals with the forgot password module of the login part. If the user has forgotten his password, he can
enter his email to get a password reset email sent to his account. All emailing is handled by the firebase backend.
Ready made methods are provided to be used by the users.
 */

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    private EditText emailTextBox;
    private Button submitButton;
    private FirebaseAuth firebaseAuth;
    private String email;
    private TextInputLayout forgotPasswordEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        initialise();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    private void initialise() {
        emailTextBox = findViewById(R.id.emailTextBox);
        submitButton = findViewById(R.id.submitButton);
        firebaseAuth = FirebaseAuth.getInstance();
        forgotPasswordEmail = findViewById(R.id.forgotEmailInput);
    }

    private void sendEmail() {
        email = emailTextBox.getText().toString().trim();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sending email");
        validate();
        try {
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(ForgotPassword.this, "Email sent successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        new Error().showError(ForgotPassword.this);
                    }
                }
            });
        } catch (IllegalArgumentException e) {
            progressDialog.dismiss();
        }
    }

    private void validate() {
        if (email.isEmpty())
            forgotPasswordEmail.setError("Please enter your email");
        else
            forgotPasswordEmail.setError(null);
    }
}
