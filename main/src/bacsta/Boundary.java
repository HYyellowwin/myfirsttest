package bacsta;

import allDatabase.UserDatabase;

import java.util.Scanner;

public class Boundary {
    void startBoundary() {
        Customer customer=new Customer();
        Users users=new Users();
        System.out.println("欢迎进入**影院在线售票系统");
        System.out.println("------------------------------");
        System.out.print("1.登录\n2.注册\n3.忘记密码\n");
        System.out.print("请选择你要操作的功能:");
        Scanner reader = new Scanner(System.in);
        int select = reader.nextInt();
        switch (select) {
            case 1:
                System.out.println("您已进入登录界面");
                boolean result=users.login();
                if(result==false)
                    returnMain();
                break;
            case 2:
                System.out.println("您已进入注册界面");
                customer.register();
                returnMain();
                break;
            case 3:
                users.resetPassword();
                returnMain();
                break;
            default:
                System.out.println("输入错误，请重新输入\n");
                startBoundary();
        }
    }



    void returnMain() {
        System.out.println("**************************");
        System.out.println("返回系统登录界面");
        startBoundary();
    }

    void function(Users user){
        if(user.getRole().equals("ADMINISTRATOR"))//管理员用户名
            administratorFunctionBoundary(user);
        else if(user.getRole().equals("MANAGER"))//经理用户名
            managerFunctionBoundary(user);
        else if(user.getRole().equals("FOREGROUND"))//前台用户名
            foregroundFunctionBoundary(user);
        else
            customerFunctionBoundary((Customer)user);
    }

    void administratorFunctionBoundary(Users user){
        Administrator administrator=(Administrator)user;
        System.out.println("欢迎您管理员！");
        System.out.println("------------------------------");
        System.out.print("1.密码管理\n2.用户管理\n3.退出登录\n");
        System.out.print("请选择你要操作的功能:");
        Scanner reader = new Scanner(System.in);
        int select = reader.nextInt();

        switch (select) {
            case 1:
                System.out.println("----------密码管理----------");
                System.out.print("1.修改自身密码\n2.重置用户密码\n");
                System.out.print("请选择你要操作的功能:");
                select = reader.nextInt();

                switch (select) {
                    case 1:
                        administrator.changeSelfPassword(user);
                        break;
                    case 2:
                        administrator.changeOthersPassword();
                        break;
                    default:
                        System.out.println("输入错误，请重新输入");
                        administratorFunctionBoundary(user);
                }
                break;
            case 2:
                System.out.println("----------用户管理----------");
                System.out.print("1.列出所有用户信息\n2.删除用户信息\n3.查询用户信息\n4.增加用户信息\n5.修改用户信息\n");
                System.out.print("请选择你要操作的功能:");
                select = reader.nextInt();

                switch (select) {
                    case 1:
                        administrator.listAllCineplexUserInfo();
                        break;
                    case 2:
                        administrator.deleteInfo();
                        break;
                    case 3:
                        administrator.inquireInfo();
                        break;
                    case 4:
                        administrator.addCineplexUser();
                        break;
                    case 5:
                        administrator.editCineplexUser();
                        break;
                    default:
                        System.out.println("输入错误，请重新输入");
                        administratorFunctionBoundary(user);
                }
                break;
            case 3:
                System.out.println("----------退出登录----------");
                administrator.logout();
                break;
            default:
                System.out.println("输入错误，请重新输入");
                administratorFunctionBoundary(user);
        }
        returnAdministratorFunctionBoundary(user);
    }

    void returnAdministratorFunctionBoundary(Users user){
        System.out.println("**************************");
        System.out.println("返回管理员菜单");
        administratorFunctionBoundary(user);
    }


