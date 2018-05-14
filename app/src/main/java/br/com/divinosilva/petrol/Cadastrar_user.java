package br.com.divinosilva.petrol;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import loginfbemail.ConexaoFB;

public class Cadastrar_user extends AppCompatActivity {

    private EditText editEmail, editS;
    private Button btnCadNew, btnVot;

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_user);

        inicializarComponentes();

        eventClicks();

    }

    private void eventClicks() {
        btnVot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Finaliza a tela
            }
        });

        btnCadNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString().trim();
                String sen = editS.getText().toString().trim();

                implSen(email, sen);
            }
        });
    }

    private void implSen(String email, String sen) {

        auth.createUserWithEmailAndPassword(email, sen)
                .addOnCompleteListener(Cadastrar_user.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                                alert("Usuário Cadastrado com Sucesso !!!!");

                            Intent i = new Intent(Cadastrar_user.this, Perfil.class);
                            startActivity(i);
                            finish();

                        }else{
                                alert("Erro ao tentar Cadastrar o Usuário");
                        }
                    }
                });
    }

    private void  alert(String men){
        Toast.makeText(Cadastrar_user.this, men,Toast.LENGTH_SHORT).show();
    }



    private void inicializarComponentes() {

        editEmail = (EditText) findViewById(R.id.editTextEmailCadNew);
        editS = (EditText) findViewById(R.id.editTextSenhaCadNew);
        btnCadNew = (Button) findViewById(R.id.butnCadNew);
        btnVot = (Button) findViewById(R.id.butnVoltCadNew);

    }


    @Override
    protected void onStart() {
        super.onStart();
        auth = ConexaoFB.getFirebaseAuth();


    }
}
