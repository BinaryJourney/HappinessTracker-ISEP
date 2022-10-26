package isep.fr.moneytracker.Objects;

import java.util.Date;

public class Task {
    private boolean done;
    private String Description;
    private Date duTime;

    public Task(boolean done, String description, Date duTime) {
        this.done = done;
        Description = description;
        this.duTime = duTime;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setDuTime(Date duTime) {
        this.duTime = duTime;
    }

    public String getDescription() {
        return Description;
    }

    public Date getDuTime() {
        return duTime;
    }
}
