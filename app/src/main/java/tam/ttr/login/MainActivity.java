package tam.ttr.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
// TODO [3] Inisialisasi komponen yang digunakan pada xml dan google client
    private TextView tv_name, tv_email;
    private LinearLayout linearLayout;
    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private GoogleSignInAccount account;
    private static final int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        TODO [4] deklarasi
        signInButton = findViewById(R.id.sign_in_button);
//        mengatur dimensi/ukuran button
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        tv_email = findViewById(R.id.tv_email);
        tv_name = findViewById(R.id.tv_name);
        linearLayout = findViewById(R.id.identitas);
//        Menghilangkan tampilan identitas sebelum sign in
        linearLayout.setVisibility(View.GONE);

//        TODO [5] konfigurasi google sign in untuk request user ID dll. dan google client
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
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
// TODO [7] setelah sigIn, get googlecount user
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
//    mengembalikan informasi user
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(MainActivity.this);
        try {

            Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show();
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

//            Get data user

            // Signed in successfully, show authenticated UI.
            updateUI(acct);
        } catch (ApiException e) {
            Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show();
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("signIn_Tag", "signInResult:failed code=" + e.getStatusCode());
            updateUI(acct);
        }
    }

    private void updateUI(GoogleSignInAccount account) {

        if (account != null){
            signInButton.setVisibility(View.GONE);
            linearLayout.setVisibility(View.VISIBLE);
            tv_name.setText(account.getDisplayName());
            tv_email.setText(account.getEmail());

            Toast.makeText(this, ""+account.getFamilyName(), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Kosong", Toast.LENGTH_SHORT).show();
        }
    }
}
