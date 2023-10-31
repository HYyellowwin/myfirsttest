package allDao;
import bacsta.Movie;
public interface MovieDao {
    boolean addMovie(Movie movie);
    boolean updateMovie(Movie movie);
    boolean deleteMovie(Movie movie);
    void allMovie();
}
