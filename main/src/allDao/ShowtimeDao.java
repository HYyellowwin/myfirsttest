package allDao;
import bacsta.Showtime;
public interface ShowtimeDao {
    boolean addShowtime(Showtime showtime);
    boolean updateShowtime(Showtime showtime);
    boolean deleteShowtime(Showtime showtime);
    void allShowtime();
}
