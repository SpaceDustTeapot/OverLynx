package com.example.spacedust.overlynx;

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
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class Board extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        msg = new String[1000];
        threadId = new int[1000];
        postCount = new int[1000];
        fileCount = new int[1000];
        subject = new String[1000];
        locked = new boolean[1000];
        pinned = new boolean[1000];
        cyclic = new boolean[1000];
        thumb = new String[1000];
        autoSage = new boolean[1000];
        setUp();

    }

    void setUp()
    {
        Log.d("WHY NOT WORKING?","MEME");
        Lay = (TableLayout) findViewById(R.id.board);
        //int SelectedID = getIntent().getIntExtra("SelectedID",0);
        String SelectedID = getIntent().getStringExtra("SelectedID");
        Chan = SelectedID;
        getChanAndBo(Chan);
        setTitle("/"+Bor+"/");
        getBoard(Chan,Bor);

        for(int i =0;i<=bPtr;i++)
        {

            //TableLayout Lay = (TableLayout) findViewById(R.id.Main);
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView entry = new TextView(this);
            //build text
           // String Bldstring = "/" + uri[i] + "/ -" + Boardname[i];
            entry.setText(msg[i]);
            Log.d("text",msg[i]);
            entry.setTextSize(20.f);
            entry.setPadding(entry.getPaddingLeft(), 5, entry.getPaddingRight(), 15);
            row.setId(i);
            row.setOnClickListener(this);
            row.addView(entry);
            Lay.addView(row);

        }
        //TableLayout.addview("Testing!");
    }
    String dom;
    TableLayout Lay;
    String Chan;
    String Bor;
    serviceHandler hand;
    String[] msg;
    int[] threadId;
    int[] postCount;
    int[] fileCount;
    String[] subject;
    boolean[] locked;
    boolean[] pinned;
    boolean[] cyclic;
    String[] thumb;
   // Date[] date; ?
    boolean[] autoSage;
    int bPtr;

    void getChanAndBo(String longString)
    {
        int len = longString.length();

        boolean flag = false;
        for(int i = 0;i<len;i++)
        {
            String temp = longString.substring(i,i+1);

            if(flag == false && new String(".").equals(temp))
            {
                Chan = longString.substring(0,i);
                boolean flag2 =false;
                flag = true;
                for(int k =0;k<len;k++)
                {
                    String temp2 = longString.substring(k,k+1);
                    if(flag2 == false && new String(",").equals(temp2))
                    {
                        Bor = longString.substring(i + 1, k);
                        dom = longString.substring(k + 1, len);
                        flag2 = true;
                    }
                }
            }
        }
    }

    void getBoard(String nam,String bo)
    {
        Log.d("serviceHand","making serviceHandler intance");
        hand = new serviceHandler();
        Log.d("serviceHand","Making a request");
        hand.makeRequest(nam,1,bo,dom);
        parseTheJson(Chan);

    }

    public void parseTheJson(String chanName)
    {
        //   Scanner scan
        String toBeParsed =  ScanFile();
        // readFileToString();

        try {
         //   JSONObject Board = new JSONObject(toBeParsed);
            //JSONArray jasonArray =  Board.optJSONArray("");
             JSONArray jasonArray = new JSONArray(toBeParsed);
            for(int i=0; i<jasonArray.length();i++)
            {
                JSONObject jsonObject = jasonArray.getJSONObject(i);
                bPtr = i;
             //   uri[i] = jsonObject.optString("boardUri").toString();
              //  Boardname[i] = jsonObject.optString("boardName").toString();
              //  boardDesc[i] = jsonObject.optString("boardDescription").toString();

                msg[i] = jsonObject.optString("message");
                threadId[i] = jsonObject.optInt("threadId");
                postCount[i] = jsonObject.optInt("postCount");
                fileCount[i] = jsonObject.optInt("fileCount");
                subject[i] = jsonObject.optString("subject").toString();
                locked[i] = jsonObject.optBoolean("locked");
                pinned[i] = jsonObject.optBoolean("pinned");
                cyclic[i] = jsonObject.optBoolean("cyclic");
                thumb[i] = jsonObject.optString("thumb").toString();
                autoSage[i] = jsonObject.optBoolean("autosage");
            }

        }catch(JSONException e)
        {

        }

    }

    String ScanFile()
    {
        Log.d("SCANFILE","IN SCAN FILE");
        File fi = Environment.getExternalStorageDirectory();
        Log.d("CHAN",Chan);
        String fileLoc = fi.getPath();
        fileLoc = fileLoc + "/overlynx/";
    //    fileLoc = fileLoc + hand.retBoardName(Chan);
        fileLoc = fileLoc + Chan + "/" + Bor;
        fileLoc = fileLoc + "/threadlist.lynx";
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
            //    String meme = new String(b, StandardCharsets.UTF_8);
                String meme = new String(b);
                Log.d("meme",meme);
               // return new String (b, StandardCharsets.UTF_8);
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

    @Override
    public void onClick(View v)
    {
        Log.d("Onclick", "CALLBACK WORKS!");
        int returnedVal = v.getId();
        Log.d("V", Integer.toString(returnedVal));
        Log.d("serviceHand","calling getBoard");
      //  loadBoard(returnedVal);
        //       getBoard();
        // newText.setText("WORKED!");
        //  updateRow();
        //loadChan(returnedVal);
    }
}
