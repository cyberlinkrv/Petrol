package br.com.divinosilva.petrol;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class BemVindo extends AppCompatActivity {

    private LoginButton btnLoginFB;
    private Button btnLoginEmail;

    private FirebaseAuth auth;
    private CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bem_vindo);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        if(isLoggedIn == true){
            Intent ia = new Intent(BemVindo.this, MainActivity.class);
            ia.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(ia);
        }


        inicializarComponent();
        inicializarFBCall();
        clickButton();


    }


    private void clickButton() {

        btnLoginFB.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                firebaseLogin(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                alert("Cancelado pelo usuário");
            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        btnLoginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BemVindo.this, Login.class);
                startActivity(i);
            }
        });


    }

    private void firebaseLogin(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(BemVindo.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(BemVindo.this, MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);

                        }else{
                             alert("Erro de autenticação com Firebase");
                        }
                    }


                });
    }


    private void inicializarFBCall() {

        auth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();
    }

    private void inicializarComponent() {
        btnLoginFB = (LoginButton) findViewById(R.id.btnLoginFb);
        btnLoginFB.setReadPermissions("email", "public_profile");
        btnLoginEmail = (Button) findViewById(R.id.btnLoginEmail);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    private void  alert(String men){
        Toast.makeText(BemVindo.this, men,Toast.LENGTH_LONG).show();
    }



}
