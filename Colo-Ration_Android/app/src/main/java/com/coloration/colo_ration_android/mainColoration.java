package com.coloration.colo_ration_android;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class mainColoration extends AppCompatActivity {

    private Button supply;

    private static final String url = "jdbc:mysql://sql2.olympe.in:3306/sn8lkdvu";
    private static final String user = "sn8lkdvu"; // Coloration
    private static final String pass = "mm2pCre21";

    private String[] table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_coloration);
        new dbConnection().execute();
        /*supply = (Button)findViewById(R.id.supply);
        supply.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(mainColoration.this, com.coloration.colo_ration_android.supplyList.class);
                startActivity(intent);
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_coloration, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void supplyClick(View view) {
        Intent intent = new Intent(mainColoration.this, supplyList.class);
        intent.putExtra("table", table);
        startActivity(intent);
    }

    private class dbConnection extends AsyncTask<Void, String, Void> {
        protected Void doInBackground(Void... params) {
            try {

                Class.forName("org.postgresql.Driver");

                String url = "jdbc:postgresql://192.168.1.24:5432/testDB";
                String user = "postgres";
                String passwd = "postgres";

                Connection conn = DriverManager.getConnection(url, user, passwd);
                Statement state = conn.createStatement();

                //state.execute("INSERT INTO roommate(\"Id\", \"Name\", \"Surname\") VALUES (4,'{b}', '{b}');");

                ResultSet r = state.executeQuery("SELECT COUNT(*) AS rowcount FROM roommate");
                r.next();
                int count = r.getInt("rowcount");

                // L'objet ResultSet contient le résultat de la requête SQL
                ResultSet result = state.executeQuery("SELECT * FROM roommate");

                // On récupère les MetaData
                ResultSetMetaData resultMeta = result.getMetaData();


                // On affiche le nom des colonnes

                for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                    System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");

                table = new String[count];

                int j = 0;
                while (result.next()) {
                    table[j] = new String();
                    table[j] = "";
                    for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                        table[j] += "\t" + result.getObject(i).toString() + "\t |";
                    j++;

                }
                result.close();
                state.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values){
        }

        @Override
        protected void onPreExecute() {
        }
        @Override
        protected void onPostExecute(Void result) {
        }
    }

}


