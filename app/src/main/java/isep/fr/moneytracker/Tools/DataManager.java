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

/**
 * This class is used to retrieve data from json file.
 */
public class DataManager {

    /**
     * Gets json file content then return it as a JSONObject.
     *
     * @param activity   the activity
     * @param objectName the object name
     * @return the json
     * @throws IOException   the io exception
     * @throws JSONException the json exception
     */
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

        JSONObject jsonObject  = new JSONObject(responce); //Create a JSONObject with the content of the file before returning it
        //Java Object

        return jsonObject;
    }

    /**
     * Save jsonObject into a .json file. Very useful strategy so save data as it can easily be converted into another object.
     *
     * @param jsonObject the json object
     * @param activity   the activity
     * @param objectName the object name
     * @throws JSONException the json exception
     * @throws IOException   the io exception
     */
    public void saveJson(JSONObject jsonObject, Activity activity, String objectName) throws JSONException, IOException {

        String jsonString = jsonObject.toString(); //Convert the JSONObject to string before saving it to file. Allow the app to retrieve it easily afterwards without changing the structure of the object.

        File file = new File(activity.getFilesDir(),objectName);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(jsonString);
        bufferedWriter.close();
    }

}

