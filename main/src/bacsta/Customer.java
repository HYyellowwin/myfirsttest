package bacsta;

import allDatabase.MovieDatabase;
import allDatabase.ShowtimeDatabase;
import allDatabase.TheaterDatabase;
import allDatabase.TicketDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.time.LocalDateTime;

public class Customer extends Users {
    private String LV=null;
    private double accumulatedConsumptionAmount=0;//累计消费总金额
    private int cumulativeConsumption=0; //累计消费次数
    public List<TicketHistory> purchaseHistory = new ArrayList<>();//类型为TicketHistory的链表
    MovieDatabase movieDatabase=new MovieDatabase();
    ShowtimeDatabase showtimeDatabase=new ShowtimeDatabase();
    TheaterDatabase theaterDatabase=new TheaterDatabase();
    TicketDatabase ticketDatabase=new TicketDatabase();
    public void setLV(String userLV){
        this.LV=userLV;
    }

    public String getLV(){
        return this.LV;
    }

    public void setAccumulatedConsumptionAmount(double accumulatedConsumptionAmount){
        this.accumulatedConsumptionAmount+=accumulatedConsumptionAmount;  //总金额为单次交易的累加
    }

    public double getAccumulatedConsumptionAmount(){
        return this.accumulatedConsumptionAmount;
    }

    public void setCumulativeConsumption(int cumulativeConsumption){
        this.cumulativeConsumption+=cumulativeConsumption;  //总次数为单次交易的累加
    }

    public int getCumulativeConsumption(){
        return this.cumulativeConsumption;
    }

    public void register() {
        String tempName;
        String tempPassword;
        String checkTmpPassword;
        String tempEmail;
        String tempNum;
        String tempID;
        Scanner reader = new Scanner(System.in);
        System.out.println("----------注册----------");
        // 设置用户名
        while (true) {
            System.out.println("申请用户名:");
            tempName = reader.next();
            if (userDatabase.findUserByName(tempName) == null) {
                break; // 用户名未被申请时跳出循环
            } else {
                System.out.println("该用户名已经被申请，请重新输入:");
            }
        }
        // 设置用户ID，随机
        tempID = UUID.randomUUID().toString().substring(0, 8);
        // 设置邮箱
        while (true) {
            System.out.println("邮箱(可用于找回密码):");
            tempEmail = reader.next();
            if (isE_mailValid(tempEmail)) {
                break; // 邮箱格式正确时跳出循环
            } else {
                System.out.println("您所输入的邮箱格式不正确，请检查后重新输入");
            }
        }
        // 设置用户手机号
        while (true) {
            System.out.println("绑定手机号:");
            tempNum = reader.next();
            if (userDatabase.findUserByPhone(tempNum) == null) {
                break; // 手机号未被绑定时跳出循环
            } else {
                System.out.println("该手机号已被绑定，请重新输入:");
            }
        }
        // 设置注册时间
        LocalDateTime now = LocalDateTime.now();
        // 设置密码
        while (true) {
            System.out.println("请输入您的密码(密码长度大于8,必须包括大小写字母、数字以及标点):");
            tempPassword = reader.next();
            if (isPasswordVaild(tempPassword)) {
                System.out.println("请再次输入您的密码:");
                checkTmpPassword = reader.next();

                if (tempPassword.equals(checkTmpPassword)) {
                    setID(tempID);
                    setName(tempName);
                    setEmail(tempEmail);
                    setPhoneNum(tempNum);
                    setPassword(tempPassword);
                    setRole("CUSTOMER");
                    setRegisterTime(now.toString());
                    setLV("铜牌用户");
                    userDatabase.addUser(this);//存储用户信息,this代表对象本身
                    System.out.println("注册成功！您的用户ID为:" + getID());
                    break; // 注册成功后跳出循环
                } else {
                    System.out.println("两次密码不一致，请检查后重新输入！");
                }
            } else {
                System.out.println("密码不符合要求（密码必须长度大于8，包括大小写字母、数字以及标点），请检查后重新输入！");
            }
        }
    }

