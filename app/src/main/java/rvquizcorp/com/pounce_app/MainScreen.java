package rvquizcorp.com.pounce_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainScreen extends AppCompatActivity {
    private Button hostButton,joinButton;
    private TextView welcomeText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        initialise();
        String message;
        Bundle bundle=getIntent().getExtras();
        assert bundle != null;
        message="Welcome "+bundle.getString("display_name");
        welcomeText.setText(message);
        hostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainScreen.this, "Log out successful", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainScreen.this,LoginActivity.class);
                startActivity(intent);
                finish();
                }
        });
    }

    private void initialise() {
        hostButton=findViewById(R.id.hostButton);
        joinButton=findViewById(R.id.joinButton);
        welcomeText=findViewById(R.id.welcomeText);
    }
}
