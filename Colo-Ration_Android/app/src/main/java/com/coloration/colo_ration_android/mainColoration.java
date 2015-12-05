package com.coloration.colo_ration_android;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
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

    private String[] table;
    private String[] tableTask;
    private String[] tableExpense;
    private String[] tableEvent;
    private String[] tableRoommate;
    private String[] tableSupply;

    private boolean readyToGo = false;

    // Connection to db
    Statement state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_coloration);
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
        new dbConnection().execute();
        new dbSupply().execute();
        while(!readyToGo) {}
        Intent intent = new Intent(mainColoration.this, supplyList.class);
        intent.putExtra("tableSupply", tableSupply);
        intent.putExtra("tableTitle", table);
        readyToGo = false;
        startActivity(intent);
    }

    public void taskClick(View view) {
        new dbConnection().execute();
        new dbTask().execute();
        while(!readyToGo) {}
        Intent intent = new Intent(mainColoration.this, taskList.class);
        intent.putExtra("tableTask", tableTask);
        intent.putExtra("tableTitle", table);
        readyToGo = false;
        startActivity(intent);
    }

    public void expenseClick(View view) {
        new dbConnection().execute();
        new dbExpense().execute();
        while(!readyToGo) {}
        Intent intent = new Intent(mainColoration.this, expenseList.class);
        intent.putExtra("tableExpense", tableExpense);
        intent.putExtra("tableTitle", table);
        readyToGo = false;
        startActivity(intent);
    }

    public void eventClick(View view) {
        new dbConnection().execute();
        new dbEvent().execute();
        while(!readyToGo) {}
        Intent intent = new Intent(mainColoration.this, eventList.class);
        intent.putExtra("tableEvent", tableEvent);
        intent.putExtra("tableTitle", table);
        readyToGo = false;
        startActivity(intent);
    }

    public void roommateClick(View view) {
        new dbConnection().execute();
        new dbRoommate().execute();
        while(!readyToGo) {}
        Intent intent = new Intent(mainColoration.this, roommateList.class);
        intent.putExtra("tableRoommate", tableRoommate);
        intent.putExtra("tableTitle", table);
        readyToGo = false;
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
                state = conn.createStatement();
            } catch (Exception e) {
            e.printStackTrace();
            }
            return null;
        }
    }

    private class dbEvent extends AsyncTask<Void, String, Void> {
        protected Void doInBackground(Void... params) {

            try {
                ResultSet r = state.executeQuery("SELECT COUNT(*) AS rowcount FROM event");
                r.next();
                int count = r.getInt("rowcount");

                ResultSet result = state.executeQuery("SELECT * FROM event");
                ResultSetMetaData resultMeta = result.getMetaData();

                table = new String[resultMeta.getColumnCount()];

                for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                    table[i-1] = resultMeta.getColumnName(i).toUpperCase();

                tableEvent = new String[count];

                int j = 0;
                while (result.next()) {
                    tableEvent[j] = new String();
                    tableEvent[j] = "";
                    for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                        tableEvent[j] += result.getObject(i).toString() + "&";
                    j++;
                }
                result.close();
                state.close();
                readyToGo = true;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private class dbSupply extends AsyncTask<Void, String, Void> {
        protected Void doInBackground(Void... params) {

            try {
                ResultSet r = state.executeQuery("SELECT COUNT(*) AS rowcount FROM supply");
                r.next();
                int count = r.getInt("rowcount");

                ResultSet result = state.executeQuery("SELECT * FROM supply");
                ResultSetMetaData resultMeta = result.getMetaData();

                table = new String[resultMeta.getColumnCount()];

                for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                    table[i-1] = resultMeta.getColumnName(i).toUpperCase();

                tableSupply = new String[count];

                int j = 0;
                while (result.next()) {
                    tableSupply[j] = new String();
                    tableSupply[j] = "";
                    for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                        tableSupply[j] += result.getObject(i).toString() + "&";
                    j++;
                }
                result.close();
                state.close();
                readyToGo = true;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private class dbTask extends AsyncTask<Void, String, Void> {
        protected Void doInBackground(Void... params) {

            try {
                ResultSet r = state.executeQuery("SELECT COUNT(*) AS rowcount FROM task");
                r.next();
                int count = r.getInt("rowcount");

                ResultSet result = state.executeQuery("SELECT * FROM task");
                ResultSetMetaData resultMeta = result.getMetaData();

                table = new String[resultMeta.getColumnCount()];

                for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                    table[i-1] = resultMeta.getColumnName(i).toUpperCase();

                tableTask = new String[count];

                int j = 0;
                while (result.next()) {
                    tableTask[j] = new String();
                    tableTask[j] = "";
                    for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                        tableTask[j] += result.getObject(i).toString() + "&";
                    j++;
                }
                result.close();
                state.close();
                readyToGo = true;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private class dbExpense extends AsyncTask<Void, String, Void> {
        protected Void doInBackground(Void... params) {

            try {
                ResultSet r = state.executeQuery("SELECT COUNT(*) AS rowcount FROM expense");
                r.next();
                int count = r.getInt("rowcount");

                ResultSet result = state.executeQuery("SELECT * FROM expense");
                ResultSetMetaData resultMeta = result.getMetaData();

                table = new String[resultMeta.getColumnCount()];

                for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                    table[i-1] = resultMeta.getColumnName(i).toUpperCase();

                tableExpense = new String[count];

                int j = 0;
                while (result.next()) {
                    tableExpense[j] = new String();
                    tableExpense[j] = "";
                    for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                        tableExpense[j] += result.getObject(i).toString() + "&";
                    j++;
                }
                result.close();
                state.close();
                readyToGo = true;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private class dbRoommate extends AsyncTask<Void, String, Void> {
        protected Void doInBackground(Void... params) {

            try {
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
                    tableRoommate[j] = new String();
                    tableRoommate[j] = "";
                    for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                        tableRoommate[j] += result.getObject(i).toString() + "&";
                    j++;
                }
                result.close();
                state.close();
                readyToGo = true;
                } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    private class dbConnection1 extends AsyncTask<Void, String, Void> {
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
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(mainColoration.this, mainColoration.class);
            startActivity(intent);
        }

        return super.onKeyDown(keyCode, event);
    }
}


