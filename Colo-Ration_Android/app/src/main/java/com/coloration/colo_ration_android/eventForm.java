package com.coloration.colo_ration_android;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class eventForm extends AppCompatActivity {

    private String[] tableEvent;
    private String[] table;

    private String name;
    private String date;
    private String comment;

    private boolean readyToGo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_form);
    }

    public void addClick(View view) {
        add();
        while(!readyToGo) {}
        Intent intent = new Intent(eventForm.this, eventList.class);
        intent.putExtra("tableEvent", tableEvent);
        intent.putExtra("tableTitle", table);
        readyToGo = false;
        startActivity(intent);
    }

    public void add(){

        EditText Editname = (EditText)findViewById(R.id.name);
        name = Editname.getText().toString();

        EditText Editdate= (EditText)findViewById(R.id.date);
        date = Editdate.getText().toString();

        EditText Editcomment = (EditText)findViewById(R.id.comment);
        comment = Editcomment.getText().toString();

        new dbAddEvent().execute();
    }

    private class dbAddEvent extends AsyncTask<Void, String, Void> {
        protected Void doInBackground(Void... params) {
            try {
                Class.forName("org.postgresql.Driver");

                String url = "jdbc:postgresql://192.168.43.44:5432/testDB";
                String user = "postgres";
                String passwd = "postgres";

                Connection conn = DriverManager.getConnection(url, user, passwd);
                Statement state = conn.createStatement();

                // TODO gestion de la date
                state.execute("INSERT INTO event(nom, date, commentaire) VALUES ('" + name + "', '" + date + "', '" + comment + "');");

                ResultSet r = state.executeQuery("SELECT COUNT(*) AS rowcount FROM event");
                r.next();
                int count = r.getInt("rowcount");

                // L'objet ResultSet contient le résultat de la requête SQL
                ResultSet result = state.executeQuery("SELECT * FROM event");

                // On récupère les MetaData
                ResultSetMetaData resultMeta = result.getMetaData();

                table = new String[resultMeta.getColumnCount()];

                for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                    table[i-1] = resultMeta.getColumnName(i).toUpperCase();

                tableEvent = new String[count];

                int j = 0;
                while (result.next()) {
                    tableEvent[j] = "";
                    for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                        tableEvent[j] += result.getObject(i).toString() + "&";
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
