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

public class MainPosts extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_posts);
        setUp();

    }

    void setUp()
    {
        Lay = (TableLayout) findViewById(R.id.posts);
        //int SelectedID = getIntent().getIntExtra("SelectedID",0);
        String SelectedID = getIntent().getStringExtra("SelectedID");
        //Chan = SelectedID;
        //domain = Chan;
        splice(SelectedID);
        String title = Chan + " /" + Bor + "/" + " - ID " + thri;
        setTitle(title);
        getBoard(Chan,Bor,thri);
        Log.d("Parse",msg);

        for(int i =0;i<=bPtr;i++)
        {

            //TableLayout Lay = (TableLayout) findViewById(R.id.Main);
            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            TextView entry = new TextView(this);
            //build text
          //  String Bldstring = "/" + uri[i] + "/ -" + Boardname[i];
            //entry.setText(Bldstring);
            entry.setTextSize(20.f);
            entry.setPadding(entry.getPaddingLeft(), 5, entry.getPaddingRight(), 15);
            row.setId(i);
            row.setOnClickListener(this);
            row.addView(entry);
            Lay.addView(row);

        }
    }

    void getBoard(String nam,String bo,String th)
    {
        Log.d("serviceHand","making serviceHandler intance");
        hand = new serviceHandler();
        Log.d("serviceHand","Making a request");
        hand.makeRequest(nam,2,bo,dom,th);
        parseTheJson(Chan);

    }

    void splice(String longString)
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
                      //  dom = longString.substring(k + 1, len);
                        boolean flag3 =false;
                        flag2 = true;
                        for(int l =0;l<len; l++)
                        {
                            String temp3 = longString.substring(l,l+1);
                            if(flag3 == false && new String("]").equals(temp3))
                            {
                                dom = longString.substring(k+1,l);
                                thri = longString.substring(l+1,len);
                                flag3 = true;
                            }
                        }
                    }
                }
            }
        }
    }

    public void parseTheJson(String chanName)
    {
        //   Scanner scan
        String toBeParsed =  ScanFile();
        // readFileToString();
        Log.d("parse the json","Beforetry");
        Log.d("parsed texfile",toBeParsed);
        try {
            //   JSONObject Board = new JSONObject(toBeParsed);
            //JSONArray jasonArray =  Board.optJSONArray("");
            Log.d("in try","t");
            JSONArray jasonArray = new JSONArray(toBeParsed);
            Log.d("JSONobjectcreation","reeeee");
            for(int i=0; i<jasonArray.length();i++)
            {
                Log.d("parse the json","in loop");
                JSONObject jsonObject = jasonArray.getJSONObject(i);
                bPtr = i;
                //   uri[i] = jsonObject.optString("boardUri").toString();
                //  Boardname[i] = jsonObject.optString("boardName").toString();
                //  boardDesc[i] = jsonObject.optString("boardDescription").toString();

                msg = jsonObject.optString("message");
                threadId = jsonObject.optInt("threadId");
            //    postCount[i] = jsonObject.optInt("postCount");
            //    fileCount[i] = jsonObject.optInt("fileCount");
                subject = jsonObject.optString("subject").toString();
                locked = jsonObject.optBoolean("locked");
                pinned = jsonObject.optBoolean("pinned");
           //     cyclic[i] = jsonObject.optBoolean("cyclic");
           //     thumb[i] = jsonObject.optString("thumb").toString();
                autoSage = jsonObject.optBoolean("autosage");
            }

            Log.d("parseTest",msg);
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
        fileLoc = fileLoc + Chan+"/" +Bor +"/"+thri +"/threadlist.lynx";
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

    //Somce this is op no need for an array
    String domain;
    TableLayout Lay;
    String Boardname;
    serviceHandler hand;
    String Chan;
    String uri;
    String dom;
    String Bor;
    String thri;

    String banMessage;
    //string or int?
    String signedRole;
    String id;
    String email;
    String flag;
    int threadId;
    String subject;
    String lastEditLogin;
    String msg;
    String name;
    boolean autoSage;
    boolean locked;
    boolean cylic;
    boolean pinned;


    String[] boardDesc;
    int bPtr;
    String tldLess;

    //op files
    String[] opOriginalName;
    String[] opPath;
    String[] opThumb;
    int[] opSize;
    

}
