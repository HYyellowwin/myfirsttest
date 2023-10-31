package bacsta;

import allDatabase.UserDatabase;

import java.util.Scanner;
import java.util.UUID;
import java.time.LocalDateTime;

public class Administrator extends Users {
    Foreground foreground=new Foreground();

    public Administrator(String ID, String name, String password, String role, String email, String registerTime, String phoneNum){
        this.setID(ID);
        this.setName(name);
        this.setPassword(password);
        this.setRole(role);
        this.setEmail(email);
        this.setRegisterTime(registerTime);
        this.setPhoneNum(phoneNum);

    }

    public boolean changeSelfPassword(Users user) {
        String formerPassword;
        String tempPassword;
        String checkTmpPassword;
        boolean success = false;
        Scanner reader = new Scanner(System.in);
        System.out.println("----------修改自身密码----------");
        while (true) {
            System.out.println("请输入您原先的密码:");
            formerPassword = reader.next();
            if (user.getPassword().equals(formerPassword)) {
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
                        }
                    } else {
                        System.out.println("密码不符合要求（密码必须长度大于8,包括大小写字母、数字以及标点），请检查后重新输入！");
                    }
                }
                break; // 修改密码成功后跳出外层循环
            } else {
                System.out.println("用户名或密码错误，请检查后再次尝试");
            }
        }
        return success;
    }

    public boolean changeOthersPassword() {
        Users targetUser = new Users();
        String tempName;
        System.out.println("----------修改指定用户的密码----------");
        while (true) {
            System.out.println("请输入待修改密码用户的用户名:");
            Scanner reader = new Scanner(System.in);
            tempName = reader.next();
            targetUser = userDatabase.findUserByName(tempName);
            if (targetUser != null && (targetUser.getRole().equals("MANAGER") || targetUser.getRole().equals("FOREGROUND")
                    ||targetUser.getRole().equals("ADMINISTRATOR")||targetUser.getRole().equals("CUSTOMER"))) {
                break; // 用户存在且为影院用户时跳出循环
            } else {
                System.out.println("用户名错误或该用户不为影院用户");
            }
        }
        targetUser.setPassword("YNU123456");//重置密码
        userDatabase.updateUser(targetUser);
        return true;
    }

    public boolean listAllCineplexUserInfo(){
        System.out.println("----------列出所有影院方用户信息----------");
        userDatabase.allCineplexUser();
        return true;
    }

    public boolean deleteInfo() {
        String tempName;
        String choose;
        Users targetUser = new Users();
        Scanner reader = new Scanner(System.in);
        System.out.println("----------删除影院方用户信息----------");
        while (true) {
            System.out.println("请输入将要删除信息用户的用户名:");
            tempName = reader.next();
            targetUser = userDatabase.findUserByName(tempName);
            if (targetUser != null && (targetUser.getRole().equals("MANAGER") || targetUser.getRole().equals("FOREGROUND")
                    ||targetUser.getRole().equals("ADMINISTRATOR")||targetUser.getRole().equals("CUSTOMER"))) {
                break; // 用户存在且为影城方用户时跳出循环
            } else {
                System.out.println("该用户不存在或不为影城方用户");
            }
        }
        while (true) {
            System.out.println("确定要删除该用户所有信息吗?删除后将无法恢复！");
            System.out.println("Y/N(注意区分大小写！)");
            choose = reader.next();
            if (choose.equals("Y")) {
                userDatabase.deleteUser(targetUser);
                break; // 删除用户后跳出循环
            } else if (choose.equals("N")) {
                boundary.administratorFunctionBoundary(this);
                break; // 放弃删除后跳出循环
            } else {
                System.out.println("请输入正确的选项(Y/N)");
            }
        }
        return true;
    }

    public boolean inquireInfo() {
        String choose1, choose2 ;
        int select1,select2;
        String tempNameOrID;
        Users targetUser ;
        System.out.println("----------查询用户信息----------");
        System.out.print("1.查询用户注册时间\n2.查询用户类型\n3.查询用户绑定手机号\n4.查询用户绑定邮箱\n");
        Scanner reader = new Scanner(System.in);
        while (true) {
            System.out.print("请选择你要操作的功能:");
            choose1 = reader.next();
            if (!choose1.equals("1")&& !choose1.equals("2")&& !choose1.equals("3")&&!choose1.equals("4")) {
                System.out.println("找不到对象，请重新输入！");
            } else {
                select1=Integer.parseInt(choose1);
                break; // 选择合法选项时跳出循环
            }
        }
        switch (select1) {
            case 1:
                while (true) {
                    System.out.println("----------查询用户注册时间----------");
                    System.out.println("1.查询单个用户\n2.查询所有用户\n");
                    System.out.println("请选择你要操作的功能:");
                    choose2 = reader.next();
                    if (!choose2.equals("1")&& !choose2.equals("2")) {
                        System.out.println("找不到对象，请重新输入！");
                    } else {
                        select2=Integer.parseInt(choose2);
                        break; // 选择合法选项时跳出循环
                    }
                }
                switch (select2) {
                    case 1:
                        while (true) {
                            System.out.println("----------查询单个用户注册时间----------");
                            System.out.println("请输入用户名或用户ID:");
                            tempNameOrID = reader.next();
                            targetUser = userDatabase.findUserByName(tempNameOrID);
                            if (targetUser == null) {
                                targetUser = userDatabase.findUserByID(tempNameOrID);
                            }
                            if (targetUser != null && (targetUser.getRole().equals("MANAGER") || targetUser.getRole().equals("FOREGROUND")
                                    ||targetUser.getRole().equals("ADMINISTRATOR")||targetUser.getRole().equals("CUSTOMER"))) {
                                System.out.println("该用户注册时间为:" + targetUser.getRegisterTime());
                                break;
                            } else {
                                System.out.println("找不到对象，请重新输入！");
                            }
                        }
                        break;
                    case 2:
                        for (int i = 0; i < UserDatabase.users.size(); i++) {
                            if (UserDatabase.users.get(i).getRole().equals("MANAGER") || UserDatabase.users.get(i).getRole().equals("FOREGROUND")) {
                                System.out.print(UserDatabase.users.get(i).getName() + ":");
                                System.out.println(UserDatabase.users.get(i).getRegisterTime());
                            }
                        }
                        break;
                }
                break;
            case 2:
                while (true) {
                    System.out.println("----------查询用户类型----------");
                    System.out.print("1.查询单个用户\n2.查询所有用户\n");
                    System.out.print("请选择你要操作的功能:");
                    choose2 = reader.next();
                    if (!choose2.equals("1")&& !choose2.equals("2")) {
                        System.out.println("找不到对象，请重新输入！");
                    } else {
                        select2=Integer.parseInt(choose2);
                        break; // 选择合法选项时跳出循环
                    }
                }
                switch (select2) {
                    case 1:
                        while (true) {
                            System.out.println("请输入用户名或用户ID:");
                            tempNameOrID = reader.next();
                            targetUser = userDatabase.findUserByName(tempNameOrID);
                            if (targetUser == null) {
                                targetUser = userDatabase.findUserByID(tempNameOrID);
                            }
                            if (targetUser != null && (targetUser.getRole().equals("MANAGER") || targetUser.getRole().equals("FOREGROUND")
                                    ||targetUser.getRole().equals("ADMINISTRATOR")||targetUser.getRole().equals("CUSTOMER"))) {
                                System.out.println("该用户的用户类型为:" + targetUser.getRole());
                                break;
                            } else {
                                System.out.println("用户名/用户ID错误或该用户不为影院方用户");
                            }
                        }
                        break;
                    case 2:
                        for (int i = 0; i < UserDatabase.users.size(); i++) {
                            if (UserDatabase.users.get(i).getRole().equals("MANAGER") || UserDatabase.users.get(i).getRole().equals("FOREGROUND")
                               ||UserDatabase.users.get(i).getRole().equals("ADMINISTRATOR") || UserDatabase.users.get(i).getRole().equals("CUSTOMER")) {
                                System.out.print(UserDatabase.users.get(i).getName() + ":");
                                System.out.println(UserDatabase.users.get(i).getRole());
                            }
                        }
                        break;
                }
                break;
            case 3:
                while (true) {
                    System.out.println("----------查询用户绑定手机号----------");
                    System.out.print("1.查询单个用户\n2.查询所有用户\n");
                    System.out.print("请选择你要操作的功能:");
                    choose2 = reader.next();
                    if (!choose2.equals("1")&& !choose2.equals("2")) {
                        System.out.println("找不到对象，请重新输入！");
                    } else {
                        select2=Integer.parseInt(choose2);
                        break; // 选择合法选项时跳出循环
                    }
                }
                switch (select2) {
                    case 1:
                        while (true) {
                            System.out.println("请输入用户名或用户ID:");
                            tempNameOrID = reader.next();
                            targetUser = userDatabase.findUserByName(tempNameOrID);
                            if (targetUser == null) {
                                targetUser = userDatabase.findUserByID(tempNameOrID);
                            }
                            if (targetUser != null && (targetUser.getRole().equals("MANAGER") || targetUser.getRole().equals("FOREGROUND")
                                    ||targetUser.getRole().equals("ADMINISTRATOR")||targetUser.getRole().equals("CUSTOMER"))) {
                                System.out.println("该用户绑定的手机号为:" + targetUser.getPhoneNum());
                                break;
                            } else {
                                System.out.println("用户名/用户ID错误或该用户不为影院用户");
                            }
                        }
                        break;
                    case 2:
                        for (int i = 0; i < UserDatabase.users.size(); i++) {
                            if (UserDatabase.users.get(i).getRole().equals("MANAGER") || UserDatabase.users.get(i).getRole().equals("FOREGROUND")
                                    ||UserDatabase.users.get(i).getRole().equals("ADMINISTRATOR") || UserDatabase.users.get(i).getRole().equals("CUSTOMER")) {
                                System.out.print(UserDatabase.users.get(i).getName() + ":");
                                System.out.println(UserDatabase.users.get(i).getPhoneNum());
                            }
                        }
                        break;
                }
                break;
            case 4:
                while (true) {
                    System.out.println("----------查询用户绑定邮箱----------");
                    System.out.print("1.查询单个用户\n2.查询所有用户\n");
                    System.out.print("请选择你要操作的功能:");
                    choose2 = reader.next();
                    if (!choose2.equals("1")&& !choose2.equals("2")) {
                        System.out.println("找不到对象，请重新输入！");
                    } else {
                        select2=Integer.parseInt(choose2);
                        break; // 选择合法选项时跳出循环
                    }
                }
                switch (select2) {
                    case 1:
                        while (true) {
                            System.out.println("请输入用户名或用户ID:");
                            tempNameOrID = reader.next();
                            targetUser = userDatabase.findUserByName(tempNameOrID);
                            if (targetUser == null) {
                                targetUser = userDatabase.findUserByID(tempNameOrID);
                            }
                            if (targetUser != null && (targetUser.getRole().equals("MANAGER") || targetUser.getRole().equals("FOREGROUND")
                                    ||targetUser.getRole().equals("ADMINISTRATOR")||targetUser.getRole().equals("CUSTOMER"))) {
                                System.out.println("该用户绑定的邮箱为:" + targetUser.getEmail());
                                break;
                            } else {
                                System.out.println("用户名/用户ID错误或该用户不为影院用户");
                            }
                        }
                        break;
                    case 2:
                        for (int i = 0; i < UserDatabase.users.size(); i++) {
                            if (UserDatabase.users.get(i).getRole().equals("MANAGER") || UserDatabase.users.get(i).getRole().equals("FOREGROUND")
                                    ||UserDatabase.users.get(i).getRole().equals("ADMINISTRATOR") || UserDatabase.users.get(i).getRole().equals("CUSTOMER")) {
                                System.out.print(UserDatabase.users.get(i).getName() + ":");
                                System.out.println(UserDatabase.users.get(i).getEmail());
                            }
                        }
                        break;
                }
                break;
        }
        return true;
    }

    public boolean addCineplexUser() {
        String select;
        System.out.println("----------增加用户信息----------");
        Scanner reader = new Scanner(System.in);
        System.out.print("1.前台\n");
        boolean loop=true;
        while (loop){
            System.out.print("请选择你要操作的功能:");
            select = reader.next();
            try{
                int choose=Integer.parseInt(select);
                loop=false;
                if(choose==1){
                    addForeground();
                } else{
                    System.out.print("找不到对象，请重新输入！");
                }
            }catch (NumberFormatException e) {
                //提示 继续循环
                System.out.println("您输入的不是整数，请重新输入");
            }
        }
        return true;
    }


    public boolean editCineplexUser() {
        int choose;
        String tempName;
        String tempNum;
        String tempEmail;
        String tempRole;
        Users targetUser = new Users();
        System.out.println("----------修改用户信息----------");
        System.out.print("1.修改用户类型\n2.修改手机号\n3.修改邮箱\n");
        System.out.print("请选择你要操作的功能:");
        Scanner reader = new Scanner(System.in);
        choose = reader.nextInt();
        while (true) {
            System.out.println("请输入要修改对象的用户名:");
            tempName = reader.next();
            targetUser = userDatabase.findUserByName(tempName);
            if (targetUser != null && (targetUser.getRole().equals("MANAGER") || targetUser.getRole().equals("FOREGROUND")
                    ||targetUser.getRole().equals("ADMINISTRATOR")||targetUser.getRole().equals("CUSTOMER"))) {
                if (choose != 1 && choose != 2 && choose != 3) {
                    System.out.println("找不到对象，请重新输入！");
                    continue; // 继续循环以重新选择选项
                } else {
                    switch (choose) {
                        case 1:
                            System.out.println("----------修改用户类型----------");
                            System.out.println("当前该用户的用户类型:" + targetUser.getRole());
                            System.out.println("请输入要更改为的用户类型(MANAGER/FOREGROUND):");
                            tempRole = reader.next();
                            if (tempRole.equals(targetUser.getRole())) {
                                System.out.println("修改类型与当前类型一致,请重新输入！");
                                continue; // 继续循环以重新输入
                            }
                            targetUser.setRole(tempRole);
                            userDatabase.updateUser(targetUser);
                            break;
                        case 2:
                            System.out.println("----------修改手机号----------");
                            System.out.println("当前该用户绑定的手机号:" + targetUser.getPhoneNum());
                            System.out.println("请输入要绑定的新手机号:");
                            tempNum = reader.next();
                            if (tempNum.equals(targetUser.getPhoneNum())) {
                                System.out.println("修改手机号与当前手机号一致,请重新输入！");
                                continue; // 继续循环以重新输入
                            } else if (userDatabase.findUserByPhone(tempNum) != null) {
                                System.out.println("该手机号已被绑定，请重新输入！");
                                continue; // 继续循环以重新输入
                            } else {
                                targetUser.setPhoneNum(tempNum);
                                userDatabase.updateUser(targetUser);
                            }
                            break;
                        case 3:
                            System.out.println("----------修改邮箱----------");
                            System.out.println("当前该用户绑定的邮箱号:" + targetUser.getEmail());
                            System.out.println("请输入要绑定的新邮箱号:");
                            tempEmail = reader.next();
                            if (tempEmail.equals(targetUser.getEmail())) {
                                System.out.println("修改邮箱与当前邮箱一致,请重新输入！");
                                continue; // 继续循环以重新输入
                            } else if (userDatabase.findUserByEmail(tempEmail) != null) {
                                System.out.println("该邮箱已被绑定，请重新输入！");
                                continue; // 继续循环以重新输入
                            } else {
                                targetUser.setEmail(tempEmail);
                                userDatabase.updateUser(targetUser);
                            }
                            break;
                    }
                }
                return true;
            } else {
                System.out.println("用户名错误或该用户不为影院用户");
                continue; // 继续循环以重新输入
            }
        }
    }



    public boolean addForeground() {
        String tempName;
        String tempPassword;
        String tempEmail;
        String tempID;
        String tempNum;
        System.out.println("----------新增前台用户----------");
        Scanner reader = new Scanner(System.in);
        while (true) {
            System.out.println("请输入用户名:");
            tempName = reader.next();
            if (userDatabase.findUserByName(tempName) != null) {
                System.out.println("该用户名已经被占用，请重新输入:");
                continue;
            }
            // 设置邮箱
            System.out.println("请输入邮箱:");
            tempEmail = reader.next();
            if (!isE_mailValid(tempEmail)) {
                System.out.println("您所输入的邮箱格式不正确，请检查后重新输入");
                continue;
            } else if (userDatabase.findUserByEmail(tempEmail) != null) {
                System.out.println("该邮箱已被绑定，请重新输入");
                continue;
            }
            // 设置用户手机号
            System.out.println("请输入用户手机号:");
            tempNum = reader.next();
            if (userDatabase.findUserByPhone(tempNum) != null) {
                System.out.println("该手机号已经被绑定，请重新输入:");
                continue;
            }
            // 设置用户ID，随机
            tempID = UUID.randomUUID().toString().substring(0, 8);
            if (userDatabase.findUserByID(tempID) != null) {
                System.out.println("该用户id已经被占用,请重新输入:");
                continue;
            }
            // 如果所有信息都输入正确，跳出循环
            break;
        }
        // 设置注册时间
        LocalDateTime now = LocalDateTime.now();
        // 设置默认密码
        tempPassword = "#YNUforeground777";
        foreground.setID(tempID);
        foreground.setName(tempName);
        foreground.setEmail(tempEmail);
        foreground.setPhoneNum(tempNum);
        foreground.setPassword(tempPassword);
        foreground.setRegisterTime(now.toString());
        foreground.setRole("FOREGROUND");
        userDatabase.addUser(foreground);
        System.out.println("添加前台用户成功！");
        System.out.println("新用户的id为:" + foreground.getID());
        System.out.println("新用户的默认密码为:" + foreground.getPassword());
        return true;
    }
}