    public void upMembership(){
        double price;
        System.out.println("----------更改会员----------");
        System.out.println("1.金牌用户（购票享88折）￥88.0\n2.银牌用户（购票享95折）￥66.0\n3.铜牌用户（购票无折扣）免费\n");
        System.out.println("亲爱的顾客，您当前的会员等级是"+getLV()+"，请问您是否确认要更改会员Y/N(注意区分大小写！)：");
        Scanner reader = new Scanner(System.in);
        while (true) {
            String choose = reader.next();
            if (choose.equals("Y")) {
                while(true){
                    System.out.println("请输入您想要更改的会员序号：");
                    int select= reader.nextInt();
                    if(select!=1 && select!=2 && select!=3){
                        System.out.println("找不到对象，请重新输入！");
                    }else{
                        switch (select){
                            case 1:
                                price=88.0;
                                if(getLV().equals("金牌用户")){
                                    System.out.println("您已经是"+getLV()+",不能重复更改！");
                                }else{
                                    if(payMethod(this,price)){
                                        setLV("金牌用户");
                                        System.out.println("会员更改成功！现在您已是金牌用户！");
                                    }else{
                                        System.out.println("会员更改失败，请稍后重新尝试");
                                    }
                                }
                                break;
                            case 2:
                                price=66.0;
                                if(getLV().equals("银牌用户")){
                                    System.out.println("您已经是"+getLV()+",不能重复更改！");
                                }else{
                                    if(payMethod(this,price)){
                                        setLV("银牌用户");
                                        System.out.println("会员更改成功！现在您已是银牌用户！");
                                    }else{
                                        System.out.println("会员更改失败，请稍后重新尝试");
                                    }
                                }
                                break;
                            case 3:
                                if(getLV().equals("铜牌用户")){
                                    System.out.println("您已经是"+getLV()+",不能重复更改！");
                                }else{
                                    setLV("铜牌用户");
                                    System.out.println("会员更改成功！现在您已是铜牌用户！");
                                }
                        }
                        break;
                    }
                }
                break;
            } else if (choose.equals("N")) {
                System.out.println("您已确认放弃更改");
                break; // 放弃升级后跳出循环
            } else {
                System.out.println("请输入正确的选项(Y/N)");
            }
        }
    }

    public boolean changeSelfPassword(Users user) {
        String formerPassword;
        String tempPassword;
        String checkTmpPassword;
        boolean success = false;
        Scanner reader = new Scanner(System.in);
        System.out.println("----------修改密码----------");
        while (true) {
            System.out.println("请输入您原先的密码:");
            formerPassword = reader.next();
            if (user.getPassword().equals(formerPassword)) {//检查密码是否正确
                while (true) {
                    System.out.println("请输入新密码(密码长度大于8,必须包括大小写字母、数字以及标点):");
                    tempPassword = reader.next();
                    if (isPasswordVaild(tempPassword)) {
                        System.out.println("请再次输入新密码：");
                        checkTmpPassword=reader.next();
                        if (tempPassword.equals(checkTmpPassword)) {
                            user.setPassword(tempPassword);
                            userDatabase.updateUser(user);
                            success = true;
                            break; // 修改密码成功后跳出内层循环
                        } else {
                            System.out.println("两次密码不一致，请检查后重新输入！");
                        }
                    } else {
                        System.out.println("密码不符合要求（密码必须长度大于8,包括大小写字母、数字以及标点），请检查后重新输入！");
                    }
                }
                break; // 修改密码成功后跳出外层循环
            } else {
                System.out.println("用户名或密码错误，请检查后重新尝试");
            }
        }
        return success;
    }

    public boolean viewAllFilmInfo(){
        System.out.println("----------查看所有电影放映信息----------");
        movieDatabase.allMovie();
        showtimeDatabase.allShowtime();
        return true;
    }

