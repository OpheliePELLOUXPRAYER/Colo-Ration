package com.coloration.colo_ration_android;

import android.content.Intent;
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

    private int nb = 3;
    private String txt = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_coloration);
        testDB();
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
        intent.putExtra("nb", nb);
        intent.putExtra("name", txt);
        startActivity(intent);
    }

    public void testDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);
            /* System.out.println("Databaseection success"); */

            String result = "Database connection success\n";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from test");
            ResultSetMetaData rsmd = rs.getMetaData();

            while(rs.next()) {
                result += rsmd.getColumnName(1) + ": " + rs.getInt(1) + "\n";
                result += rsmd.getColumnName(2) + ": " + rs.getString(2) + "\n";
            }
            txt += result;
            //tv.setText(result);
        }
        catch(Exception e) {
            e.printStackTrace();
            txt = e.toString();
            //tv.setText(e.toString());
        }

    }
}
