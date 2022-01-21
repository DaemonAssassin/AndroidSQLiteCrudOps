package com.gmail.mateendev3.androiddbcrudops.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gmail.mateendev3.androiddbcrudops.R;
import com.gmail.mateendev3.androiddbcrudops.database.DBHelper;

public class MainActivity extends AppCompatActivity {

    //declaring members
    EditText etRollNo, etName;
    Button btnAddRecord, btnDeleteRecord, btnUpdateRecord, btnViewData;
    DBHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing members
        etName = findViewById(R.id.et_name);
        etRollNo = findViewById(R.id.et_roll_no);
        btnAddRecord = findViewById(R.id.btn_add_record);
        btnDeleteRecord = findViewById(R.id.btn_delete_record);
        btnUpdateRecord = findViewById(R.id.btn_update_record);
        btnViewData = findViewById(R.id.btn_view_data);
        mHelper = new DBHelper(this);

        //Performing crud operation on data
        performCurdOperations();
    }

    private void performCurdOperations() {
        //insert record to db
        insertRecord();
        //delete record from db
        deleteRecord();
        //update record from db
        updateRecord();
        //view data from db
        viewData();
    }

    /**
     * Method to insert record in the db
     */
    private void insertRecord() {
        btnAddRecord.setOnClickListener(v -> {

            String rollNo = etRollNo.getText().toString();
            String name = etName.getText().toString();

            //condition to check if editTexts are empty
            if (checkInput(rollNo, name)) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                return;
            }

            //condition to check did data inserted successfully
            if (mHelper.insertData(rollNo, name))
                Toast.makeText(this, "Record inserted Successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Recored insertion failed", Toast.LENGTH_SHORT).show();


            //resetting edit texts to normal
            etRollNo.setText("");
            etName.setText("");
        });
    }

    /**
     * method to delete record from the db
     */
    private void deleteRecord() {
        btnDeleteRecord.setOnClickListener(v -> {
            String rollNo = etRollNo.getText().toString();
            if (checkInput(rollNo)) {
                Toast.makeText(this, "Please Enter Roll no", Toast.LENGTH_SHORT).show();
                return;
            }

            if (mHelper.deleteRecord(rollNo))
                Toast.makeText(this, "Record deleted Successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Record deletion failed", Toast.LENGTH_SHORT).show();

            //resetting etRollNO
            etRollNo.setText("");
        });
    }

    private void updateRecord() {
        btnUpdateRecord.setOnClickListener(v -> {

            String rollNO = etRollNo.getText().toString();
            String name = etName.getText().toString();

            if (checkInput(rollNO, name)) {
                Toast.makeText(this, "Not updated successfully", Toast.LENGTH_SHORT).show();
                return;
            }

            if (mHelper.updateRecord(rollNO, name))
                Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Not updated successfully", Toast.LENGTH_SHORT).show();

            //resetting edit texts to normal
            etRollNo.setText("");
            etName.setText("");
        });
    }

    /**
     * method to view data form the db
     */
    private void viewData() {
        btnViewData.setOnClickListener(v -> {
            //Getting Cursor obj with all data in it from db
            Cursor cursor = mHelper.getDataFromDB();
            if (cursor.getCount() > 0) {

                StringBuilder dataBuilder = new StringBuilder();
                //loop to traverse every row of the table inside cursor
                while (cursor.moveToNext()) {
                    dataBuilder.append("RollNo: ").append(cursor.getString(1)).append("\n");
                    dataBuilder.append("Name: ").append(cursor.getString(2)).append("\n\n\n");
                }

                //AlertDialog to show data
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Students Data")
                        .setMessage(dataBuilder)
                        .show();

            } else
                Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * method to check did the input field are empty or not
     *
     * @param rollNo rollNo of the student
     * @return true if rollNo is empty else false
     */
    private boolean checkInput(String rollNo) {
        return rollNo.isEmpty();
    }

    /**
     * method to check did the input field are empty or not
     *
     * @param rollNo roll no of the student
     * @param name   name of the student
     * @return true if name and rollNo is empty else false
     */
    private boolean checkInput(String rollNo, String name) {
        return rollNo.isEmpty() || name.isEmpty();
    }
}