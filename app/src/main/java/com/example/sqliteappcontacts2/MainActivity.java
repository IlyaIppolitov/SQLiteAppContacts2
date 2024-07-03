package com.example.sqliteappcontacts2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final String LOG_TAG = "myLogs";
    Button btnAdd,btnRead,btnClear, btnClearAll;
    EditText etName,etEmail;

    DBHelper dbHelper;

    private RecyclerView recyclerView;
    private ContactsAdapter contactsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.btnAdd);
        btnRead = findViewById(R.id.btnRead);
        btnClear = findViewById(R.id.btnClear);
        btnClearAll = findViewById(R.id.btnClearAll);

        btnAdd.setOnClickListener(this);
        btnRead.setOnClickListener(this);
        btnClear.setOnClickListener(this);
        btnClearAll.setOnClickListener(this);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);

        dbHelper = new DBHelper(this);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v) {
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();

        switch (v.getId()){
            case(R.id.btnAdd):
                dbHelper.addContact(name, email);
                updateContactsView();
                break;
            case (R.id.btnRead):
                updateContactsView();
                break;
            case(R.id.btnClear):
                dbHelper.deleteContact(name);
                updateContactsView();
                break;
            case(R.id.btnClearAll):
                dbHelper.clearContacts();
                updateContactsView();
                break;

        }
        dbHelper.close();
    }

    private void updateContactsView(){
        contactsAdapter = new ContactsAdapter(dbHelper.getAllContacts());
        recyclerView.setAdapter(contactsAdapter);
    }
}