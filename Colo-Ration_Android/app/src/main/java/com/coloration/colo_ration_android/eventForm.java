package com.coloration.colo_ration_android;

import android.content.Intent;
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

public class eventForm extends AppCompatActivity {

    private String[] tableEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_form);
        Intent i = getIntent();
    }

    public void addClick(View view) {
        Intent intent = new Intent(eventForm.this, eventList.class);
        add();
        intent.putExtra("tableEvent", tableEvent);
        startActivity(intent);
    }

    public void add(){

        EditText Editname = (EditText)findViewById(R.id.name);
        String name = Editname.getText().toString();

        EditText Editdate= (EditText)findViewById(R.id.date);
        String date = Editdate.getText().toString();

        EditText Editcomment = (EditText)findViewById(R.id.comment);
        String comment = Editcomment.getText().toString();

        try {
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://192.168.1.24:5432/testDB";
            String user = "postgres";
            String passwd = "postgres";

            Connection conn = DriverManager.getConnection(url, user, passwd);
            Statement state = conn.createStatement();


            state.execute("INSERT INTO event(name, date, comment) VALUES (" + name + ", " + date + ", "  + comment);
            //state.execute("INSERT INTO roommate(firstname, lastname, mail)VALUES('firstname(30)', 'lastname(30)', 'mail(100)'");

            ResultSet r = state.executeQuery("SELECT COUNT(*) AS rowcount FROM event");
            r.next();
            int count = r.getInt("rowcount");

            // L'objet ResultSet contient le résultat de la requête SQL
            ResultSet result = state.executeQuery("SELECT * FROM event");

            // On récupère les MetaData
            ResultSetMetaData resultMeta = result.getMetaData();


            // On affiche le nom des colonnes

            for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");

            tableEvent = new String[count];

            int j = 0;
            while (result.next()) {
                tableEvent[j] = "";
                for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                    tableEvent[j] += "\t" + result.getObject(i).toString() + "\t |";
                j++;

            }
            result.close();
            state.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main_coloration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_adding) {
            addElement();
            return true;
        }*/

        return false;//super.onOptionsItemSelected(item);
    }
}
