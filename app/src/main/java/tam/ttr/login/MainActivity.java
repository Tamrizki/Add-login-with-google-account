package tam.ttr.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
// TODO [3] Inisialisasi komponen yang digunakan pada xml dan google client
    private TextView tv_name, tv_email;
    private LinearLayout linearLayout;
    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInOptions gso;
    private GoogleSignInAccount account;
    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        TODO [4] konfigurasi google sign in untuk request user ID dll. dan google client
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

//        TODO [5] deklarasi
        signInButton = findViewById(R.id.sign_in_button);
//        mengatur dimensi/ukuran button
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        tv_email = findViewById(R.id.tv_email);
        tv_name = findViewById(R.id.tv_name);
        linearLayout = findViewById(R.id.identitas);
//        Menghilangkan tampilan identitas sebelum sign in
        linearLayout.setVisibility(View.GONE);

        signInButton.setOnClickListener(this);
    }

//    TODO [6] buat methode untuk memanggil google client
        private void signIn() {
            Intent signInIntent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
        }

    @Override
    public void onClick(View view) {
        if (view == signInButton){
//            Panggil methode yang telah dibuat
            signIn();
        }
    }
}
