package bacsta;

import allDatabase.UserDatabase;

import java.util.Random;
import java.util.Scanner;


public class Users {
    private String ID=null;
    private String name=null;
    private String password=null;
    private String role=null;
    private String email=null;
    private String registerTime=null;
    private String phoneNum=null;
    private int passwordAttempts = 0;
    UserDatabase userDatabase=new UserDatabase();
    Boundary boundary=new Boundary();
    
    public void setID(String userID){
        this.ID=userID;
    }

    public String getID(){
        return this.ID;
    }

    public void setName(String userName){
        this.name=userName;
    }

    public String getName(){
        return this.name;
    }

    public void setPassword(String userPassword){
        this.password=userPassword;
    }

    public String getPassword(){
        return this.password;
    }

    public void setRole(String userRole){
        this.role=userRole;
    }

    public String getRole(){
        return this.role;
    }

    public void setEmail(String userEmail){
        this.email=userEmail;
    }

    public String getEmail(){
        return this.email;
    }

    public void setRegisterTime(String registerTime2){
        this.registerTime=registerTime2.toString();
    }

    public String getRegisterTime(){
        return this.registerTime;
    }

    public void setPhoneNum(String userPhoneNum){
        this.phoneNum=userPhoneNum;
    }

    public String getPhoneNum(){
        return this.phoneNum;
    }

    boolean login() {
        String tempName;
        String tempPassword;
        Users targetUser;
        boolean isAccountLocked = false;
        while (true) {
            System.out.println("----------登录----------");
            System.out.println("请输入你的用户名");
            Scanner reader = new Scanner(System.in);
            tempName = reader.next();
            targetUser = userDatabase.findUserByName(tempName);
            if (targetUser == null) {
                System.out.println("用户名不存在，请检查后再次尝试");
                continue;
            }

            if (isAccountLocked && targetUser.getRole().equals("CUSTOMER")) {
                System.out.println("账户已锁定，请联系管理员解锁。");
                break; // 账户已锁定，退出登录循环
            }

            System.out.println("请输入你的密码");
            tempPassword = reader.next();

            if (targetUser.getPassword().equals(tempPassword)) {
                System.out.println("登陆成功！");
                boundary.function(targetUser);
                break;
            } else {
                System.out.println("用户名或密码错误，请检查后再次尝试");
                targetUser.passwordAttempts++;
                if (targetUser.passwordAttempts >= 5 && targetUser.getRole().equals("CUSTOMER")) {
                    System.out.println("密码错误次数达到上限，账户已锁定。");
                    isAccountLocked = true;
                    break; // 账户已锁定，退出登录循环2
                }
                boundary.startBoundary();
            }
        }

        return !isAccountLocked;
    }

    void logout(){
        boundary.startBoundary();
    };

    boolean isPasswordVaild(String password){
        String format="^(?![A-Za-z0-9]+$)(?![a-z0-9\\W]+$)(?![A-Za-z\\W]+$)(?![A-Z0-9\\W]+$)[a-zA-Z0-9\\W]{8,}$";
        if((password!=null)&&(!password.isEmpty()))
            return password.matches(format);
        else
            return false;
    }

    boolean isE_mailValid(String email){
        String format="^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$";
        if((email!=null)&&(!email.isEmpty()))
            return email.matches(format);
        
        else
            return false;
    }

    public void resetPassword() {
        System.out.println("----------忘记密码----------");
        System.out.println("请输入您的用户名");
        Scanner sc = new Scanner(System.in);
        String username = sc.next();
        Users targetUser = userDatabase.findUserByName(username);
        if (targetUser == null) {
            System.out.println("用户名不存在，请检查后再次尝试");
            return;
        }
        System.out.println("请输入绑定的邮箱地址");
        String email = sc.next();
        if (!targetUser.getEmail().equals(email)) {
            System.out.println("邮箱地址不匹配，请检查后再次尝试");
            return;
        }
        // 生成随机密码
        String newPassword = generateRandomPassword();
        targetUser.setPassword(newPassword);
        // 模拟
        System.out.println("我们将为您随机生成新密码并发送到您的邮箱中，请注意查收");
        System.out.println("新的登录密码已发送到您的邮箱，请查看并登录。");
        System.out.println("-----------------**邮箱----------------------");
        System.out.println("尊敬的**影院用户"+username+"，您的新密码是：" + newPassword);
        System.out.println("--------------------------------------------");
    }

    public String generateRandomPassword() {//生成随机密码
        String characters = "#￥@！*ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder newPassword = new StringBuilder();
        Random random = new Random();
        int passwordLength = 8;
        for (int i = 0; i < passwordLength; i++) {
            int index = random.nextInt(characters.length());
            newPassword.append(characters.charAt(index));
        }
        return newPassword.toString();
    }

}
