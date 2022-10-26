package isep.fr.moneytracker.Objects;

import java.util.Date;

public class Task {
    private boolean done;
    private String Description;
    private String duTime;

    public Task(boolean done, String description, String duTime) {
        this.done = done;
        Description = description;
        this.duTime = duTime;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setDuTime(String duTime) {
        this.duTime = duTime;
    }

    public String getDescription() {
        return Description;
    }

    public String getDuTime() {
        return duTime;
    }
}