    public boolean viewFilmInfo(){
        System.out.println("----------查看指定电影放映信息----------");
        List<Showtime> filteredShowtime = new ArrayList<>();
        while(true){
            System.out.println("请输入影片名称:");
            Scanner reader = new Scanner(System.in);
            String tempTitle = reader.next();
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

    public boolean buyTicket(Customer customer){
        String tempTime;
        String tempTheaterID;
        Showtime targetShowtime;
        Theater targetTheater;
        boolean seccess=false;
        String ticketID;
        String theaterName;
        String showTime;
        String duration;
        String title;
        Timer timer = new Timer();
        Scanner reader = new Scanner(System.in);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                timer.cancel();
            }
        };
        String ticketNum;
        TheaterDatabase theaterDatabase=new TheaterDatabase();
        System.out.println("----------购票----------");
        System.out.println("以下是可以选择的场次");
        showtimeDatabase.allShowtime();
        while(true){
            System.out.println("请输入场次所在放映厅编号:");
            tempTheaterID = reader.next();
            System.out.println("请输入场次放映时间:");
            Scanner readerT = new Scanner(System.in);
            tempTime = readerT.nextLine();
            targetShowtime=showtimeDatabase.findShowtimeByTheaterAndTime(tempTheaterID, tempTime);
            if (targetShowtime==null) {
                System.out.println("该场次不存在");
                continue;
            }
            else{
                boolean loop=true;
                while (loop){
                    System.out.println("请输入您的购票张数:");
                    ticketNum = reader.next();
                    try {
                        //Integer.parseInt(input)把接收的字符串转换成int接收的值
                        int num=Integer.parseInt(ticketNum);
                        //是整数就退出循环
                        //不是整数就报异常
                        loop=false;
                        targetTheater=theaterDatabase.findTheaterByID(targetShowtime.getTheaterID());
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
                        for(int i=0;i<Integer.parseInt(ticketNum);i++){
                            int line=-1;
                            int column=-1;
                            int[] seatID={-1,-1};
                            System.out.println("请选择座位排数:");
                            line = reader.nextInt();
                            System.out.println("请选择座位座数:");
                            column = reader.nextInt();
                            seatID[0]=line;
                            seatID[1]=column;
                            if(targetTheater.findSeatByID(seatID)!=null){
                                System.out.println("选择成功！请于两分钟内完成支付！");
                                if(pay(targetShowtime,customer)){
                                    System.out.println("购票成功！");
                                    // 查询影票相关信息
                                    ticketID = generateTicketID();
                                    theaterName=targetTheater.getTheaterName();
                                    showTime=targetShowtime.getShowTime();
                                    title=targetShowtime.getMovie().getTitle();
                                    duration=targetShowtime.getMovie().getDuration();
                                    //存储影票信息
                                    Ticket ticket=new Ticket(ticketID, theaterName, showTime, duration, title, seatID);
                                    ticketDatabase.addTicket(ticket);
                                    LocalDateTime localDateTime = LocalDateTime.now();
                                    String purchaseTime=localDateTime.toString();
                                    //设置购票记录
                                    TicketHistory ticketHistory=new TicketHistory(ticketID,purchaseTime,theaterName,showTime,title,duration,seatID);
                                    purchaseHistory.add(ticketHistory);
                                    targetTheater.setSeat(seatID); //更新放映厅座位信息
                                    System.out.println("此次购票信息为：");
                                    System.out.println("电影票ID编号"+ticketID);
                                    System.out.println("放映厅: " +theaterName );
                                    System.out.println("放映时间: " + showTime);
                                    System.out.println("片名: " +title );
                                    System.out.println("片长: " + duration );
                                    System.out.println("座位号: " + line + "排" + column + "座");
                                }
                                else{
                                    System.out.println("支付失败");
                                    timer.schedule(task, 2 * 60 * 1000);
                                }
                            }
                            else
                                System.out.println("选座失败！");
                        }
                    } catch (NumberFormatException e) {
                        //提示 继续循环
                        System.out.println("您输入的不是整数，请重新输入");
                    }
                }
            }
            break;
        }
        return seccess;
    }

