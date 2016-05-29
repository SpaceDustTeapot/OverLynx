package com.example.spacedust.overlynx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Board extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
    }

    void setUp()
    {
        Lay = (TableLayout) findViewById(R.id.thread);
        //int SelectedID = getIntent().getIntExtra("SelectedID",0);
        String SelectedID = getIntent().getStringExtra("SelectedID");
        Chan = SelectedID;
        setTitle(SelectedID);
        getBoard(Chan);

        for(int i =0;i<=bPtr;i++)
        {

            //TableLayout Lay = (TableLayout) findViewById(R.id.Main);
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView entry = new TextView(this);
            //build text
            String Bldstring = "/" + uri[i] + "/ -" + Boardname[i];
            entry.setText(Bldstring);
            entry.setTextSize(20.f);
            entry.setPadding(entry.getPaddingLeft(), 5, entry.getPaddingRight(), 15);
            row.setId(i);
            row.setOnClickListener(this);
            row.addView(entry);
            Lay.addView(row);

        }
        //TableLayout.addview("Testing!");
    }
}
