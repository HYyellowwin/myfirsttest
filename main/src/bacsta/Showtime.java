package bacsta;

public class Showtime {
    private String theaterID;
    private String showTime;
    private int price;
    private Movie movie;

    public Showtime(String theaterID,String showTime,int price,Movie movie){
        this.setTheaterID(theaterID);
        this.setShowTime(showTime);
        this.setPrice(price);
        this.setMovie(movie);
    }

    public String getTheaterID() {
        return this.theaterID;
    }

    public void setTheaterID(String theaterID) {
        this.theaterID = theaterID;
    }

    public String getShowTime() {
        return this.showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Movie getMovie() {
        return this.movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public void showMovieInfo(Movie movie){
        System.out.println("影片名称: " + movie.getTitle());
        System.out.println("导演: " + movie.getDirector());
        System.out.println("主演: " + movie.getLeadingRole());
        System.out.println("剧情简介: " + movie.getSynopsis());
        System.out.println("时长: " + movie.getDuration());
    }

    public static boolean isShowtimeValid(String showtime){
        String format = "^[1-9]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])\\s+(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$";// 2021-07-28 15:16:44

        if ((showtime != null) && (!showtime.isEmpty()))
            return showtime.matches(format);

        else
            return false;
    }
}