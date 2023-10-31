package bacsta;

public class Ticket {
    private String ticketID;
    private boolean isTakeOut=false;
    private String theaterName;
    private String showTime;
    private String duration;
    private String title;
    private int[] seatID=new int[2];

    public Ticket(String ticketID,String theaterName,String showTime,String duration,String title,int[] seatID){
        this.ticketID=ticketID;
        this.theaterName=theaterName;
        this.showTime=showTime;
        this.duration=duration;
        this.title=title;
        this.seatID=seatID;
    }

    public String getTicketID() {
        return this.ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public boolean getStatus() {
        return this.isTakeOut;
    }

    public void setStatus(boolean isTakeOut) {
        this.isTakeOut = isTakeOut;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public String getTheaterName() {
        return this.theaterName;
    }

    public String getShowTime() {
        return this.showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDuration() {
        return this.duration;
    }

    public void setSeatID(int[] seatID) {
        this.seatID = seatID;
    }

    public int[] getSeatID() {
        return this.seatID;
    }
}
