package isep.fr.moneytracker.Tools;


import android.app.Activity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataManager {

    public JSONObject getJson(Activity activity, String objectName) throws IOException, JSONException {

        File file = new File(activity.getFilesDir(),objectName);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        StringBuilder stringBuilder = new StringBuilder();
        String line = bufferedReader.readLine();
        while (line != null){
            stringBuilder.append(line).append("\n");
            line = bufferedReader.readLine();
        }
        bufferedReader.close();
        // This responce will have Json Format String
        String responce = stringBuilder.toString();

        JSONObject jsonObject  = new JSONObject(responce);
        //Java Object

        return jsonObject;
    }

    public void saveJson(JSONObject jsonObject, Activity activity, String objectName) throws JSONException, IOException {

        String jsonString = jsonObject.toString();

        File file = new File(activity.getFilesDir(),objectName);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(jsonString);
        bufferedWriter.close();
    }

}

