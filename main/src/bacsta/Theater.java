package bacsta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Theater {
    private String theaterID;
    private String theaterName;
    private String[][] seatPosition={
                                        {" O "," O "," O "," O "," O "," O "," O "," O "," O "," O "," O "," O "},
                                        {" O "," O "," O "," O "," O "," O "," O "," O "," O "," O "," O "," O "},
                                        {" O "," O "," O "," O "," O "," O "," O "," O "," O "," O "," O "," O "},
                                        {" O "," O "," O "," O "," O "," O "," O "," O "," O "," O "," O "," O "},                    
                                        {" O "," O "," O "," O "," O "," O "," O "," O "," O "," O "," O "," O "},
                                        {" O "," O "," O "," O "," O "," O "," O "," O "," O "," O "," O "," O "},
                                        {" O "," O "," O "," O "," O "," O "," O "," O "," O "," O "," O "," O "},
                                    };
    public List<Seat> seats = new ArrayList<>(84);

    public Theater(String theaterID,String theaterName){
        this.setTheaterID(theaterID);
        this.setTheaterName(theaterName);
    }

    public void setTheaterID(String theaterID){
        this.theaterID = theaterID;
    }

    public String getTheaterID() {
        return this.theaterID;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public String getTheaterName() {
        return this.theaterName;
    }

    public void setSeatPosition(String[][] seatPosition){
        this.seatPosition = seatPosition;
    }

    public String[][] getSeatPosition() {
        return this.seatPosition;
    }

    public boolean addSeat(Seat seat){
        seats.add(seat);
        return true;
    }

    public void setSeat(int[] seatID){
        this.seatPosition[seatID[0]-1][seatID[1]-1] = " X ";
        Seat targetSeat=findSeatByID(seatID);
        targetSeat.setReservedState(true);
        updateSeat(targetSeat);
    }

    public String getSeat() {
        return this.theaterID;
    }

    public Seat findSeatByID(int[] seatID) {
        int count=0;
        for (Seat seat : seats) {
            if (seat.getSeatID() == null);
            else if (Arrays.equals(seat.getSeatID(),seatID)&&!seat.getReservedState()) {
                count++;
                updateSeat(seat);
                return seat;
            }
        }
        if(count==0){
            System.out.println("该座位不存在或已被预订！");
        }
        return null;
    }

    public boolean releaseSeat(int[] seatID) {
        Seat seatToRelease = findSeatByID(seatID);

        if (seatToRelease != null && seatToRelease.getReservedState()) {
            seatToRelease.setReservedState(false);
            updateSeat(seatToRelease);
            return true; // 座位释放成功
        } else {
            System.out.println("座位不存在或未被预定");
            return false; // 座位释放失败
        }
    }

    public boolean updateSeat(Seat seat) {
        int index = -1;
        if (seats.contains(seat)) {
            index = seats.indexOf(seat);
            seats.set(index, seat);
            System.out.println("座位信息更新成功！");
            return true;
        } else {
            System.out.println("未找到该座位");
            return false;
        }
    }
}
