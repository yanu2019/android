package com.yin.hy.utils;


import android.content.Context;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class LogManager {
    private FileOutputStream outputStream;
    private BufferedWriter bufferedWriter;

    public void saveLog(Context context,String data,String fileName,int mode){
        try{
            outputStream = context.openFileOutput(fileName,mode);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(data);
        }catch(IOException ioex){
            Log.e("LogManager", "saveLog:failed" + ioex.getMessage());
        }catch (Exception ex){
            Log.e("LogManager", "saveLog: "+ex.getMessage() );
        }finally{
            IoStreamUtl.closeOutputStream("LogManager","saveLog",outputStream);
            IoStreamUtl.closeWriter("LogManager","saveLog",bufferedWriter);
        }
    }
}
