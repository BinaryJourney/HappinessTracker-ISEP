package isep.fr.moneytracker.Objects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Day {
    private boolean dayDone;
    private double happiness; //happiness score
    private String date;
    private List<Task> taskList;
    private String daySummary;

    public Day(boolean dayDone, double happiness, String date, List<Task> taskList, String daySummary) {
        this.dayDone = dayDone;
        this.happiness = happiness;
        this.date = date;
        this.taskList = taskList;
        this.daySummary = daySummary;
    }

    public Day() {
        this.dayDone = false;
        this.happiness = 0;
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        this.date = formatter.format(Calendar.getInstance().getTime());
        this.taskList = new ArrayList<>();
        this.daySummary = "";
    }

    public void setDayDone(boolean dayDone) {
        this.dayDone = dayDone;
    }

    public void setHappiness(double happiness) {
        this.happiness = happiness;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public void addTask(Task task){
        this.taskList.add(task);
    }

    public void setDaySummary(String daySummary) {
        this.daySummary = daySummary;
    }

    public boolean isDayDone() {
        return dayDone;
    }

    public double getHappiness() {
        return happiness;
    }

    public String getDate() {
        return date;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public Task getTask(int position){
        return taskList.get(position);
    }

    public String getDaySummary() {
        return daySummary;
    }

    @Override
    public String toString() {
        return "Day{" +
                "dayDone=" + dayDone +
                ", happiness=" + happiness +
                ", date='" + date + '\'' +
                ", taskList=" + taskList +
                ", daySummary='" + daySummary + '\'' +
                '}';
    }
}
