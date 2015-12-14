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

public class expenseForm extends AppCompatActivity {

    private String[] tableExpense;
    private String[] table;

    private String name;
    private String price;
    private String comment;

    private boolean readyToGo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_form);
    }

    public void addClick(View view) {
        add();
        while(!readyToGo) {}
        Intent intent = new Intent(expenseForm.this, expenseList.class);
        intent.putExtra("tableExpense", tableExpense);
        intent.putExtra("tableTitle", table);
        readyToGo = false;
        startActivity(intent);
    }

    public void add(){

        EditText Editname = (EditText)findViewById(R.id.name);
        name = Editname.getText().toString();

        EditText Editprice= (EditText)findViewById(R.id.price);
        price = Editprice.getText().toString();

        EditText Editcomment = (EditText)findViewById(R.id.comment);
        comment = Editcomment.getText().toString();

        new dbAdExpense().execute();
    }

    private class dbAdExpense extends AsyncTask<Void, String, Void> {
        protected Void doInBackground(Void... params) {
            try {
                Class.forName("org.postgresql.Driver");

                String url = "jdbc:postgresql://192.168.43.44:5432/testDB";
                String user = "postgres";
                String passwd = "postgres";

                Connection conn = DriverManager.getConnection(url, user, passwd);
                Statement state = conn.createStatement();

                // TODO revoir price
                state.execute("INSERT INTO expense(nom, prix, commentaire) VALUES ('" + name + "', '" + Float.valueOf(price) + "', '"  + comment +"');");

                ResultSet r = state.executeQuery("SELECT COUNT(*) AS rowcount FROM expense");
                r.next();
                int count = r.getInt("rowcount");

                // L'objet ResultSet contient le résultat de la requête SQL
                ResultSet result = state.executeQuery("SELECT * FROM expense");

                // On récupère les MetaData
                ResultSetMetaData resultMeta = result.getMetaData();

                table = new String[resultMeta.getColumnCount()];

                for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                    table[i-1] = resultMeta.getColumnName(i).toUpperCase();

                tableExpense = new String[count];

                int j = 0;
                while (result.next()) {
                    tableExpense[j] = "";
                    for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                        tableExpense[j] += result.getObject(i).toString() + "&";
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
