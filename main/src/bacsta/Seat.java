package bacsta;

public class Seat {
    private int[] seatID=new int[2];
    private boolean reservedState=false;

    Seat(int[] seatID){
        this.seatID=seatID;
    }

    public void setSeatID(int[] seatID){
        this.seatID=seatID;
    }

    public int[] getSeatID(){
        return this.seatID;
    }

    public void setReservedState(boolean reservedState){
        this.reservedState=reservedState;
    }

    public boolean getReservedState(){
        return this.reservedState;
    }

    public boolean reserveSeat(){
        this.reservedState=true;
        return true;
    }

    public boolean cancelReserved(){
        this.reservedState=false;
        return true;
    }

    

}
