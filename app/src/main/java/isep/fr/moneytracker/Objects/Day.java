package isep.fr.moneytracker.Objects;

import java.util.Date;
import java.util.List;

public class Day {
    private boolean dayDone;
    private float happiness; //happiness score
    private Date date;
    private List<Task> taskList;
    private String daySummary;

    public Day(boolean dayDone, float happiness, Date date, List<Task> taskList, String daySummary) {
        this.dayDone = dayDone;
        this.happiness = happiness;
        this.date = date;
        this.taskList = taskList;
        this.daySummary = daySummary;
    }

    public void setDayDone(boolean dayDone) {
        this.dayDone = dayDone;
    }

    public void setHappiness(float happiness) {
        this.happiness = happiness;
    }

    public void setDate(Date date) {
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

    public float getHappiness() {
        return happiness;
    }

    public Date getDate() {
        return date;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public String getDaySummary() {
        return daySummary;
    }
}
