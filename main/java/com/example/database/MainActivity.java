package com.example.database;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener {
    private TextView textView;
    private DataHelper dataHelper;
    private Spinner spinner;
    private Button deleteButton;
    private EditText newUserEditText;
    private Button addButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.textView = (TextView) this.findViewById(R.id.textView);
        this.spinner = (Spinner) this.findViewById(R.id.spinner);
        this.deleteButton = (Button) this.findViewById(R.id.buttonDelete);
        this.newUserEditText = (EditText) this.findViewById(R.id.editText);
        this.addButton = (Button) this.findViewById(R.id.buttonInsert);

        this.dataHelper = new DataHelper(this);


        int rowsDeleted = this.dataHelper.deleteAll();
        Log.d("MainActivity", "Deleted " + rowsDeleted + " rows");


        this.dataHelper.insert("Elhadj Bah");
        this.dataHelper.insert("Aicha Diallo");
        this.dataHelper.insert("Daniel ");


        List<String> users = dataHelper.selectAll();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedUser = spinner.getSelectedItem().toString();
                dataHelper.deleteUser(selectedUser);
                updateUI();
            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUser = newUserEditText.getText().toString();
                dataHelper.insert(newUser);
                updateUI();
            }
        });


        updateUI();
    }

    private void updateUI() {

        List<String> users = dataHelper.selectAll();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        newUserEditText.setText("");


        textView.setText("");
        for (String user : users) {
            textView.append(user + "\n");
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectuser = parent.getItemAtPosition(position).toString();
        Log.d("MainActivity", "Selected user: " + selectuser);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }
}