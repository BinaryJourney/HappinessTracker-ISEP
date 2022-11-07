package isep.fr.moneytracker.Objects;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import isep.fr.moneytracker.Tools.DataManager;

/**
 * This class define the attributes of the History object
 */
public class History {
    private List<Day> dayList = new ArrayList<>();

    /**
     * Gets history from a jsonfile.
     * The objectives is to retrieve the data contains inside a JSONObject, by getting the name of the attributes.
     *
     * @param activity the activity
     * @throws JSONException the json exception
     * @throws IOException   the io exception
     */
    public void getHistory(Activity activity) throws JSONException, IOException {
        DataManager dataManager = new DataManager();
        JSONObject historyJsonObject = dataManager.getJson(activity, "happinessHistory.json");

        JSONArray dayJsonArray = new JSONArray();
        JSONObject dayJsonObject = new JSONObject();

        JSONArray taskJsonArray = new JSONArray();
        JSONObject taskJsonObject = new JSONObject();

        dayJsonArray = historyJsonObject.getJSONArray("dayList");
        List<Task> taskList = new ArrayList<>();

        for(int i=0; i<dayJsonArray.length(); i++){ //Iterate on the JSONArray to get every Day object contains in history

            dayJsonObject = dayJsonArray.getJSONObject(i);
            taskJsonArray = dayJsonObject.getJSONArray("taskList");

            for(int j=0; j<taskJsonArray.length(); j++){ //Iterate on the JSONArray to get every Task object contains in history
                taskJsonObject = taskJsonArray.getJSONObject(j);
                Task task = new Task(taskJsonObject.getBoolean("done"), taskJsonObject.getString("name"), taskJsonObject.getString("description"), taskJsonObject.getString("duTime"));
                taskList.add(task);
            }
            Day day = new Day(dayJsonObject.getBoolean("dayDone"), dayJsonObject.getDouble("happiness"), dayJsonObject.getString("date"), taskList, dayJsonObject.getString("daySummary"));

            taskList = new ArrayList<>();

            dayList.add(day);
        }
    }

    /**
     * Add day.
     *
     * @param day the day
     */
    public void addDay(Day day){
        this.dayList.add(day);
    }

    /**
     * Save every Day object contain in history to a .json history file.
     * We convert each Day to a JSONObject before adding them to a JSONArray.
     *
     * @param activity the activity
     * @throws JSONException the json exception
     * @throws IOException   the io exception
     */
    public void saveHistory(Activity activity) throws JSONException, IOException {
        DataManager dataManager = new DataManager();
        JSONObject historyJsonObject = new JSONObject();

        JSONArray dayJsonArray = new JSONArray();
        JSONObject dayJsonObject = new JSONObject();

        JSONArray taskJsonArray = new JSONArray();
        JSONObject taskJsonObject = new JSONObject();

        List<Task> taskList = new ArrayList<>();
        for(int i=0; i<dayList.size(); i++){
            dayJsonObject = new JSONObject();
            dayJsonObject.put("dayDone", dayList.get(i).isDayDone());
            dayJsonObject.put("happiness", dayList.get(i).getHappiness());
            dayJsonObject.put("date", dayList.get(i).getDate());
            dayJsonObject.put("daySummary", dayList.get(i).getDaySummary());

            taskList = dayList.get(i).getTaskList();
            for(Task task:taskList){
                taskJsonObject = new JSONObject();
                taskJsonObject.put("done", task.isDone());
                taskJsonObject.put("name", task.getName());
                taskJsonObject.put("description", task.getDescription());
                taskJsonObject.put("duTime", task.getDuTime());
                taskJsonArray.put(taskJsonObject);
            }
            dayJsonObject.put("taskList", taskJsonArray);

            taskJsonArray = new JSONArray();

            dayJsonArray.put(dayJsonObject);
        }

        historyJsonObject.put("dayList", dayJsonArray);

        System.out.println("history"+historyJsonObject);

        dataManager.saveJson(historyJsonObject, activity, "happinessHistory.json");
    }

    /**
     * Gets day list.
     *
     * @return the day list
     */
    public List<Day> getDayList() {
        return dayList;
    }

    /**
     * Sets day list.
     *
     * @param dayList the day list
     */
    public void setDayList(List<Day> dayList) {
        this.dayList = dayList;
    }
}
