package bacsta;

public class Main {
    public static void main(String[] args) {
        //给定所有初始信息
        Initialize initialize=new Initialize();
        initialize.initializeAdmin();
        initialize.initializeForeground();
        initialize.initializeManager();
        initialize.initializeCustomer();
        initialize.initializeTheater();
        initialize.initializeSeat();
        initialize.initializeMovie();
        initialize.initializeShowtime();
        //程序开始运行
        Boundary begin=new Boundary();
        begin.startBoundary();
    }
}
