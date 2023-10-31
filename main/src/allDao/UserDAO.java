package allDao;
import bacsta.Users;

public interface UserDAO{
    boolean addUser(Users user);
    boolean updateUser(Users user);
    boolean deleteUser(Users user);
    void allCineplexUser();
}
