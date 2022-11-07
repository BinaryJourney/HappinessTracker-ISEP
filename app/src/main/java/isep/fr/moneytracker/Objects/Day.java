package isep.fr.moneytracker.Objects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * This class define the attributes of the Day object
 */
public class Day {
    private boolean dayDone;
    private double happiness; //happiness score
    private String date;
    private List<Task> taskList;
    private String daySummary;

    /**
     * Instantiates a new Day based on fetched data, for example from a .json file.
     *
     * @param dayDone    the day done
     * @param happiness  the happiness
     * @param date       the date
     * @param taskList   the task list
     * @param daySummary the day summary
     */
    public Day(boolean dayDone, double happiness, String date, List<Task> taskList, String daySummary) {
        this.dayDone = dayDone;
        this.happiness = happiness;
        this.date = date;
        this.taskList = taskList;
        this.daySummary = daySummary;
    }

    /**
     * Instantiates a new Day - useful to reset a day after it was saved to history
     */
    public Day() {
        this.dayDone = false;
        this.happiness = 0;
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        this.date = formatter.format(Calendar.getInstance().getTime());
        this.taskList = new ArrayList<>();
        this.daySummary = "";
    }

    /**
     * Sets day done.
     *
     * @param dayDone the day done
     */
    public void setDayDone(boolean dayDone) {
        this.dayDone = dayDone;
    }

    /**
     * Sets happiness.
     *
     * @param happiness the happiness
     */
    public void setHappiness(double happiness) {
        this.happiness = happiness;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Sets task list.
     *
     * @param taskList the task list
     */
    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    /**
     * Add task.
     *
     * @param task the task
     */
    public void addTask(Task task){
        this.taskList.add(task);
    }

    /**
     * Sets day summary.
     *
     * @param daySummary the day summary
     */
    public void setDaySummary(String daySummary) {
        this.daySummary = daySummary;
    }

    /**
     * Is day done boolean.
     *
     * @return the boolean
     */
    public boolean isDayDone() {
        return dayDone;
    }

    /**
     * Gets happiness.
     *
     * @return the happiness
     */
    public double getHappiness() {
        return happiness;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets task list.
     *
     * @return the task list
     */
    public List<Task> getTaskList() {
        return taskList;
    }

    /**
     * Get task task.
     *
     * @param position the position
     * @return the task
     */
    public Task getTask(int position){
        return taskList.get(position);
    }

    /**
     * Gets day summary.
     *
     * @return the day summary
     */
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
