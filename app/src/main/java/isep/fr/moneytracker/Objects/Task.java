package isep.fr.moneytracker.Objects;

import java.util.Date;

/**
 * This class defines the attributes of the Task object.
 */
public class Task {
    private boolean done;
    private String name;
    private String Description;
    private String duTime;

    /**
     * Instantiates a new Task.
     */
    public Task(){}

    /**
     * Instantiates a new Task with data, for example when we retrieve them from the json file.
     *
     * @param done        the done
     * @param name        the name
     * @param description the description
     * @param duTime      the du time
     */
    public Task(boolean done, String name, String description, String duTime) {
        this.done = done;
        this.name = name;
        this.Description = description;
        this.duTime = duTime;
    }

    /**
     * Is done boolean.
     *
     * @return the boolean
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Sets done.
     *
     * @param done the done
     */
    public void setDone(boolean done) {
        this.done = done;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        Description = description;
    }

    /**
     * Sets du time.
     *
     * @param duTime the du time
     */
    public void setDuTime(String duTime) {
        this.duTime = duTime;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return Description;
    }

    /**
     * Gets du time.
     *
     * @return the du time
     */
    public String getDuTime() {
        return duTime;
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
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }
}
