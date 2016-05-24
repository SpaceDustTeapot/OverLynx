package com.example.spacedust.overlynx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class mainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        setUp();
    }

    void setUp()
    {
        loadChanList();
        Lay = (TableLayout) findViewById(R.id.Main);
        for(int i=0;i<chanListPtr;i++)
        {
            //TableLayout Lay = (TableLayout) findViewById(R.id.Main);
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView entry = new TextView(this);
            entry.setText(chanList[i]);
            row.addView(entry);
            Lay.addView(row);
        }

        //TableLayout.addview("Testing!");
    }

    void loadChanList()
    {
        //THIS IS TEMPORY. In the future there be a list that that the user can add
        chanList = new String[100];
        chanList[0] = "spacechan.xyz";
        chanList[1] = "Lynxhub.com";
        chanList[2] = "freech.net";
        chanListPtr = 3;
    }

    String[] chanList;
    int chanListPtr;
    TableLayout Lay;
}
