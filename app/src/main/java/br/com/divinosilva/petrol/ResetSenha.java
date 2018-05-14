package br.com.divinosilva.petrol;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import loginfbemail.ConexaoFB;

public class ResetSenha extends AppCompatActivity {

    private EditText editEm;
    private Button btnEnvs;

    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_senha);

        iniciarComponentes();
        eventoClick();

    }

    private void eventoClick() {

        btnEnvs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emai = editEm.getText().toString().trim();
                resetSenha(emai);
            }
        });
    }

    private void resetSenha(String emai) {

        auth.sendPasswordResetEmail(emai)
        .addOnCompleteListener(ResetSenha.this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                        alert("Você recebera em seu e-mail as instruções para trocar sua senha");
                        finish();
                }else {
                        alert("E-mail informado não corresponde a nem um e-mail cadastrado.");
                }
            }
        });

    }

    private void  alert(String men){
        Toast.makeText(ResetSenha.this, men,Toast.LENGTH_LONG).show();
    }

    private void iniciarComponentes() {
        editEm = (EditText) findViewById(R.id.editText3);
        btnEnvs = (Button) findViewById(R.id.buttonEn);

    }


    @Override
    protected void onStart() {
        super.onStart();
        auth = ConexaoFB.getFirebaseAuth();
    }
}
