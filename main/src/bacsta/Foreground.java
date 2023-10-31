package bacsta;

import allDatabase.MovieDatabase;
import allDatabase.ShowtimeDatabase;
import allDatabase.TheaterDatabase;
import allDatabase.UserDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Foreground extends Users {
    MovieDatabase movieDatabase=new MovieDatabase();
    ShowtimeDatabase showtimeDatabase=new ShowtimeDatabase();
    TheaterDatabase theaterDatabase=new TheaterDatabase();

    public boolean listAllFilmInfo(){
        System.out.println("----------列出所有正在上映的影片信息----------");
        movieDatabase.allMovie();
        return true;
    }

    public boolean listAllSessionsInfo(){
        System.out.println("----------列出所有场次的信息----------");
        showtimeDatabase.allShowtime();
        return true;
    }

    public boolean listSessionInfo(){
        String tempTitle;
        List<Showtime> filteredShowtime = new ArrayList<>();
        System.out.println("----------列出指定电影和场次的信息----------");
        while(true){
            System.out.println("请输入影片名称:");
            Scanner reader = new Scanner(System.in);
            tempTitle = reader.next();
            for (int i = 0; i < ShowtimeDatabase.showtimes.size(); i++) {
                if (ShowtimeDatabase.showtimes.get(i).getMovie().getTitle().equals(tempTitle)) {
                    filteredShowtime.add(ShowtimeDatabase.showtimes.get(i));
                }
            }
            if(filteredShowtime.isEmpty()){
                System.out.println("该电影没有排场次");
            }
            else{
                for (Showtime showtime : filteredShowtime) {
                    Theater targetTheater = theaterDatabase.findTheaterByID(showtime.getTheaterID());
                    System.out.println("-----------------------------");
                    System.out.println("所在放映厅: " + targetTheater.getTheaterName());
                    System.out.println("放映厅座位情况:");
                    // 打印座位布局
                    String[][] seatLayout = targetTheater.getSeatPosition();
                    for (int i = 0; i < seatLayout.length; i++) {
                        for (int j = 0; j < seatLayout[i].length; j++) {
                            System.out.print(seatLayout[i][j] + " ");
                        }
                        System.out.println();
                    }
                    System.out.println("场次时间: " + showtime.getShowTime());
                    System.out.println("场次价格: " + showtime.getPrice());
                    System.out.println("影片: " + showtime.getMovie().getTitle());
                    System.out.println("-----------------------------");
                }
                break;
            }
        }
        return true;
    }

    public boolean sellTickets(){
        String ticketID;
        String theaterName;
        String showTime;
        String duration;
        String title;
        String tempTime;
        String tempTheaterName;
        String tempNameOrNum;
        Theater targetTheater;
        Showtime targetShowtime;
        double targetPrice;
        int line=-1;
        int column=-1;
        int[] seatID={-1,-1};
        Users targetUser = new Customer();
        Scanner reader = new Scanner(System.in);
        System.out.println("----------售票----------");
        System.out.println("请输入将售出电影的片名：" );
        title=reader.next();
        while(true){
            System.out.println("请输入对应场次所在放映厅编号:");
            tempTheaterName = reader.next();
            System.out.println("请输入对应场次放映时间:");
            Scanner readerT = new Scanner(System.in);
            tempTime = readerT.nextLine();
            targetShowtime=showtimeDatabase.findShowtimeByTheaterAndTime(tempTheaterName, tempTime);
            if (targetShowtime==null) {
                System.out.println("该场次不存在");
            }
            else {
                System.out.println("请输入顾客的用户名/手机号:");
                tempNameOrNum = reader.next();
                targetUser = userDatabase.findUserByName(tempNameOrNum);
                if (targetUser == null) {
                    targetUser = userDatabase.findUserByPhone(tempNameOrNum);
                }
                if (targetUser != null ) {
                    targetTheater = theaterDatabase.findTheaterByID(targetShowtime.getTheaterID());
                    System.out.println("该放映厅座位情况:");
                    // 打印座位布局
                    String[][] seatLayout = targetTheater.getSeatPosition();
                    for (int i = 0; i < seatLayout.length; i++) {
                        for (int j = 0; j < seatLayout[i].length; j++) {
                            System.out.print(seatLayout[i][j] + " ");
                        }
                        System.out.println();
                    }
                    System.out.println("-----------预定座位-----------:");
                    System.out.println("请选择座位排数:");
                    line = reader.nextInt();
                    System.out.println("请选择座位座数:");
                    column = reader.nextInt();
                    seatID[0] = line;
                    seatID[1] = column;
                    if (targetTheater.findSeatByID(seatID) != null) {
                        Customer customer = (Customer) targetUser;//强制类型转换
                        System.out.println("顾客为" + customer.getLV() + ",应付款金额为：" + customer.payDiscount(customer, targetShowtime.getPrice()));
                        System.out.print("实付款金额为：");
                        targetPrice = reader.nextDouble();
                        customer.setAccumulatedConsumptionAmount(targetPrice);
                        customer.setCumulativeConsumption(1);
                        // 存储影票相关信息
                        ticketID = customer.generateTicketID();
                        theaterName = targetTheater.getTheaterName();
                        showTime = targetShowtime.getShowTime();
                        title = targetShowtime.getMovie().getTitle();
                        duration = targetShowtime.getMovie().getDuration();
                        //存储影票信息
                        Ticket ticket = new Ticket(ticketID, theaterName, showTime, duration, title,seatID);
                        customer.ticketDatabase.addTicket(ticket);
                        LocalDateTime localDateTime = LocalDateTime.now();
                        String purchaseTime = localDateTime.toString();
                        //设置购票记录
                        Customer.TicketHistory ticketHistory = new Customer.TicketHistory(ticketID, purchaseTime, theaterName, showTime, title, duration, seatID);
                        customer.purchaseHistory.add(ticketHistory);
                        targetTheater.setSeat(seatID);
                        System.out.println("此次购票信息为：");
                        System.out.println("电影票ID编号" + ticketID);
                        System.out.println("放映厅: " + theaterName);
                        System.out.println("放映时间: " + showTime);
                        System.out.println("片名: " + title);
                        System.out.println("片长: " + duration );
                        System.out.println("座位号: " + line + "排" + column + "座");
                    }
                    break;
                } else {
                    System.out.println("用户名/手机号错误或该用户不为影院用户");
                }
            }
        }

        return true;
    }
}

