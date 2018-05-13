package br.com.divinosilva.petrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

import model.Pessoa;

public class MainActivity extends AppCompatActivity {

    EditText editNome, editEmail;
    ListView listV_dados;

    FirebaseDatabase firebaseDatabase; // inicializo os objetos do banco
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNome = (EditText) findViewById(R.id.editNome);
        editEmail = (EditText) findViewById(R.id.editEmail);
        listV_dados = (ListView) findViewById(R.id.listViwerFB);

        inicializarFirebase();
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menu_novo){
            Pessoa p = new Pessoa();

            p.setUid(UUID.randomUUID().toString());
            p.setNome(editNome.getText().toString());
            p.setEmail(editEmail.getText().toString());

            databaseReference.child("CAD_PESSOA").child(p.getUid()).setValue(p); // Aqui preparo para enviar ao banco

            limparCampos();

        }


        return true;
    }

    private void limparCampos() {

        editNome.setText("");
        editEmail.setText("");
    }
}
