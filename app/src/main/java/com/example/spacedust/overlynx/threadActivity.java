package com.example.spacedust.overlynx;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class threadActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        Boardname = new String[1000];
        uri = new String[1000];
        boardDesc = new String[1000];
        setUp();
    }

    void setUp() {

        Lay = (TableLayout) findViewById(R.id.thread);
        //int SelectedID = getIntent().getIntExtra("SelectedID",0);
        String SelectedID = getIntent().getStringExtra("SelectedID");
        Chan = SelectedID;
        domain = Chan;
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

    void getBoard(String nam)
    {
        Log.d("serviceHand","making serviceHandler intance");
        hand = new serviceHandler();
        Log.d("serviceHand","Making a request");
        hand.makeRequest(nam,0,null,null);
        parseTheJson(Chan);

    }

    public void parseTheJson(String chanName)
    {
     //   Scanner scan
      String toBeParsed =  ScanFile();
        // readFileToString();

        try {
            JSONObject Board = new JSONObject(toBeParsed);
            JSONArray jasonArray =  Board.optJSONArray("boards");

            for(int i=0; i<jasonArray.length();i++)
            {
                JSONObject jsonObject = jasonArray.getJSONObject(i);
                bPtr = i;
                uri[i] = jsonObject.optString("boardUri").toString();
                Boardname[i] = jsonObject.optString("boardName").toString();
                boardDesc[i] = jsonObject.optString("boardDescription").toString();

            }

        }catch(JSONException e)
        {

        }

    }

    String ScanFile()
    {
        File fi = Environment.getExternalStorageDirectory();
        Log.d("CHAN",Chan);
        String fileLoc = fi.getPath();
       fileLoc = fileLoc + "/overlynx/";
       fileLoc = fileLoc + hand.retBoardName(Chan);
        tldLess = hand.retBoardName(Chan);
        fileLoc = fileLoc + "/boardlist.lynx";
        Log.d("fileLoc",fileLoc);
       fi = new File(fileLoc);
        Log.d("fi",Boolean.toString(fi.exists()));
        if(fi.exists() == true)
        {
            try {

                //Borrowed from stack overflow http://stackoverflow.com/questions/326390/how-do-i-create-a-java-string-from-the-contents-of-a-file
                InputStream in = new FileInputStream(fi);
                int Len =(int)fi.length();
                byte[] b  = new byte[Len];
                int len = b.length;
                int total = 0;

                while (total < len) {
                    int result = in.read(b, total, len - total);
                    if (result == -1) {
                        break;
                    }
                    total += result;
                }
                //String meme = new String(b, StandardCharsets.UTF_8);
                String meme = new String(b);
                Log.d("meme",meme);
                //return new String (b, StandardCharsets.UTF_8);
                return new String(b);
            }catch(IOException e) {
                e.printStackTrace();
            }
            Log.d("built", "ABOUT TO RETURN");
            return fileLoc;
        }
        Log.d("built","outside If");
        return fileLoc;
    }

    String domain;
    TableLayout Lay;
    String[] Boardname;
    serviceHandler hand;
    String Chan;
    String[] uri;
    String[] boardDesc;
    int bPtr;
    String tldLess;

    @Override
    public void onClick(View v)
    {
        Log.d("Onclick", "CALLBACK WORKS!");
        int returnedVal = v.getId();
        Log.d("V", Integer.toString(returnedVal));
        Log.d("serviceHand","calling getBoard");
        loadBoard(returnedVal);
 //       getBoard();
        // newText.setText("WORKED!");
        //  updateRow();
        //loadChan(returnedVal);
    }

    void loadBoard(int val)
    {
        if(bPtr < val)
        {
            Log.d("ERROR","SOMETHING WENT WRONG");
        }
        else
        {
            String chosenBoard = uri[val];
            String cho = tldLess + "." + chosenBoard + "," + domain;
            startActivity(new Intent(this,Board.class).putExtra("SelectedID",cho));
        }
    }
}