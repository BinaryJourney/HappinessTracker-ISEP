package isep.fr.moneytracker.Objects;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import isep.fr.moneytracker.Tools.DataManager;

public class User {
    private String name;
    private Day currentDay;

    public User(String name){
        this.name = name;
        this.currentDay = new Day();
    }

    public User(Activity activity) throws JSONException, IOException {

        DataManager dataManager = new DataManager();
        JSONObject userJsonObject = dataManager.getJson(activity, "userProfile.json");
        JSONObject dayJsonObject = new JSONObject();
        JSONArray taskJsonArray = new JSONArray();
        JSONObject taskJsonObject = new JSONObject();

        this.name = userJsonObject.getJSONObject("name").toString();

        dayJsonObject = userJsonObject.getJSONObject("currentDay");
        taskJsonArray = dayJsonObject.getJSONArray("taskList");


        List<Task> taskList = new ArrayList<>();
        for(int i=0; i<taskJsonArray.length(); i++){
            taskJsonObject = taskJsonArray.getJSONObject(i);
            Task task = new Task(taskJsonObject.getBoolean("done"), taskJsonObject.getString("description"), taskJsonObject.getString("duTime"));
            taskList.add(task);
        }

        this.currentDay = new Day(dayJsonObject.getBoolean("dayDone"), dayJsonObject.getDouble("happiness"), dayJsonObject.getString("date"), taskList, dayJsonObject.getString("daySummary"));


    }

    public void saveUser(Activity activity) throws JSONException, IOException {
        DataManager dataManager = new DataManager();
        JSONObject userJsonObject = new JSONObject();
        JSONObject dayJsonObject = new JSONObject();
        JSONArray taskJsonArray = new JSONArray();
        JSONObject taskJsonObject = new JSONObject();

        dayJsonObject.put("dayDone", this.currentDay.isDayDone());
        dayJsonObject.put("happiness", this.currentDay.getHappiness());
        dayJsonObject.put("date", this.currentDay.getDate());
        dayJsonObject.put("daySummary", this.currentDay.getDaySummary());

        List<Task> taskList = currentDay.getTaskList();
        for(Task task:taskList){
            taskJsonObject.put("done", task.isDone());
            taskJsonObject.put("description", task.getDescription());
            taskJsonObject.put("duTime", task.getDuTime());
            taskJsonArray.put(taskJsonObject);
        }
        dayJsonObject.put("taskList", taskJsonArray);

        userJsonObject.put("name", this.name);
        userJsonObject.put("currentDay", dayJsonObject);

        dataManager.saveJson(userJsonObject, activity, "userProfile.json");
    }

    public String getName() {
        return name;
    }

    public Day getCurrentDay() {
        return currentDay;
    }
}
