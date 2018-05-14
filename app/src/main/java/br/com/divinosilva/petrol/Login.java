package br.com.divinosilva.petrol;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import loginfbemail.ConexaoFB;

public class Login extends AppCompatActivity {

    private EditText editEm, editSen;
    private Button btnLo, btnN;
    private TextView txtEsqueciSenha;

    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializarComponentes();
        eventoClicks();


    }

    private void eventoClicks() {
        btnN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Cadastrar_user.class);
                startActivity(i);
            }
        });

        btnLo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String em = editEm.getText().toString().trim();
                String se = editSen.getText().toString().trim();

                login(em, se);
            }
        });

        txtEsqueciSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, ResetSenha.class);
                startActivity(i);
            }
        });

    }

    private void login(final String em, String se) {

        auth.signInWithEmailAndPassword(em, se)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent i = new Intent(Login.this, MainActivity.class); // Aqui se logar passo a Man ou Perfil
                            startActivity(i);
                            alert("Bem Vindo!!  "+em);
                            limpCamposLogin();
                        }else{
                            alert("Erro ao tentar logar na aplicação => Favor verifique seu Usuário ou Senha");
                        }
                    }
                });

    }

    private void limpCamposLogin() {
        editEm.setText("");
        editSen.setText("");
    }


    private void  alert(String men){
        Toast.makeText(Login.this, men,Toast.LENGTH_LONG).show();
    }

    private void inicializarComponentes() {

        editEm = (EditText) findViewById(R.id.editTextEmailLog);
        editSen = (EditText) findViewById(R.id.editTextSenhaLog);
        btnLo = (Button) findViewById(R.id.butnLogin);
        btnN = (Button) findViewById(R.id.butnCadLog);
        txtEsqueciSenha = (TextView) findViewById(R.id.textViewEsqueciSenha);

    }

    @Override
    protected void onStart() {
        super.onStart();

        auth = ConexaoFB.getFirebaseAuth();
    }
}
