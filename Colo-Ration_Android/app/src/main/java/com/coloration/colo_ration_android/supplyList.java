package com.coloration.colo_ration_android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class supplyList extends AppCompatActivity {

    int nb = 10;
    String txt;
    String[] table;
    String[] tableTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supply_list);
        Intent i = getIntent();
        table = i.getStringArrayExtra("tableSupply");
        tableTitle = i.getStringArrayExtra("tableTitle");

        TableLayout tab = (TableLayout) findViewById(R.id.tab);
        try{
            TableRow rowTitle = new TableRow(this);
            rowTitle.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            rowTitle.setBackgroundColor(getResources().getColor(R.color.cyan));
            for (int k = 1; k < tableTitle.length; k++) {
                TextView tv1 = new TextView(this);
                tv1.setText(tableTitle[k]);
                tv1.setGravity(Gravity.CENTER);
                rowTitle.addView(tv1, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, 1));
            }
            tab.addView(rowTitle);

            for (int j = 0; j < table.length; j++) {
                TableRow newRow = new TableRow(this);
                newRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                if (j % 2 == 1) {
                    newRow.setBackgroundColor(getResources().getColor(R.color.cyan));
                }

                String[] values = table[j].split("&");

                for (int k = 1; k < values.length; k++) {
                    TextView tv = new TextView(this);
                    tv.setText("\t" + values[k].trim() + "\t");
                    tv.setGravity(Gravity.CENTER);
                    tv.setEllipsize(TextUtils.TruncateAt.END);
                    tv.setSingleLine(true);
                    tv.setMaxLines(1);
                    tv.setHorizontallyScrolling(true);
                    newRow.addView(tv, new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT, 1));
                }
                tab.addView(newRow);
            }
        }catch(NullPointerException e){

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

        if (id == R.id.action_adding) {
            addElement();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addElement(){
        Intent intent = new Intent(supplyList.this, supplyForm.class);
        startActivity(intent);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(supplyList.this, mainColoration.class);
            startActivity(intent);
        }

        return super.onKeyDown(keyCode, event);
    }

}
