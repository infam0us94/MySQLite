package com.exemple.sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String DATABASE_NAME = "mydatabase";

    DatabaseManager mDatabase;
    EditText editTextName, editTextSalary;
    Spinner spinnerDept;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = new DatabaseManager(this);


        editTextName = findViewById(R.id.editTextName);
        editTextSalary = findViewById(R.id.editTxtSalary);
        spinnerDept = findViewById(R.id.spinnerDepartment);

        findViewById(R.id.buttonAddEmployee).setOnClickListener(this);
        findViewById(R.id.textViewEmployees).setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAddEmployee:
                addEmployee();
                break;
            case R.id.textViewEmployees:
                startActivity(new Intent(this, EmployeerActivity.class));
                break;
        }
    }

    private void addEmployee() {
        String name = editTextName.getText().toString().trim();
        String salary = editTextSalary.getText().toString().trim();
        String dept = spinnerDept.getSelectedItem().toString();

        if (name.isEmpty()) {
            editTextName.setError("Name can't be empty");
            editTextName.requestFocus();
            return;
        }
        if (salary.isEmpty()) {
            editTextSalary.setError("Salary can't be empty");
            editTextSalary.requestFocus();
            return;
        }

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String joiningDate = sdf.format(cal.getTime());

        if (mDatabase.addEmployee(name, dept, joiningDate, Double.parseDouble(salary)))
            Toast.makeText(this, "Employee Added", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Employee not Added", Toast.LENGTH_LONG).show();
    }
}