    public boolean pay(Showtime showtime,Customer customer){
        int select=-1;//选择支付方式
        String choose;//确认是否支付
        System.out.println("----------付款（此功能仅为模拟）----------");
        double discountedPrice = payDiscount(customer, showtime.getPrice());
        return payMethod(customer, discountedPrice);
    }

    public double payDiscount(Customer customer,double price){
        double discount=1.0;
        if (customer.getLV().equals("金牌用户")) {
            discount = 0.88;
        } else if (customer.getLV().equals("银牌用户")) {
            discount = 0.95;
        }
        return price * discount;
    }

    public boolean payMethod(Customer customer,double price){
        String select;
        String choose;
        boolean loop=true;
        boolean seccess=false;
        System.out.print("1.微信\n2.支付宝\n3.银行卡\n");
        while(loop){
            try{
                System.out.println("请选择支付方式：");
                Scanner reader = new Scanner(System.in);
                select = reader.next();
                int se=Integer.parseInt(select);
                loop=false;
                while(true){
                    switch (se) {
                        case 1:
                            System.out.println("----------微信支付----------");
                            System.out.println("请确认支付金额:"+price);
                            while (true) {
                                System.out.println("确定是否支付Y/F(注意区分大小写！)：");
                                choose = reader.next();
                                if (choose.equals("Y")) {
                                    System.out.println("支付成功！");
                                    accumulatedConsumptionAmount+=price;
                                    customer.cumulativeConsumption++;
                                    seccess=true;
                                    break;
                                }
                                else if (choose.equals("F")) {
                                    System.out.println("您已放弃支付");
                                    break; // 放弃支付后跳出循环
                                }
                                else {
                                    System.out.println("请输入正确的选项(Y/F)");
                                }
                            }
                            break;
                        case 2:
                            System.out.println("----------支付宝支付----------");
                            System.out.println("请确认支付金额:"+price);
                            while (true) {
                                System.out.println("确定是否支付Y/F(注意区分大小写！)：");
                                choose = reader.next();
                                if (choose.equals("Y")) {
                                    System.out.println("支付成功！");
                                    customer.accumulatedConsumptionAmount+=price;
                                    customer.cumulativeConsumption++;
                                    seccess=true;
                                    break;
                                }
                                else if (choose.equals("F")) {
                                    System.out.println("您已放弃支付");
                                    break; // 放弃支付后跳出循环
                                }
                                else {
                                    System.out.println("请输入正确的选项(Y/F)");
                                }
                            }
                            break;
                        case 3:
                            System.out.println("----------银行卡支付----------");
                            System.out.println("请确认支付金额:"+price);
                            while (true) {
                                System.out.println("确定是否支付Y/F(注意区分大小写！)：");
                                choose = reader.next();
                                if (choose.equals("Y")) {
                                    System.out.println("支付成功！");
                                    customer.accumulatedConsumptionAmount+=price;
                                    customer.cumulativeConsumption++;
                                    seccess=true;
                                    break;
                                }
                                else if (choose.equals("F")) {
                                    System.out.println("您已放弃支付");
                                    break; // 放弃支付后跳出循环
                                }
                                else {
                                    System.out.println("请输入正确的选项(Y/F)");
                                }
                            }
                            break;
                        default:
                            System.out.println("找不到对象，请重新输入！");
                    }
                    break;
                }
            }catch(NumberFormatException e) {
                //提示 继续循环
                System.out.println("您输入的不是整数，请重新输入");
            }
        }

        return  seccess;
    }

