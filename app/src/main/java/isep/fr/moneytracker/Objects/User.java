package isep.fr.moneytracker.Objects;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import isep.fr.moneytracker.R;
import isep.fr.moneytracker.Tools.DataManager;

/**
 * This class regroup all the data of the user. It is primarily used to stock the currentDay object before saving it to the history
 */
public class User {
    private String name;
    private Day currentDay;

    /**
     * Instantiates a new User.
     */
    public User(){
        this.name = "userName";
        this.currentDay = new Day();
    }

    /**
     * Instantiates a new User by checking if a userProfile.json was previously created. This method is retrieve the currentDay multiples times
     *
     * @param activity the activity
     * @throws JSONException the json exception
     * @throws IOException   the io exception
     */
    public User(Activity activity) throws JSONException, IOException {

        DataManager dataManager = new DataManager();
        JSONObject userJsonObject = dataManager.getJson(activity, "userProfile.json"); //Rerieve the JSONObject
        JSONObject dayJsonObject = new JSONObject();
        JSONArray taskJsonArray = new JSONArray();
        JSONObject taskJsonObject = new JSONObject();

        //System.out.println(userJsonObject);

        this.name = userJsonObject.getString("name"); //get the different attributes of the user by calling the name of the attributes inside the JSONObject

        dayJsonObject = userJsonObject.getJSONObject("currentDay");
        taskJsonArray = dayJsonObject.getJSONArray("taskList"); //Fetch the task list as a JSONARRAY, a well suited type because you can get element based on indexes.

        List<Task> taskList = new ArrayList<>();
        for(int i=0; i<taskJsonArray.length(); i++){
            taskJsonObject = taskJsonArray.getJSONObject(i);
            Task task = new Task(taskJsonObject.getBoolean("done"), taskJsonObject.getString("name"), taskJsonObject.getString("description"), taskJsonObject.getString("duTime")); //Create a new task then add it to the global task list.
            taskList.add(task);
        }

        this.currentDay = new Day(dayJsonObject.getBoolean("dayDone"), dayJsonObject.getDouble("happiness"), dayJsonObject.getString("date"), taskList, dayJsonObject.getString("daySummary")); //Finally create the currentDay object based of every data imported from the JSONFile.

    }

    /**
     * Save user inside a JSONObject before saving it to a .json file.
     * This method is very useful to create a wel structured architecture of the saved data.
     *
     * @param activity the activity
     * @throws JSONException the json exception
     * @throws IOException   the io exception
     */
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
            taskJsonObject = new JSONObject();
            taskJsonObject.put("done", task.isDone());
            taskJsonObject.put("name", task.getName());
            taskJsonObject.put("description", task.getDescription());
            taskJsonObject.put("duTime", task.getDuTime());
            taskJsonArray.put(taskJsonObject);
        }
        dayJsonObject.put("taskList", taskJsonArray);

        userJsonObject.put("name", this.name);
        userJsonObject.put("currentDay", dayJsonObject);

        //System.out.println(userJsonObject);

        dataManager.saveJson(userJsonObject, activity, "userProfile.json");
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets current day.
     *
     * @return the current day
     */
    public Day getCurrentDay() {
        return currentDay;
    }

    /**
     * Sets current day.
     *
     * @param currentDay the current day
     */
    public void setCurrentDay(Day currentDay) {
        this.currentDay = currentDay;
    }
}