    void managerFunctionBoundary(Users user){
        Manager manager=(Manager)user;
        System.out.println("欢迎您经理！");
        System.out.println("------------------------------");
        System.out.print("1.密码管理\n2.影片管理\n3.排片管理\n4.顾客信息管理\n5.更改经理用户信息\n6.退出登录\n");
        System.out.print("请选择你要操作的功能:");
        Scanner reader = new Scanner(System.in);
        int select = reader.nextInt();

        switch (select) {
            case 1:
                System.out.println("----------密码管理----------");
                System.out.print("1.修改自身密码\n2.重置用户密码\n");
                System.out.print("请选择你要操作的功能:");
                select = reader.nextInt();

                switch(select){
                    case 1:
                        manager.changeSelfPassword(user);
                        break;
                    case 2:
                        manager.changeOthersPassword();
                        break;
                    default:
                        System.out.println("输入错误，请重新输入");
                        managerFunctionBoundary(user);
                }
                break;
            case 2:
                System.out.println("----------影片管理----------");
                System.out.print("1.列出正在上映影片的信息\n2.添加影片信息\n3.修改影片信息\n4.删除影片信息\n5.查询影片信息\n");
                System.out.print("请选择你要操作的功能:");
                select = reader.nextInt();

                switch(select){
                    case 1:
                        manager.listAllFilmInfo();
                        break;
                    case 2:
                        manager.addFilmInfo();
                        break;
                    case 3:
                        manager.editFilmInfo();
                        break;
                    case 4:
                        manager.deleteFilmInfo();
                        break;
                    case 5:
                        manager.inquireFilmInfo();
                        break;
                    default:
                        System.out.println("输入错误，请重新输入");
                        administratorFunctionBoundary(user);
                }
                break;
            case 3:
                System.out.println("----------排片管理----------");
                System.out.print("1.增加场次\n2.修改场次\n3.删除场次\n4.列出所有场次信息\n");
                System.out.print("请选择你要操作的功能:");
                select = reader.nextInt();

                switch(select){
                    case 1:
                        manager.addSession();
                        break;
                    case 2:
                        manager.editSession();
                        break;
                    case 3:
                        manager.deleteSession();
                        break;
                    case 4:
                        manager.listAllSession();
                        break;
                    default:
                        System.out.println("输入错误，请重新输入");
                        administratorFunctionBoundary(user);
                }
                break;
            case 4:
                System.out.println("----------顾客信息管理----------");
                System.out.print("1.列出所有顾客的信息\n2.查询顾客信息\n");
                System.out.print("请选择你要操作的功能:");
                select = reader.nextInt();

                switch(select){
                    case 1:
                        manager.listAllCustomerInfo();
                        break;
                    case 2:
                        manager.inquireCustomerInfo();
                        break;
                    default:
                        System.out.println("输入错误，请重新输入");
                        administratorFunctionBoundary(user);
                }
                break;
            case 5:
                manager.addManager();
                break;
            case 6:
                System.out.println("----------退出登录----------");
                manager.logout();
                break;
            default:
                System.out.println("输入错误，请重新输入");
                managerFunctionBoundary(user);
        }

        returnManagerFunctionBoundary(user);
    }

    void returnManagerFunctionBoundary(Users user){
        System.out.println("**************************");
        System.out.println("返回经理界面");
        managerFunctionBoundary(user);
    }


    void foregroundFunctionBoundary(Users user){
        Foreground foreground=new Foreground();
        System.out.println("欢迎您前台！");
        System.out.println("------------------------------");
        System.out.print("1.列出所有正在上映的影片的信息\n2.列出所有场次的信息\n3.列出指定电影和场次的信息\n4.售票功能\n5.退出登录\n");
        System.out.print("请选择你要操作的功能:");
        Scanner reader = new Scanner(System.in);
        int select = reader.nextInt();

        switch(select){
            case 1:
                foreground.listAllFilmInfo();
                break;
            case 2:
                foreground.listAllSessionsInfo();
                break;
            case 3:
                foreground.listSessionInfo();
                break;
            case 4:
                foreground.sellTickets();
                break;
            case 5:
                foreground.logout();;
                break;
            default:
                System.out.println("输入错误，请重新输入");
                foregroundFunctionBoundary(user);
        }
        returnForegroundFunctionBoundary(user);
    }

    void returnForegroundFunctionBoundary(Users user){
        System.out.println("**************************");
        System.out.println("返回前台界面");
        foregroundFunctionBoundary(user);
    }

    void customerFunctionBoundary(Customer customer){
        System.out.println("欢迎您顾客！");//再加一个用户ID？
        System.out.println("------------------------------");
        System.out.print("1.密码管理\n2.购票功能\n3.更改会员\n4.退出登录\n");
        System.out.print("请选择你要操作的功能:");
        Scanner reader = new Scanner(System.in);
        int select = reader.nextInt();
        switch(select){
            case 1:
                System.out.println("----------密码管理----------");
                System.out.print("1.修改密码\n");
                System.out.print("请选择你要操作的功能:");
                select = reader.nextInt();

                switch(select){
                    case 1:
                        customer.changeSelfPassword(customer);
                        break;
                    default:
                        System.out.println("输入错误，请重新输入");
                        customerFunctionBoundary(customer);
                }
                break;
            case 2:
                System.out.println("----------购票功能----------");
                System.out.print("1.查看所有电影放映信息\n2.查看指定电影放映信息\n3.购票\n4.取票\n5.查看购票记录\n");
                System.out.print("请选择你要操作的功能:");
                select = reader.nextInt();

                switch(select){
                    case 1:
                        customer.viewAllFilmInfo();
                        break;
                    case 2:
                        customer.viewFilmInfo();
                        break;
                    case 3:
                        customer.buyTicket(customer);
                        break;
                    case 4:
                        customer.getTicket();
                        break;
                    case 5:
                        customer.checkPurchaseHistory();
                        break;
                    default:
                        System.out.println("输入错误，请重新输入");
                        customerFunctionBoundary(customer);
                }
                break;
            case 3:
                customer.upMembership();
                break;
            case 4:
                customer.logout();
                break;
            default:
                System.out.println("输入错误，请重新输入");
                customerFunctionBoundary(customer);
        }
        returnCustomerFunctionBoundary(customer);
    }

    void returnCustomerFunctionBoundary(Customer customer){
        System.out.println("**************************");
        System.out.println("返回顾客界面");
        customerFunctionBoundary(customer);  //再返回？为什么
    }
}
