package allDatabase;

import bacsta.Theater;

import java.util.ArrayList;
import java.util.List;

public class TheaterDatabase{
    public static List<Theater> theaters = new ArrayList<>(5);

    public Theater findTheaterByID(String targetID) {
        for (Theater theater: theaters) {
            if (theater.getTheaterID()== null);
            else if (theater.getTheaterID().equals(targetID)) {
                return theater;
            }
        }
        return null;
    }

}