    public boolean getTicket(){
        
        System.out.println("----------取票----------");
        System.out.println("请输入电影票的电子ID编号:");
        Scanner scanner = new Scanner(System.in);
        String ticketID = scanner.nextLine();
        Ticket targetTicket=ticketDatabase.findTicketByID(ticketID);
        LocalDateTime getTicketTime = LocalDateTime.now();
        if(!targetTicket.getStatus()){
            targetTicket.setStatus(true);
            ticketDatabase.updateTicket(targetTicket);
            System.out.println("取票成功！");
            System.out.println("-------------**影院-------------");
            System.out.println("影票id: " +targetTicket.getTicketID() );
            System.out.println("取票时间: " + getTicketTime.toString());//？有些许问题
            System.out.println("放映厅: " +targetTicket.getTheaterName() );
            System.out.println("放映时间: " +targetTicket.getShowTime() );
            System.out.println("片名: " +targetTicket.getTitle() );
            System.out.println("片长: " + targetTicket.getDuration());
            System.out.println("座位号: " + targetTicket.getSeatID()[0] + "排" + targetTicket.getSeatID()[1] + "座");
            System.out.println("------------*********------------");
        }
        else{
            System.out.println("该电影票已被取出！");
        }
        return true;
    }

    public boolean checkPurchaseHistory(){
        System.out.println("----------查看购票历史----------");
        for (TicketHistory history : purchaseHistory) {
            System.out.println("购票时间: " + history.getPurchaseTime());
            System.out.println("放映厅: " + history.getTheaterName());
            System.out.println("放映时间: " + history.getShowTime());
            System.out.println("片名: " + history.getMovieTitle());
            System.out.println("片长: " + history.getDuration());
            System.out.println("座位号: " + history.getSeatID()[0] + "排" + history.getSeatID()[1] + "座");
            System.out.println("-----------------------------");
        }
        return true;
    }

    public String generateTicketID() {//生成随机影票ID
        String characters1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String characters2 = "0123456789";
        int ticketLength = 10; // 电影票编码的长度
        StringBuilder ticketID = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            int index = random.nextInt(characters1.length());
            char randomChar = characters1.charAt(index);
            ticketID.append(randomChar);
        }
        for (int i = 2; i < ticketLength; i++) {
            int index = random.nextInt(characters2.length());
            char randomChar = characters2.charAt(index);
            ticketID.append(randomChar);
        }
        return ticketID.toString();
    }

    public static class TicketHistory {//购票历史
        private String purchaseTime;
        private String ticketID;
        private String theaterName;
        private String duration;
        private String showTime;
        private String movieTitle;
        private int[] seatID;

        public TicketHistory(String ticketID,String purchaseTime, String theaterName, String showTime, String movieTitle, String duration,int[] seatID) {
            this.ticketID = ticketID;
            this.purchaseTime = purchaseTime;
            this.theaterName = theaterName;
            this.showTime = showTime;
            this.movieTitle = movieTitle;
            this.duration = duration;
            this.seatID = seatID;
        }

        // Getter 方法
        public String getPurchaseTime() {
            return purchaseTime;
        }
        public String getTicketID() {
            return ticketID;
        }

        public String getTheaterName() {
            return theaterName;
        }

        public String getDuration() {
            return duration;
        }

        public String getShowTime() {
            return showTime;
        }

        public String getMovieTitle() {
            return movieTitle;
        }

        public int[] getSeatID() {
            return seatID;
        }

        // Setter 方法
        public void setPurchaseTime(String purchaseTime) {
            this.purchaseTime = purchaseTime;
        }
        public void ticketID(String ticketID) {this.ticketID = ticketID;}

        public void setTheaterName(String theaterName) {
            this.theaterName = theaterName;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public void setShowTime(String showTime) {
            this.showTime = showTime;
        }

        public void setMovieTitle(String movieTitle) {
            this.movieTitle = movieTitle;
        }

        public void setSeatID(int[] seatID) {
            this.seatID = seatID;
        }
    }

}
