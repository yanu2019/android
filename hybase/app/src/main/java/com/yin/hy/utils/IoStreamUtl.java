package com.yin.hy.utils;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.stream.Stream;

public class IoStreamUtl {

    //存单个对象
    public static void inObject(String fileName,int mode, Context context, Object obj){
        ObjectOutputStream oos = null;

        try{
            oos = new ObjectOutputStream(context.openFileOutput(fileName,mode));
            oos.writeObject(obj);
        }catch(IOException ioe){
            Log.e("IoStreamUtl", "writeObject:" + ioe.getMessage());
        }finally {
            try{
                if(oos != null){
                    oos.close();
                }
            }catch (IOException io){
                Log.e("IoStreamUtl", "writeObject: close stream Exception" + io.getMessage());
            }
        }

    }
    //取单个对象
    public static Object outObject(String fileName,Context context){
        ObjectInputStream ois = null;
        Object obj = null;
        try{
            ois = new ObjectInputStream(context.openFileInput(fileName));
            obj = ois.readObject();
        }catch(IOException ioex){
            Log.e("IoStreamUtl", "outObject: read faild"+ioex.getMessage());

        } catch (Exception ex){
            Log.e("IoStreamUtl", "outObject: " + ex.getMessage() );
        }finally {
            closeInputStream("IoStreamUtl","outObject",ois);
        }
        return obj;
    }

    public static void closeInputStream(String tag, String methodName, InputStream ...stream){
        try{
            for(InputStream i:stream){
                if(i!=null){
                    i.close();
                }
            }
        }catch(Exception ex){
            Log.e(tag, methodName + ": close stream exception" + ex.getMessage() );
        }
    }

    public static void closeOutputStream(String tag, String methodName, OutputStream ...stream){
        try{
            for(OutputStream o:stream){
                if(o!=null){
                    o.close();
                }
            }
        }catch(Exception ex){
            Log.e(tag, methodName + ": close stream exception" + ex.getMessage() );
        }
    }

    public static void closeWriter(String tag, String methodName, Writer...writer){
        try{
            for(Writer w:writer){
                if(w!=null){
                    w.close();
                }
            }
        }catch(Exception ex){
            Log.e(tag, methodName + ": close stream exception" + ex.getMessage() );
        }
    }

    public static void closeReader(String tag, String methodName, Reader...reader){
        try{
            for(Reader r:reader){
                if(r!=null){
                    r.close();
                }
            }
        }catch(Exception ex){
            Log.e(tag, methodName + ": close stream exception" + ex.getMessage() );
        }
    }
}
