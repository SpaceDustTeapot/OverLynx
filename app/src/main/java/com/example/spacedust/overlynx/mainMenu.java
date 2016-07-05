package com.example.spacedust.overlynx;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class mainMenu extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setUp();
    }

    void setUp()
    {
        loadChanList();
        ScrollView scroll = (ScrollView) findViewById(R.id.sites);
        Lay = (TableLayout) findViewById(R.id.Main);
        for(int i=0;i<chanListPtr;i++)
        {
            //TableLayout Lay = (TableLayout) findViewById(R.id.Main);
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView entry = new TextView(this);
            entry.setText(chanList[i]);
            entry.setTextSize(20.f);
            entry.setPadding(entry.getPaddingLeft(),5,entry.getPaddingRight(),15);
            row.setId(i);
            row.setOnClickListener(this);
            row.addView(entry);
            Lay.addView(row);
           // scroll.addView(row);
           // Lay.addView(scroll);
        }

        //TableLayout.addview("Testing!");
    }

    void loadChan(int id)
    {
        if(chanListPtr < id )
        {
            Log.d("ERROR","SOMETHING WENT WRONG:Chan id bigger then list ptr");

        }
        else
        {
            Log.d("WOOHOO","switching contentView");

            SelectedID = id;
            //Switch contentview
            String ChanName = chanList[id];
            //startActivity(new Intent(this,threadActivity.class).putExtra("SelectedID",SelectedID));
            startActivity(new Intent(this,threadActivity.class).putExtra("SelectedID",ChanName));
        }
    }


    void loadChanList()
    {
        //THIS IS TEMPORY. In the future there be a list that that the user can add
        chanList = new String[100];
        chanList[0] = "spacechan.xyz";
        chanList[1] = "lynxhub.com";
        chanList[2] = "freech.net";
        chanList[3] = "bunkerchan.org";
        chanList[4] = "endchan.xyz";
        chanListPtr = 5;
    }

    void updateRow()
    {
        TableRow row = new TableRow(this);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        row.setLayoutParams(lp);
        row.addView(newText);
        Lay.addView(row);


    }


    @Override
    public void onClick(View v)
    {
        Log.d("Onclick","CALLBACK WORKS!");
        int returnedVal = v.getId();
        Log.d("V",Integer.toString(returnedVal));
        // newText.setText("WORKED!");
        //  updateRow();
        loadChan(returnedVal);
    }

    String[] chanList;
    int chanListPtr;
    TableLayout Lay;
    TextView newText;
    public int SelectedID;
}
