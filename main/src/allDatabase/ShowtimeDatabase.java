package allDatabase;


import bacsta.Showtime;
import allDao.ShowtimeDao;
import java.util.ArrayList;
import java.util.List;

public class ShowtimeDatabase implements ShowtimeDao {
    public static List<Showtime> showtimes = new ArrayList<>();
    TheaterDatabase theaterDatabase=new TheaterDatabase();

//    @Override
    public boolean addShowtime(Showtime showtime) {
        showtimes.add(showtime);
        return true;
    }

//    @Override
    public boolean updateShowtime(Showtime showtime) {
        int index = -1;
        if (showtimes.contains(showtime)) {
            index = showtimes.indexOf(showtime);
            showtimes.set(index, showtime);
            System.out.println("场次信息更新成功！");
            return true;
        } else {
            System.out.println("未找到该场次");
            return false;
        }
    }

//    @Override
    public boolean deleteShowtime(Showtime showtime) {
        showtimes.remove(showtime);
        return true;
    }

//    @Override
    public void allShowtime() {
        for (int i = 0; i < showtimes.size(); i++) {
            System.out.println("放映厅编号:" + showtimes.get(i).getTheaterID());
            System.out.println("放映厅:" + theaterDatabase.findTheaterByID(showtimes.get(i).getTheaterID()).getTheaterName());
            System.out.println("放映时间:" + showtimes.get(i).getShowTime());
            System.out.println("场次价格:" + showtimes.get(i).getPrice());
            System.out.println("放映影片:" + showtimes.get(i).getMovie().getTitle());
            System.out.println("\n");
        }
    }

    public Showtime findShowtimeByTheaterAndTime(String theaterID,String time) {
        for (Showtime showtime : showtimes) {
            if (showtime.getTheaterID() == null||showtime.getShowTime()==null)
                continue;
            else if (showtime.getTheaterID().equals(theaterID)&&showtime.getShowTime().equals(time)) {
                return showtime;
            }
        }
        return null;
    }
}
