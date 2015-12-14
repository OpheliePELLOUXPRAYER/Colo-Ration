package com.coloration.colo_ration_android;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
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
    private String[] table;

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
        intent.putExtra("tableTitle", table);
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

                String url = "jdbc:postgresql://192.168.43.44:5432/testDB";
                String user = "postgres";
                String passwd = "postgres";

                Connection conn = DriverManager.getConnection(url, user, passwd);
                Statement state = conn.createStatement();

                state.execute("INSERT INTO roommate(prenom, nom, mail, telephone) VALUES ('"+ firstname + "', '" + lastname + "', '" + mail  + "', '" + phonenumber  + "');");

                ResultSet r = state.executeQuery("SELECT COUNT(*) AS rowcount FROM roommate");
                r.next();
                int count = r.getInt("rowcount");

                ResultSet result = state.executeQuery("SELECT * FROM roommate");

                ResultSetMetaData resultMeta = result.getMetaData();

                table = new String[resultMeta.getColumnCount()];

                for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                    table[i-1] = resultMeta.getColumnName(i).toUpperCase();

                tableRoommate = new String[count];

                int j = 0;
                while (result.next()) {
                    tableRoommate[j] = "";
                    for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                        tableRoommate[j] += result.getObject(i).toString() + "&";
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
        }

        return super.onKeyDown(keyCode, event);
    }
}
