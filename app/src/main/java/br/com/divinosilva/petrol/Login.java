package br.com.divinosilva.petrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    private EditText editEmail, editSenha;
    private Button bntLogar, btnN;
    private TextView txtEsqueciSenha;


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

    }

    private void inicializarComponentes() {

        editEmail = (EditText) findViewById(R.id.editTextEmailLog);
        editSenha = (EditText) findViewById(R.id.editTextSenhaLog);
        bntLogar = (Button) findViewById(R.id.butnLogin);
        btnN = (Button) findViewById(R.id.butnCadLog);
        txtEsqueciSenha = (TextView) findViewById(R.id.textViewEsqueciSenha);


    }





}
