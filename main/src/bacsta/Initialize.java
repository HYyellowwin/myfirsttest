package bacsta;

import allDatabase.MovieDatabase;
import allDatabase.ShowtimeDatabase;
import allDatabase.TheaterDatabase;
import allDatabase.UserDatabase;

import java.time.LocalDateTime;

public class Initialize {//初始化顾客、管理员、经理、前台以及电影信息
    UserDatabase userDatabase=new UserDatabase();
    Foreground foreground=new Foreground();
    Customer customer1=new Customer();
    Customer customer2=new Customer();
    void initializeAdmin(){//管理员登录账户
        Administrator administrator=new Administrator("001777", "admin", "ynuiffo#777", "ADMINISTRATOR", "123782163@qq.com", LocalDateTime.now().toString(), "1928738192");
        userDatabase.addUser(administrator);
    }

    void initializeManager(){//经理登录账户
        Manager manager=new Manager("111777","张经理","#YNUmanager111","MANAGER","123456789@qq.com",LocalDateTime.now().toString(),"13753199082");
        userDatabase.addUser(manager);
    }

    void initializeForeground(){//前台登录账户
        foreground.setID("100777");
        foreground.setName("前台小李");
        foreground.setPassword("#YNUforeground777");
        foreground.setRole("FOREGROUND");
        foreground.setEmail("234567891@qq.com");
        foreground.setRegisterTime(LocalDateTime.now().toString());
        foreground.setPhoneNum("13753109065");
        userDatabase.addUser(foreground);
    }

    void initializeCustomer(){//顾客登录账户
        customer1.setID("100401");
        customer1.setName("吴金拍");
        customer1.setPassword("@Ty109529");
        customer1.setRole("CUSTOMER");
        customer1.setEmail("949743751@qq.com");
        customer1.setRegisterTime(LocalDateTime.now().toString());
        customer1.setPhoneNum("15096606564");
        customer1.setLV("金牌用户");
        userDatabase.addUser(customer1);
        customer2.setID("100402");
        customer2.setName("王银白");
        customer2.setPassword("@Ww109529");
        customer2.setRole("CUSTOMER");
        customer2.setEmail("942834751@qq.com");
        customer2.setRegisterTime(LocalDateTime.now().toString());
        customer2.setPhoneNum("1523848964");
        customer2.setLV("银牌用户");
        userDatabase.addUser(customer2);
    }

    void initializeTheater(){//影厅信息
        TheaterDatabase.theaters.add(new Theater("1", "激光巨幕全景声厅"));
        TheaterDatabase.theaters.add(new Theater("2", "N号激光厅"));
        TheaterDatabase.theaters.add(new Theater("3", "IMAX巨幕厅"));
        TheaterDatabase.theaters.add(new Theater("4", "普通影厅1"));
        TheaterDatabase.theaters.add(new Theater("5", "普通影厅2"));
    }

    void initializeSeat(){//申请座位号
        for(Theater theater:TheaterDatabase.theaters){
            for(int r=0;r<7;r++){
                for(int c=0;c<12;c++){
                    int[] seatID={r+1,c+1};
                    theater.seats.add(new Seat(seatID));
                }
            }
        }
    }
    
    void initializeMovie(){
        MovieDatabase.movies.add(new Movie("爱玛", "奥特姆·代·怀尔德", "安雅·泰勒·乔伊、约翰尼·弗林", "该片改编自简·奥斯汀同名小说，讲述了少女爱玛·伍德豪斯与年长许多的乔治·奈特利先生曲折的爱情故事", "124分钟"));
        MovieDatabase.movies.add(new Movie("绿里奇迹", "弗兰克·德拉邦特", "汤姆·汉克斯、大卫·摩斯", "影片改编自小说《绿里奇迹》，讲述了高个子黑人约翰·科菲因被误判来到死牢后发生的超自然的事件", "188分钟"));
        MovieDatabase.movies.add(new Movie("猫鼠游戏", "斯蒂文·斯皮尔伯格", "莱昂纳多·迪卡普里奥、汤姆·汉克斯", "讲述了FBI探员卡尔与擅长伪造文件的罪犯弗兰克之间进行一场场猫抓老鼠的较量的故事", "141分钟"));
        MovieDatabase.movies.add(new Movie("面纱", "约翰·卡兰", "娜奥米·沃茨、爱德华·诺顿", "讲述了一对年轻的英国夫妇来到中国乡村生活，他们经历了其在英国家乡舒适生活中无法想象和体验的情感波澜，并领悟到了爱与奉献的真谛", "124 分钟"));
    }

    void initializeShowtime(){
        ShowtimeDatabase.showtimes.add(new Showtime("3", "2023-10-08 17:26:22", 49, new Movie("爱玛", "奥特姆·代·怀尔德", "安雅·泰勒·乔伊、约翰尼·弗林", "该片改编自简·奥斯汀同名小说，讲述了少女爱玛·伍德豪斯与年长许多的乔治·奈特利先生曲折的爱情故事", "124 分钟 ")));
        ShowtimeDatabase.showtimes.add(new Showtime("2", "2023-10-18 13:26:22", 49, new Movie("绿里奇迹", "弗兰克·德拉邦特", "汤姆·汉克斯、大卫·摩斯", "影片改编自小说《绿里奇迹》，讲述了高个子黑人约翰·科菲因被误判来到死牢后发生的超自然的事件", "188 分钟 ")));
        ShowtimeDatabase.showtimes.add(new Showtime("1", "2023-10-28 19:26:22", 49, new Movie("面纱", "约翰·卡兰", "娜奥米·沃茨、爱德华·诺顿", "讲述了一对年轻的英国夫妇来到中国乡村生活，他们经历了其在英国家乡舒适生活中无法想象和体验的情感波澜，并领悟到了爱与奉献的真谛", "124 分钟")));
    }
            
}
