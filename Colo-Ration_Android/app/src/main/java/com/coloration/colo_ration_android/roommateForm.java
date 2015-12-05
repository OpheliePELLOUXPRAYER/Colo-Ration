package com.coloration.colo_ration_android;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class roommateForm extends AppCompatActivity {

    private String[] tableRoommate;

    private String firstname;
    private String lastname;
    private String mail;
    private String phonenumber;

    private boolean readyToGo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roommate_form);
    }

    public void addClick(View view) {
        add();
        while(!readyToGo) {}
        Intent intent = new Intent(roommateForm.this, roommateList.class);
        intent.putExtra("tableRoommate", tableRoommate);
        readyToGo = false;
        startActivity(intent);
    }

    public void add() {

        EditText Editfirstname = (EditText) findViewById(R.id.firstname);
        firstname = Editfirstname.getText().toString();

        EditText Editlastname = (EditText) findViewById(R.id.lastname);
        lastname = Editlastname.getText().toString();

        EditText Editmail = (EditText) findViewById(R.id.mail);
        mail = Editmail.getText().toString();

        EditText Editphonenumber = (EditText) findViewById(R.id.phonenumber);
        phonenumber = Editphonenumber.getText().toString();

        new dbAddRommate().execute();
    }

    private class dbAddRommate extends AsyncTask<Void, String, Void> {
        protected Void doInBackground(Void... params) {

            try {
                Class.forName("org.postgresql.Driver");

                String url = "jdbc:postgresql://192.168.1.24:5432/testDB";
                String user = "postgres";
                String passwd = "postgres";

                Connection conn = DriverManager.getConnection(url, user, passwd);
                Statement state = conn.createStatement();

                state.execute("INSERT INTO roommate(firstname, lastname, mail)VALUES('"+ firstname + "', ''" + lastname + "'', ''" + mail  + "');"); //a modifier

                ResultSet r = state.executeQuery("SELECT COUNT(*) AS rowcount FROM task");
                r.next();
                int count = r.getInt("rowcount");

                ResultSet result = state.executeQuery("SELECT * FROM task");

                ResultSetMetaData resultMeta = result.getMetaData();

                for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                    System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");

                tableRoommate = new String[count];

                int j = 0;
                while (result.next()) {
                    tableRoommate[j] = "";
                    for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                        tableRoommate[j] += "\t" + result.getObject(i).toString() + "\t |";
                    j++;

                }
                result.close();
                state.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            readyToGo = true;
            return null;
        }
    }
}
