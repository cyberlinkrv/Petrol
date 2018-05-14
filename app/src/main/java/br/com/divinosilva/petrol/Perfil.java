package br.com.divinosilva.petrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import loginfbemail.ConexaoFB;

public class Perfil extends AppCompatActivity {

    private TextView txtEmail, txtId;
    private Button btnLogout;

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        inicializarComponentes();
        eventoClick();

    }

    private void eventoClick() {

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConexaoFB.logOut();
                finish();
            }
        });
    }

    private void inicializarComponentes() {

        txtEmail = (TextView) findViewById(R.id.textPerfilEmail);
        txtId = (TextView) findViewById(R.id.textPerfilId);
        btnLogout = (Button) findViewById(R.id.butnLogout);
    }


    @Override
    protected void onStart() {
        super.onStart();
        auth = ConexaoFB.getFirebaseAuth();
        user = ConexaoFB.getFirebaseUser();

        verificaUser();
    }

    private void verificaUser() {
        if(user == null){
            finish();
        }else{
            txtEmail.setText("E-mail : "+user.getEmail());
            txtId.setText("ID : "+user.getUid());
        }


    }






}
