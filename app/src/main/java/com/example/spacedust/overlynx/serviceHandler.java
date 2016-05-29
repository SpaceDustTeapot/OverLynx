package com.example.spacedust.overlynx;


import android.inputmethodservice.Keyboard;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import java.net.HttpURLConnection;

import org.json.JSONObject;

/**
 * Created by spacedust on 25/05/16.
 */
//used http://www.androidhive.info/2012/01/android-json-parsing-tutorial/


public class serviceHandler {


    public void makeRequest(String link,int mode,String Board) {
        //For testing sake I use spacechan.xyz
        if(mode == 0) {
            String ActURL = "http://" + link + "/boards.js?json=1";
            URL url;
            HttpURLConnection urlConnection;
            InputStream in;
//Gotta add http:// and /boards.js?=1 to link
            try {
                //   url = new URL("http://spacechan.xyz/boards.js?json=1");
                url = new URL(ActURL);
                urlConnection = (HttpURLConnection) url.openConnection();
                in = new BufferedInputStream(urlConnection.getInputStream());
                //readStream(in)
                CreateOverLynxDirectory();
                // createBoardDir("spacechan.xyz");
                //MAKES chan  DIR
                createBoardDir(link);
                getBoardList(in,0);
                urlConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();

            }

        }
        else if(mode == 1)
        {
            //Threadmode
            String ActURL = "http://" + link + "/"+Board +"/catalog.js";

            URL url;
            HttpURLConnection urlConnection;
            InputStream in;
//Gotta add http:// and /boards.js?=1 to link
            try {
                //   url = new URL("http://spacechan.xyz/boards.js?json=1");
                url = new URL(ActURL);
                urlConnection = (HttpURLConnection) url.openConnection();
                in = new BufferedInputStream(urlConnection.getInputStream());
                //readStream(in)
     //           CreateOverLynxDirectory();
                //MAKES BOARD DIR
                mkBoard(link,Board);
                // createBoardDir("spacechan.xyz");
                //createBoardDir(link);
                getBoardList(in,1);
                urlConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();

            }


        }
//    finally {
    //    urlConnection.disconnect();

  //  }

    }

    void createBoardDir(String name)
    {
        int length = name.length();
      //  Log.d("Length",Integer.toString(length));
        int Flag = 0;
        String realName = "";
        for(int i =0; i<length;i++)
        {
           // Log.d("wank","First!");
            String temp = name.substring(i,i+1);
            Log.d("temp",temp);
            if(Flag == 0 )
            {
                Log.d("flag","IN FLAG");
                if(new String(".").equals(temp))
                {
                    Flag = 1;
                    realName = name.substring(0, i);
                    Log.d("wank", realName);
                }
            }
            //Log.d("temp","Passed over the if statement");
        }
        Log.d("createBoardDir",realName);

        String boardLoc = StorageLoc + "/" + realName;
        File fi = new File(boardLoc);
        gBoardLoc = boardLoc;
        Log.d("gBoardLoc",gBoardLoc);
        if(fi.isDirectory() == false)
        {
            fi.mkdir();
        }
    }

    void mkBoard(String name,String Board)
    {
        int length = name.length();
        //  Log.d("Length",Integer.toString(length));
        int Flag = 0;
        String realName = "";
        for(int i =0; i<length;i++)
        {
            // Log.d("wank","First!");
            String temp = name.substring(i,i+1);
            Log.d("temp",temp);
            if(Flag == 0 )
            {
                Log.d("flag","IN FLAG");
                if(new String(".").equals(temp))
                {
                    Flag = 1;
                    realName = name.substring(0, i);
                    Log.d("wank", realName);
                }
            }
            //Log.d("temp","Passed over the if statement");
        }
        Log.d("createBoardDir",realName);

        String boardLoc = StorageLoc + "/" + realName;
        boardLoc = boardLoc + "/" + Board;
        File fi = new File(boardLoc);
        gBoardLoc = boardLoc;
        Log.d("gBoardLoc",gBoardLoc);
        if(fi.isDirectory() == false)
        {
            fi.mkdir();
        }

    }

   public String retBoardName(String name)
   {
       int length = name.length();
       //  Log.d("Length",Integer.toString(length));
       int Flag = 0;
       String realName = "";
       for(int i =0; i<length;i++)
       {
           // Log.d("wank","First!");
           String temp = name.substring(i,i+1);
           Log.d("temp",temp);
           if(Flag == 0 )
           {
               Log.d("flag","IN FLAG");
               if(new String(".").equals(temp))
               {
                   Flag = 1;
                   realName = name.substring(0, i);
                   Log.d("wank", realName);
               }
           }
           //Log.d("temp","Passed over the if statement");
       }
       return realName;

   }

    void getBoardList(InputStream in,int mode)
    {
        if(mode == 0)
        {
            gBoardLoc = gBoardLoc + "/boardlist.lynx";
        }
         else if(mode == 1)
        {
            gBoardLoc = gBoardLoc + "/threadlist.lynx";
        }

        File fi = new File(gBoardLoc);
        Log.d("meme",gBoardLoc);
        //check file existance
        if(fi.exists() == false)
        {
            try {
                fi.createNewFile();
            } catch(IOException e)
            {
                e.printStackTrace();
            }
        }

        writeToFile(in,fi);
    }

    void CreateOverLynxDirectory()
    {
        File fi;
        String FileLoc;
        //fi.getFile
        //writeToFile(in,fi)
        fi = Environment.getExternalStorageDirectory();
        FileLoc = fi.getPath();
        FileLoc = FileLoc + "/overlynx";
        StorageLoc = FileLoc;

        //Log.d("seviceHand",link);
        Log.d("serviceHand",FileLoc);
        fi = new File(FileLoc);
        Log.d("serviceHand",fi.getPath());

        if(fi.isDirectory() == false)
        {
            fi.mkdir();
        }
    }


    void writeToFile(InputStream in, File fi)
    {
        try {
            OutputStream out = new FileOutputStream(fi);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.close();
        }
        catch (IOException e)
            {
                 e.printStackTrace();
            }
    }


    String StorageLoc;
    String board;
    String gBoardLoc;
}