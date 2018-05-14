package br.com.divinosilva.petrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import model.Pessoa;

public class Pesquisa extends AppCompatActivity {

    private EditText editPesqui;
    private ListView listPesquisa;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private List<Pessoa> listaPessoa = new ArrayList<Pessoa>();
    private ArrayAdapter<Pessoa> arrayAdapterPessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa);

        initComponentes();
        inicializaFireBase();
        evemtoEdit();
    }

    private void evemtoEdit() {
        editPesqui.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    String palavra = editPesqui.getText().toString().trim();
                    pesquisaPalavra(palavra);
            }
        });

    }

    private void pesquisaPalavra(String palavra) {

        Query query;
        if(palavra.equals("")){
            query = databaseReference.child("CAD_PESSOA").orderByChild("nome");// Aqui vou receber todos os elementos ordenados
        }else{
            query = databaseReference.child("CAD_PESSOA")
                    .orderByChild("nome").startAt(palavra).endAt(palavra+"\uf8ff");
        }

        listaPessoa.clear();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot objDataSnap:dataSnapshot.getChildren()){
                    Pessoa p = objDataSnap.getValue(Pessoa.class);
                    listaPessoa.add(p);
                }

                arrayAdapterPessoa = new ArrayAdapter<Pessoa>(Pesquisa.this,
                        android.R.layout.simple_list_item_1,listaPessoa);
                listPesquisa.setAdapter(arrayAdapterPessoa);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void inicializaFireBase() {
        FirebaseApp.initializeApp(Pesquisa.this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void initComponentes() {

        editPesqui = (EditText) findViewById(R.id.editTextPesquisar);
        listPesquisa = (ListView) findViewById(R.id.listVPesquisa);
    }


    @Override
    protected void onResume() {
        super.onResume();
        pesquisaPalavra("");
    }
}
