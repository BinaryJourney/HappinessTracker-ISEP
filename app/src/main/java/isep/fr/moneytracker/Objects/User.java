package isep.fr.moneytracker.Objects;

import java.util.List;

public class User {
    private String name;
    private float lastThreeHappiness;
    private List<Day> dayList;

    public User(){

    }

    public float getLastThreeHappiness() {
        return lastThreeHappiness;
    }

    public List<Day> getDayList() {
        return dayList;
    }
}
