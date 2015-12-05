package com.coloration.colo_ration_android;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class taskForm extends AppCompatActivity {

    private String[] tableTask;

    private String name;
    private String priority;
    private String comment;

    private boolean readyToGo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_form);
        Intent i = getIntent();
    }

    public void addClick(View view) {
        add();
        while(!readyToGo) {}
        Intent intent = new Intent(taskForm.this, taskList.class);
        intent.putExtra("tableTask", tableTask);
        readyToGo = false;
        startActivity(intent);
    }

    public void add(){

        EditText Editname = (EditText)findViewById(R.id.name);
        name = Editname.getText().toString();

        EditText Editpriority= (EditText)findViewById(R.id.priority);
        priority = Editpriority.getText().toString();

        EditText Editcomment = (EditText)findViewById(R.id.comment);
        comment = Editcomment.getText().toString();

        new dbAddTask().execute();

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

    private class dbAddTask extends AsyncTask<Void, String, Void> {
        protected Void doInBackground(Void... params) {

            try {
                Class.forName("org.postgresql.Driver");

                String url = "jdbc:postgresql://192.168.1.24:5432/testDB";
                String user = "postgres";
                String passwd = "postgres";

                Connection conn = DriverManager.getConnection(url, user, passwd);
                Statement state = conn.createStatement();

                state.execute("INSERT INTO task(name, priority, comment) VALUES ('" + name + "', '" + Integer.valueOf(priority) + "', '"  + comment +"');");
                //state.execute("INSERT INTO roommate(firstname, lastname, mail)VALUES('firstname(30)', 'lastname(30)', 'mail(100)'");

                ResultSet r = state.executeQuery("SELECT COUNT(*) AS rowcount FROM task");
                r.next();
                int count = r.getInt("rowcount");

                ResultSet result = state.executeQuery("SELECT * FROM task");

                ResultSetMetaData resultMeta = result.getMetaData();
                for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                    System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");

                tableTask = new String[count];

                int j = 0;
                while (result.next()) {
                    tableTask[j] = "";
                    for (int i = 1; i <= resultMeta.getColumnCount(); i++)
                        tableTask[j] += "\t" + result.getObject(i).toString() + "\t |";
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
