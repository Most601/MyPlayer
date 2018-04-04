package metaextract.nkm.com.myplayer;

/**
 * Created by Most601 on 15/12/2017.
 */


import android.os.Environment;
import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileManager {

    private static final String TAG = "FileManager";

    public String filePath;
    private File file ;



    //-----------------------------------------------------------------------------------------------




    //-------------------Aviv--------------------
    public FileManager(String Filename , boolean append ) {
        boolean a = isExternalStorageWritable();
        if (a) {
            Log.d(TAG, "External Storage Writable");
        } else {
            Log.d(TAG, "External Storage not Writable");
        }
        File outputStream1 = getPublicPicturesDirectory("Log");
        filePath = outputStream1.getPath();
        file = new File(filePath, Filename+".csv"  );
        if(file.length() == 0) {
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(file, append));
                bw.write(String.valueOf(Filename + "\n"));
                bw.close();
                Log.d(TAG, "File created - "+Filename);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //------------ Aviv CSV----------------------------

    public void writeInternalFileCsvNewLINE( String content, boolean   append){
        try {
            FileWriter _file;
            _file = new FileWriter(file.getAbsoluteFile(),append);
            _file.append('\n');
            _file.append(content+",");
            _file.close();
        }catch (Exception e){

        }


    }

    public void writeInternalFileCsvSameLine(  String content, boolean   append) {
        try {
            FileWriter _file;
            _file = new FileWriter(file.getAbsoluteFile(),append);
            _file.append(content+",");
            _file.close();
        }catch (Exception e){

        }
    }

    public boolean deleteFile() {
        return file.delete();
    }

    //---------------------------end Aviv-----------------------------









    //----------------- writeInternalFile String ---------------------------------------------------
    /**
     * External Storage Methods
     **/
    public void writeInternalFile(String fileName, String content, boolean append) throws IOException {
        boolean a = isExternalStorageWritable();
        if (a) {
            Log.e("FileManager ", "1111111111111111111111111111111");
        } else {
            Log.e("FileManager  ", "0000000000000000000000000000000");
        }

        //----------- הוספה שלי לעומת המקור ----------
        File outputStream1 = getPublicPicturesDirectory("Log");
        filePath = outputStream1.getPath();
        File file = new File(filePath, fileName+".csv" );
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file,append));
            bw.write(String.valueOf(content+"\n"));
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //--------------------

       // writeInternalFile(fileName, content.getBytes(), append);
    }

    //------- writeInternalFile byte[] ---------

    public void writeInternalFile(String fileName, byte[] content, boolean append) throws IOException {
        // Context.MODE_PRIVATE = 0, therefore, we don't need to explicitly specify it.
        File outputStream1 = getPublicPicturesDirectory("A");
        filePath = outputStream1.getPath() + "/" + fileName;
        FileOutputStream outputStream = new FileOutputStream(filePath, false);


        outputStream.write(content);
        outputStream.close();
    }

    //----------------- read Internal File ---------------------------------------------------------

    public String readInternalFile(String fileName) throws IOException {
        String content = "";
        boolean a = isExternalStorageReadable();
        if (a) {
            Log.e("1212121212 - ", "1212121212");
        } else {
            Log.e("00001111000000 - ", "000000011111100000");
        }

        File outputStream1 = getPublicPicturesDirectory("A");
        filePath = outputStream1.getPath() + "/" + fileName;
        FileInputStream inputStream = new FileInputStream(filePath);
        if (inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            while ((content = bufferedReader.readLine()) != null) {
                stringBuilder.append(content);
            }

            // Releasing resources.
            bufferedReader.close();
            streamReader.close();
            inputStream.close();

            content = stringBuilder.toString();
        }

        return content;
    }


    //----------------------------------------------------------------------------------------------















    //------- We check to see if there is External Storage. ----------------------------------------

    /*
       is External Storage Writable {
     */
    public static boolean isExternalStorageWritable() {
        //We question the environment of the device ( Environment.______ )
        String state = Environment.getExternalStorageState();
        //Checks whether there is External Storage and return true or false
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /*
       is External Storage Readable {
     */
    public static boolean isExternalStorageReadable() {
        //We question the environment of the device ( Environment.______ )
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    //-------------------------------------

    // Public == user-related.
    public static File getPublicPicturesDirectory(String picturesFolder) {
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), picturesFolder);
        if (!file.mkdirs()) {
            Log.d(TAG, "Directory not created");
        }
        return file;
    }
//
//    // Private == app-related.
//    public File getPrivatePicturesDirectory(String picturesFolder) {
//        File file = new File(context.getExternalFilesDir(
//                Environment.DIRECTORY_PICTURES), picturesFolder);
//        if (!file.mkdirs()) {
//            Log.e("SNAPGuidesError", "Directory not created");
//        }
//        return file;
//    }

    //----------------------------------------------------------------------------------------------


}
























//****************** /** Internal Storage המקורי של אחסון פנימי  **/  *******************************
//
//
//public class FileManager {
//
//    private Context m_context;
//
//    public FileManager(Context context) {
//        m_context = context;
//    }
//
//    //---------------------------------------------------------
//
//    //We get a string and convert it into bytes and send them to the second function
//
//    public void writeInternalFile(String fileName, String content, boolean append) throws IOException {
//        writeInternalFile(fileName, content.getBytes(), append);
//    }
//
//    public void writeInternalFile(String fileName, byte[] content, boolean append) throws IOException {
//        // Context.MODE_PRIVATE = 0, therefore, we don't need to explicitly specify it.
//        //Object writing to file
//        //Using m_context so that android will knows which application we're talking about,
//        FileOutputStream outputStream = m_context.openFileOutput(fileName, append ? Context.MODE_APPEND : Context.MODE_PRIVATE);
//        //write down the data
//        outputStream.write(content);
//        outputStream.close();
//    }
//
//    //----------------------------------------------------------
//
//    public String readInternalFile(String fileName) throws IOException {
//        String content = "";
//        //same as FileOutputStream gast now is FileInputStream
//        FileInputStream inputStream = m_context.openFileInput(fileName);
//        if (inputStream != null) {
//            InputStreamReader streamReader = new InputStreamReader(inputStream);
//            BufferedReader bufferedReader = new BufferedReader(streamReader);
//            StringBuilder stringBuilder = new StringBuilder();
//            while ((content = bufferedReader.readLine()) != null) {
//                stringBuilder.append(content);
//            }
//
//            // Releasing resources.
//            bufferedReader.close();
//            streamReader.close();
//            inputStream.close();
//
//            content = stringBuilder.toString();
//        }
//
//        return content;
//    }
//
//    public boolean deleteInternalFile(String fileName) {
//        return m_context.deleteFile(fileName);
//    }
//
//    String[] getInternalFileList() {
//        return m_context.fileList();
//    }
//
//}
//
//*************************************************************************************************