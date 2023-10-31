package allDatabase;


import bacsta.Customer;
import bacsta.Users;
import allDao.UserDAO;
import java.util.ArrayList;
import java.util.List;


public class UserDatabase implements UserDAO {//用户资料库？
    public static List<Users> users=new ArrayList<>();//链表

//    @Override
    public boolean addUser(Users user) {
        users.add(user);
        return true;
    }

//    @Override
    public boolean updateUser(Users user){
        int index=-1;
        if(users.contains(user)){
            index=users.indexOf(user);
            users.set(index, user);
            System.out.println("用户信息更新成功！");
            return true;
        }
        else{
            System.out.println("未找到该用户");
            return false;
        }
    }

//    @Override
     public boolean deleteUser(Users user) {
        users.remove(user);
        return true;
    }

//    @Override
    public void allCineplexUser() {
        for(int i=0;i<users.size();i++){
            if(users.get(i).getRole().equals("MANAGER")||users.get(i).getRole().equals("FOREGROUND")
                    ||users.get(i).getRole().equals("ADMINISTRATOR")){
                System.out.println("用户id:"+users.get(i).getID());
                System.out.println("用户用户名:"+users.get(i).getName());
                System.out.println("用户注册时间:"+users.get(i).getRegisterTime());
                System.out.println("用户类型:"+users.get(i).getRole());
                System.out.println("用户手机号:"+users.get(i).getPhoneNum());
                System.out.println("用户邮箱:"+users.get(i).getEmail());
                System.out.println("\n");
            }else if(users.get(i).getRole().equals("COSTOMER")){
                Customer customer=(Customer)users.get(i);
                System.out.println("用户id:"+customer.getID());
                System.out.println("用户用户名:"+customer.getName());
                System.out.println("用户类型:"+customer.getRole());
                System.out.println("用户级别:"+customer.getLV());
                System.out.println("用户注册时间:"+customer.getRegisterTime());
                System.out.println("用户累计消费总金额:"+customer.getAccumulatedConsumptionAmount());
                System.out.println("用户累计消费次数:"+customer.getCumulativeConsumption());
                System.out.println("用户手机号:"+customer.getPhoneNum());
                System.out.println("用户邮箱:"+customer.getEmail());
                System.out.println("\n");
            }
        }
    }    

    public Users findUserByName(String targetName) {
        for (Users user : users) {
            if(user.getName()==null)
                continue;
            else if (user.getName().equals(targetName)) {
                return user; // 找到匹配的用户并返回
            }
        }
        return null; // 没有找到匹配的用户，返回null或者其他适当的值
    }
    
    public Users findUserByID(String targetID) {
        for (Users user : users) {
            if(user.getID()==null)
                continue;
            else if (user.getID().equals(targetID)) {
                return user; // 找到匹配的用户并返回
            }
    }
    return null; // 没有找到匹配的用户，返回null或者其他适当的值
    }

    public Users findUserByEmail(String targetEmail) {
        
        for (Users user : users) {
            if(user.getEmail()==null)
                continue;
            else if (user.getEmail().equals(targetEmail)) {
                return user; // 找到匹配的用户并返回
            }
    }
    return null; // 没有找到匹配的用户，返回null或者其他适当的值
    }

    public Users findUserByPhone(String targetPhone) {
        for (Users user : users) {
            if(user.getPhoneNum()==null)
                continue;
            else if (user.getPhoneNum().equals(targetPhone)) {
                return user; // 找到匹配的用户并返回
            }
        }
    return null; // 没有找到匹配的用户，返回null或者其他适当的值
    }
}