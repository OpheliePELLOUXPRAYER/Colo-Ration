package com.coloration.colo_ration_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class supplyList extends AppCompatActivity {

    int nb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supply_list);
        Intent i = getIntent();
        nb = i.getIntExtra("nb", 0);
        /*
        Button b = (Button)findViewById(R.id.name);
        b.setText(name);*/


        TableLayout tab = (TableLayout) findViewById(R.id.tab);


        for (int j = 0; j<nb; j++) {
            TableRow newRow = new TableRow(this);
            newRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            if (j%2 == 1) {
                newRow.setBackgroundColor(getResources().getColor(R.color.cyan));
            }

            TextView tv = new TextView(this);
            tv.setText("yolo");

            newRow.addView(tv);
            tab.addView(newRow);
        }
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
}
