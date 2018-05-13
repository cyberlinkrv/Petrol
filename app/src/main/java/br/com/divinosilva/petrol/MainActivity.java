package br.com.divinosilva.petrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import model.Pessoa;

public class MainActivity extends AppCompatActivity {

    EditText editNome, editEmail;
    ListView listV_dados;

    FirebaseDatabase firebaseDatabase; // inicializo os objetos do banco
    DatabaseReference databaseReference;

    private List<Pessoa> listaPessoa = new ArrayList<Pessoa>(); // Para carregar Lista do banco
    private ArrayAdapter<Pessoa> arrayAdapterPessoa;

    Pessoa pessoaSelecionada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editNome = (EditText) findViewById(R.id.editNome);
        editEmail = (EditText) findViewById(R.id.editEmail);
        listV_dados = (ListView) findViewById(R.id.listViwerFB);

        inicializarFirebase();
        eventoDatabase();//Função que traz a lista do banco

        listV_dados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pessoaSelecionada = (Pessoa)parent.getItemAtPosition(position);
                editNome.setText(pessoaSelecionada.getNome());
                editEmail.setText(pessoaSelecionada.getEmail());
            }
        });
    }

    private void eventoDatabase() {
        databaseReference.child("CAD_PESSOA").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaPessoa.clear();
                for(DataSnapshot objetoSnapshort:dataSnapshot.getChildren()){
                    Pessoa p = objetoSnapshort.getValue(Pessoa.class); // aqui ele tras tudo do banco
                    listaPessoa.add(p);
                }
                arrayAdapterPessoa = new ArrayAdapter<Pessoa>(MainActivity.this,
                        android.R.layout.simple_list_item_1, listaPessoa);
                listV_dados.setAdapter(arrayAdapterPessoa);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { // Aqui eu inflo o menu
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

        }else
            if(id == R.id.menu_update) {
            Pessoa p = new Pessoa();

            p.setUid(pessoaSelecionada.getUid());
            p.setNome(editNome.getText().toString().trim());// trim tira todos espaços em branco no inicio e no final
            p.setEmail(editEmail.getText().toString().trim());

            databaseReference.child("CAD_PESSOA").child(p.getUid()).setValue(p);
            limparCampos();

        }else
            if(id == R.id.menu_delete){
            Pessoa p = new Pessoa();
            p.setUid(pessoaSelecionada.getUid());
            databaseReference.child("CAD_PESSOA").child(p.getUid()).removeValue();
            limparCampos();
        }


        return true;
    }

    private void limparCampos() {

        editNome.setText("");
        editEmail.setText("");
    }
}
