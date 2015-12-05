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

public class supplyForm extends AppCompatActivity {

    private String[] tableSupply;

    private String name;
    private String quantity;
    private String comment;

    private boolean readyToGo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supply_form);
    }

    public void addClick(View view) {
        add();
        while(!readyToGo) {}
        Intent intent = new Intent(supplyForm.this, supplyList.class);
        intent.putExtra("tableSupply", tableSupply);
        readyToGo = false;
        startActivity(intent);
    }

    public void add(){

        EditText Editname = (EditText)findViewById(R.id.name);
        name = Editname.getText().toString();

        EditText Editquantity= (EditText)findViewById(R.id.quantity);
        quantity = Editquantity.getText().toString();

        EditText Editcomment = (EditText)findViewById(R.id.comment);
        comment = Editcomment.getText().toString();

        new dbAdSupply().execute();
    }

    private class dbAdSupply extends AsyncTask<Void, String, Void> {
        protected Void doInBackground(Void... params) {
            try {
                Class.forName("org.postgresql.Driver");

                String url = "jdbc:postgresql://192.168.1.24:5432/testDB";
                String user = "postgres";
                String passwd = "postgres";

                Connection conn = DriverManager.getConnection(url, user, passwd);
                Statement state = conn.createStatement();

                state.execute("INSERT INTO supply(name, quantity, comment) VALUES ('" + name + "', '" + Integer.valueOf(quantity) + "', '"  + comment +"');");

                ResultSet r = state.executeQuery("SELECT COUNT(*) AS rowcount FROM supply");
                r.next();
                int count = r.getInt("rowcount");

                // L'objet ResultSet contient le résultat de la requête SQL
                ResultSet result = state.executeQuery("SELECT * FROM supply");

                // On récupère les MetaData
                ResultSetMetaData resultMeta = result.getMetaData();

                for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                    System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");

                tableSupply = new String[count];

                int j = 0;
                while (result.next()) {
                    tableSupply[j] = "";
                    for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                        tableSupply[j] += result.getObject(i).toString() + "&";
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
