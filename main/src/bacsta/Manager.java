package bacsta;

import allDatabase.MovieDatabase;
import allDatabase.ShowtimeDatabase;
import allDatabase.UserDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Manager extends Users {
    MovieDatabase movieDatabase=new MovieDatabase();
    ShowtimeDatabase showtimeDatabase=new ShowtimeDatabase();


    public Manager(String ID, String name, String password, String role, String email, String registerTime, String phoneNum){
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
        System.out.println("----------修改自身密码----------");
        Scanner reader = new Scanner(System.in);
        while (true) {
            System.out.println("请输入你原先的密码:");
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
                        } else {
                            System.out.println("密码不符合要求(密码必须长度大于8,包括大小写字母、数字以及标点)，请检查后重新输入！");
                        }
                    }
                }
                break; // 修改密码成功后跳出外层循环
            } else{
                System.out.println("用户名或密码错误，请检查后再次尝试");
            }
        }
        return success;
    }

    public boolean addManager() {
        String tempName;
        String tempPassword;
        String tempEmail;
        String tempID;
        String tempNum;
        System.out.println("----------新增用户----------");
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
        tempPassword = "#YNUmanager111";
        setID(tempID);
        setName(tempName);
        setEmail(tempEmail);
        setPhoneNum(tempNum);
        setPassword(tempPassword);
        setRegisterTime(now.toString());
        setRole("MANAGER");
        userDatabase.addUser(this);
        System.out.println("更改经理用户信息成功！");
        System.out.println("新用户的id为:" + getID());
        System.out.println("新用户的默认密码为:" + getPassword());
        return true;
    }
    public boolean changeOthersPassword(){
        String tempName;
        Users targetUser=new Users();
        System.out.println("----------重置顾客密码----------");
        System.out.println("请输入待修改密码用户的用户名");
        Scanner reader=new Scanner(System.in);
        tempName=reader.next();
        targetUser=userDatabase.findUserByName(tempName);
        if(targetUser!=null&&(targetUser.getRole().equals("CUSTOMER"))){
            targetUser.setPassword("YNU111111");
            userDatabase.updateUser(targetUser);
            return true;
        }
        else{
            System.out.println("用户名错误或该用户不为顾客");
            return false;
        }
    }

    public boolean listAllFilmInfo(){
        System.out.println("----------列出所有正在上映的影片信息----------");
        movieDatabase.allMovie();
        return true;
    }

    public boolean addFilmInfo() {
        String tempTitle;
        String tempDirector;
        String tempLeadingRole;
        String tempSynopsis;
        String tempDuration;
        System.out.println("----------添加影片信息----------");
        Scanner reader = new Scanner(System.in);
        while (true) {
            System.out.println("请输入影片名:");

            tempTitle = reader.next();
            Movie targetMovie = movieDatabase.findMovieByTitle(tempTitle);
            if (targetMovie == null) {
                break;
            }
            System.out.println("该影片已添加，请重新输入:");
        }
        System.out.println("影片导演:");
        tempDirector = reader.next();
        System.out.println("影片主演:");
        tempLeadingRole = reader.next();
        System.out.println("影片剧情简介:");
        tempSynopsis = reader.next();
        System.out.println("影片时长:");
        tempDuration = reader.next();
        Movie movie = new Movie(tempTitle,tempDirector,tempLeadingRole,tempSynopsis,tempDuration);
        movieDatabase.addMovie(movie);
        System.out.println("添加影片信息成功!");
        return true;
    }

    public boolean editFilmInfo() {
        int select;
        String tempTitle;
        String tempDirector;
        String tempLeadingRole;
        String tempSynopsis;
        String tempDuration;
        Movie targetMovie;
        System.out.println("----------修改影片信息----------");
        Scanner reader = new Scanner(System.in);
        while (true) {
            System.out.print("1.修改片名\n2.修改影片导演\n3.修改影片主演\n4.修改剧情简介\n5.修改影片时长\n");
            System.out.print("请选择你要操作的功能:");
            select = reader.nextInt();
            if (select != 1 && select != 2 && select != 3 && select != 4 && select != 5) {
                System.out.println("找不到对象，请重新输入！");
                continue; // 重新显示菜单
            }
            System.out.println("请输入片名:");
            tempTitle = reader.next();
            targetMovie = movieDatabase.findMovieByTitle(tempTitle);
            if (targetMovie == null) {
                System.out.println("不存在此影片，请检查后重新输入！");
                continue; // 重新显示菜单
            }
            switch (select) {
                case 1:
                    System.out.println("----------修改片名----------");
                    System.out.println("当前该影片的片名:" + targetMovie.getTitle());
                    System.out.println("请输入要改为的新片名:");
                    tempTitle = reader.next();
                    if (tempTitle.equals(targetMovie.getTitle())) {
                        System.out.println("当前片名与修改片名一致！");
                        continue; // 重新显示菜单
                    }
                    targetMovie.setTitle(tempTitle);
                    movieDatabase.updateMovie(targetMovie);
                    break;
                case 2:
                    System.out.println("----------修改影片导演----------");
                    System.out.println("当前该影片的导演:" + targetMovie.getDirector());
                    System.out.println("请输入要更改为的导演:");
                    tempDirector = reader.next();
                    if (tempDirector.equals(targetMovie.getDirector())) {
                        System.out.println("当前导演与修改导演一致！");
                        continue; // 重新显示菜单
                    }
                    targetMovie.setDirector(tempDirector);
                    movieDatabase.updateMovie(targetMovie);
                    break;
                case 3:
                    System.out.println("----------修改影片主演----------");
                    System.out.println("当前该影片的主演:" + targetMovie.getLeadingRole());
                    System.out.println("请输入要更改为的主演:");
                    tempLeadingRole = reader.next();
                    if (tempLeadingRole.equals(targetMovie.getLeadingRole())) {
                        System.out.println("当前主演与修改主演一致！");
                        continue; // 重新显示菜单
                    }
                    targetMovie.setLeadingRole(tempLeadingRole);
                    movieDatabase.updateMovie(targetMovie);
                    break;
                case 4:
                    System.out.println("----------修改剧情简介----------");
                    System.out.println("当前该影片的剧情简介:" + targetMovie.getSynopsis());
                    System.out.println("请输入要更改为的剧情简介:");
                    tempSynopsis = reader.next();
                    if (tempSynopsis.equals(targetMovie.getSynopsis())) {
                        System.out.println("当前剧情简介与修改剧情简介一致！");
                        continue; // 重新显示菜单
                    }
                    targetMovie.setSynopsis(tempSynopsis);
                    movieDatabase.updateMovie(targetMovie);
                    break;
                case 5:
                    System.out.println("----------修改影片时长----------");
                    System.out.println("当前该影片的时长:" + targetMovie.getDuration());
                    System.out.println("请输入要更改为的时长:");
                    tempDuration = reader.next();
                    if (tempDuration.equals(targetMovie.getDuration())) {
                        System.out.println("当前时长与修改时长一致！");
                        continue; // 重新显示菜单
                    }
                    targetMovie.setDuration(tempDuration);
                    movieDatabase.updateMovie(targetMovie);
                    break;
            }
            return true;
        }
    }

    public boolean deleteFilmInfo(){
        String tempTitle;
        String choose;
        Movie targetMovie;
        System.out.println("----------删除影片信息----------");
        Scanner reader = new Scanner(System.in);
        while (true) {
            System.out.println("请输入将要删除影片的片名:");
            tempTitle = reader.next();
            targetMovie = movieDatabase.findMovieByTitle(tempTitle);
            if (targetMovie != null) {
                break;
            } else {
                System.out.println("该影片不存在");
            }
        }
        while (true) {
            System.out.println("确定要删除该影片所有信息吗?删除之后将无法恢复！");
            System.out.println("Y/N(注意区分大小写！)");
            choose = reader.next();
            if (choose.equals("Y")) {
                movieDatabase.deleteMovie(targetMovie);
                break; // 删除影片后跳出循环
            } else if (choose.equals("N")) {
                boundary.administratorFunctionBoundary(this);
                break; // 放弃删除后跳出循环
            } else {
                System.out.println("请输入正确的选项(Y/N)");
            }
        }
        return true;
    }   

    public boolean inquireFilmInfo() {
        int select;
        String tempTitle;
        String tempDirector;
        String tempLeadingRole;
        List<Movie> filteredMovies = new ArrayList<>(); // 用于存储符合条件的电影

        System.out.println("----------查询影片信息----------");
        Scanner reader = new Scanner(System.in);
        while (true) {
            System.out.print("1.按影片名称查询\n2.按导演查询\n3.按主演查询\n4.组合查询\n");
            System.out.print("请选择你要操作的功能:");
            select = reader.nextInt();
            if (select != 1 && select != 2 && select != 3 && select != 4) {
                System.out.println("找不到对象，请重新输入！");
            } else {
                break; // 选择合法选项时跳出循环
            }
        }
        switch (select) {
            case 1:
                System.out.println("----------按影片名称查询----------");
                System.out.println("请输入片名:");
                tempTitle = reader.next();
                for (int i = 0; i < MovieDatabase.movies.size(); i++) {
                    if (MovieDatabase.movies.get(i).getTitle().equals(tempTitle)) {
                        filteredMovies.add(MovieDatabase.movies.get(i)); // 将符合条件的电影加入列表
                    }
                }
                break;
            case 2:
                System.out.println("----------按导演查询----------");
                System.out.println("请输入导演:");
                tempDirector = reader.next();
                for (int i = 0; i < MovieDatabase.movies.size(); i++) {
                    if (MovieDatabase.movies.get(i).getDirector().equals(tempDirector)) {
                        filteredMovies.add(MovieDatabase.movies.get(i)); // 将符合条件的电影加入列表
                    }
                }
                break;
            case 3:
                System.out.println("----------按主演查询----------");
                System.out.println("请输入主演:");
                tempLeadingRole = reader.next();
                for (int i = 0; i < MovieDatabase.movies.size(); i++) {
                    if (MovieDatabase.movies.get(i).getLeadingRole().equals(tempLeadingRole)) {
                        filteredMovies.add(MovieDatabase.movies.get(i)); // 将符合条件的电影加入列表
                    }
                }
                break;
            case 4:
                System.out.println("----------组合查询----------");
                System.out.println("请输入影片名称:");
                tempTitle = reader.next();
                System.out.println("请输入导演:");
                tempDirector = reader.next();
                System.out.println("请输入主演:");
                tempLeadingRole = reader.next();
                // 根据用户输入的条件过滤电影列表
                for (int i = 0; i < MovieDatabase.movies.size(); i++) {
                    Movie movie = MovieDatabase.movies.get(i);
                    if (movie.getTitle().equals(tempTitle) && movie.getDirector().equals(tempDirector)
                            && movie.getLeadingRole().equals(tempLeadingRole)) {
                        filteredMovies.add(movie);
                    }
                }
                break;
        }
        if (filteredMovies.isEmpty()) {
            System.out.println("没有符合条件的电影");
        } else {
            for (Movie movie : filteredMovies) {
                System.out.println("-----------------------------");
                System.out.println("影片名称: " + movie.getTitle());
                System.out.println("导演: " + movie.getDirector());
                System.out.println("主演: " + movie.getLeadingRole());
                System.out.println("剧情简介: " + movie.getSynopsis());
                System.out.println("时长: " + movie.getDuration());
                System.out.println("-----------------------------");
            }
        }
        return true;
    }

    public boolean addSession(){
        String tempTheaterID;
        String tempShowtime;
        int tempPrice;
        String tempTitle;
        Movie tempMovie;
        Showtime showtime;
        System.out.println("----------增加场次----------");
        Scanner reader = new Scanner(System.in);
        while (true) {
            System.out.println("场次所在放映厅编号:");
            tempTheaterID = reader.next();
            System.out.println("场次时间:");
            Scanner readerT = new Scanner(System.in);
            tempShowtime = readerT.nextLine();
            if (!Showtime.isShowtimeValid(tempShowtime)) {
                System.out.println("您所输入的时间格式不正确，请检查后重新输入");
                continue;
            }
            System.out.println("场次价格:");
            tempPrice = reader.nextInt();
            System.out.println("场次所放映影片的片名:");
            tempTitle = reader.next();
            tempMovie=movieDatabase.findMovieByTitle(tempTitle);
            if(tempMovie==null){
                System.out.println("您所输入影片不存在，请检查后重新输入");
                continue;
            }
            break;
        }
        showtime=new Showtime(tempTheaterID, tempShowtime, tempPrice, tempMovie);
        showtimeDatabase.addShowtime(showtime);
        System.out.println("添加场次成功！");
        System.out.println("-----------------------------");
        System.out.println("放映厅:"+showtime.getTheaterID());
        System.out.println("时间:"+showtime.getShowTime());
        System.out.println("价格:"+showtime.getPrice());
        System.out.println("影片信息更新如下:");
        System.out.println("-----------------------------");
        showtime.showMovieInfo(tempMovie);
        return true;
    }

    public boolean editSession(){
        int select;
        String tempTheater;
        String tempTime;
        int tempPrice;
        String tempTitle;
        Movie tempMovie;
        Showtime targetShowtime;
        System.out.println("----------修改场次----------");
        System.out.print("1.修改放映厅\n2.修改场次时间\n3.修改场次价格\n4.修改场次所放映的影片\n");
        System.out.print("请选择你要操作的功能:");
        Scanner reader = new Scanner(System.in);
        select = reader.nextInt();
        while(true){
            if (select != 1 && select != 2 && select != 3&& select != 4) {
                    System.out.println("找不到对象，请重新输入！");
                    continue; // 继续循环以重新选择选项
            }
            else{
                Scanner reader1 = new Scanner(System.in);
                System.out.println("请输入场次所在放映厅:");
                tempTheater = reader1.next();
                System.out.println("请输入场次所在时间:");
                Scanner readerT = new Scanner(System.in);
                tempTime = readerT.nextLine();
                targetShowtime=showtimeDatabase.findShowtimeByTheaterAndTime(tempTheater, tempTime);
                if (targetShowtime==null) {
                    System.out.println("该场次不存在");
                    continue;
                }
                else{
                    switch (select) {
                        case 1:
                            System.out.println("----------修改放映厅----------");
                            System.out.println("当前该场次所在放映厅编号:" + targetShowtime.getTheaterID());
                            System.out.println("请输入要改为的放映厅编号:");
                            tempTheater = reader.next();
                            targetShowtime.setTheaterID(tempTheater);
                            showtimeDatabase.updateShowtime(targetShowtime);
                            break;
                        case 2:
                            System.out.println("----------修改场次时间----------");
                            System.out.println("当前该场次所在时间:" + targetShowtime.getShowTime());
                            System.out.println("请输入要改为的时间:");
                            tempTime = reader.nextLine();
                            targetShowtime.setShowTime(tempTime);
                            showtimeDatabase.updateShowtime(targetShowtime);
                            break;
                        case 3:
                            System.out.println("----------修改场次价格----------");
                            System.out.println("当前该场次价格:" + targetShowtime.getPrice());
                            System.out.println("请输入要改为的价格:");
                            tempPrice = reader.nextInt();
                            targetShowtime.setPrice(tempPrice);
                            showtimeDatabase.updateShowtime(targetShowtime);
                            break;
                        case 4:
                            System.out.println("----------修改场次影片----------");
                            System.out.println("当前该场次影片:" + targetShowtime.getMovie().getTitle());
                            System.out.println("请输入要改为的影片:");
                            tempTitle = reader.next();
                            tempMovie=movieDatabase.findMovieByTitle(tempTitle);
                            targetShowtime.setMovie(tempMovie);
                            showtimeDatabase.updateShowtime(targetShowtime);
                            break;
                        }
                }   
            }
        return true;
        }
    }

    public boolean deleteSession(){
        Showtime targetShowtime;
        String tempTheater;
        String tempTime;
        String choose;
        System.out.println("----------删除场次----------");
        while(true){
            System.out.println("请输入场次所在放映厅:");
            Scanner reader = new Scanner(System.in);
            tempTheater = reader.next();
            System.out.println("请输入场次所在时间:");
            Scanner readerT = new Scanner(System.in);
            tempTime = readerT.nextLine();
            targetShowtime=showtimeDatabase.findShowtimeByTheaterAndTime(tempTheater, tempTime);
            if (targetShowtime!=null) {
                System.out.println("确定要删除该用户所有信息吗?删除之后将无法恢复！");
                System.out.println("Y/N(注意区分大小写！)");
                choose = reader.next();
                if (choose.equals("Y")) {
                    showtimeDatabase.deleteShowtime(targetShowtime);
                    break; // 删除用户后跳出循环
                } else if (choose.equals("N")) {
                    break; // 放弃删除后跳出循环
                } else {
                    System.out.println("请输入正确的选项(Y/N)");
                }
            }
            else{
                System.out.println("未找到该场次");
            }
        }
        return true;
    }

    public boolean listAllSession(){
        System.out.println("----------列出所有场次----------");
        showtimeDatabase.allShowtime();
        return true;
    }

    public boolean listAllCustomerInfo(){
        System.out.println("----------列出所有顾客信息----------");
        for(int i=0;i<UserDatabase.users.size();i++){
            if(UserDatabase.users.get(i).getRole().equals("CUSTOMER")){
            Customer customer=(Customer)UserDatabase.users.get(i);
            System.out.println("用户id:"+customer.getID());
            System.out.println("用户用户名:"+customer.getName());
            System.out.println("用户级别:"+customer.getLV());
            System.out.println("用户注册时间:"+customer.getRegisterTime());
            System.out.println("用户累计消费总金额:"+customer.getAccumulatedConsumptionAmount());
            System.out.println("用户累计消费次数:"+customer.getCumulativeConsumption());
            System.out.println("用户手机号:"+customer.getPhoneNum());
            System.out.println("用户邮箱:"+customer.getEmail());
            System.out.println("\n");
          }
        }
        return true;
    }

    public boolean inquireCustomerInfo(){
        int choose1, choose2 = 0;
        String tempName;
        Customer targetUser = new Customer();
        System.out.println("----------查询顾客信息----------");
        Scanner reader = new Scanner(System.in);
        while (true) {
            System.out.print("1.查询顾客ID\n2.查询顾客级别\n3.查询顾客注册时间\n4.查询顾客累计消费总金额\n5.查询顾客累计消费次数\n6.查询顾客绑定手机号\n7.查询用户绑定邮箱\n");
            System.out.print("请选择你要操作的功能:");
            choose1 = reader.nextInt();
            if (choose1 != 1 && choose1 != 2 && choose1 != 3 && choose1 != 4&& choose1 != 5&& choose1 != 6&& choose1 != 7) {
                System.out.println("找不到对象，请重新输入！");
            } else {
                break; // 选择合法选项时跳出循环
            }
        }
        switch (choose1) {
            case 1:
                while (true) {
                    System.out.println("1.查询单个用户\n2.查询所有用户\n");
                    System.out.println("请选择你要操作的功能:");
                    choose2 = reader.nextInt();
                    if (choose2 != 1 && choose2 != 2) {
                        System.out.println("找不到对象，请重新输入！");
                    } else {
                        break; // 选择合法选项时跳出循环
                    }
                }
                switch (choose2) {
                    case 1:
                        while (true) {
                            System.out.println("请输入用户名:");
                            tempName = reader.next();
                            targetUser = (Customer) userDatabase.findUserByName(tempName);
                            if (targetUser != null && (targetUser.getRole().equals("CUSTOMER"))) {
                                System.out.println("该用户ID为:" + targetUser.getID());
                                break;
                            } else {
                                System.out.println("用户名错误或该用户不为顾客");
                            }
                        }
                        break;
                    case 2:
                        for (int i = 0; i < UserDatabase.users.size(); i++) {
                            if (UserDatabase.users.get(i).getRole().equals("CUSTOMER")) {
                                System.out.print(UserDatabase.users.get(i).getName() + ":");
                                System.out.println(UserDatabase.users.get(i).getID());
                            }
                        }
                        break;
                }
                break;
            case 2:
                while (true) {
                    System.out.print("1.查询单个用户\n2.查询所有用户\n");
                    System.out.print("请选择:");
                    choose2 = reader.nextInt();

                    if (choose2 != 1 && choose2 != 2) {
                        System.out.println("找不到对象，请重新输入！");
                    } else {
                        break; // 选择合法选项时跳出循环
                    }
                }
                switch (choose2) {
                    case 1:
                        while (true) {
                            System.out.println("请输入用户名：");
                            tempName = reader.next();
                            targetUser = (Customer) userDatabase.findUserByName(tempName);
                            if (targetUser != null && (targetUser.getRole().equals("CUSTOMER"))) {
                                System.out.println("该用户的顾客等级为:" + targetUser.getLV());
                                break;
                            } else {
                                System.out.println("用户名错误或该用户不为顾客");
                            }
                        }
                        break;
                    case 2:
                        for (int i = 0; i < UserDatabase.users.size(); i++) {
                            if (UserDatabase.users.get(i).getRole().equals("CUSTOMER")) {
                                Customer customer=(Customer)UserDatabase.users.get(i);
                                System.out.print(customer.getName() + ":");
                                System.out.println(customer.getLV());
                            }
                        }
                        break;
                }
                break;
            case 3:
                while (true) {
                    System.out.print("1.查询单个用户\n2.查询所有用户\n");
                    System.out.print("请选择你要操作的功能:");
                    choose2 = reader.nextInt();
                    if (choose2 != 1 && choose2 != 2) {
                        System.out.println("找不到对象，请重新输入！");
                    } else {
                        break; // 选择合法选项时跳出循环
                    }
                }
                switch (choose2) {
                    case 1:
                        while (true) {
                            System.out.println("请输入用户名:");
                            tempName = reader.next();
                            targetUser = (Customer)userDatabase.findUserByName(tempName);
                            if (targetUser != null && (targetUser.getRole().equals("CUSTOMER"))) {
                                System.out.println("该用户注册时间为:" + targetUser.getRegisterTime());
                                break;
                            } else {
                                System.out.println("用户名错误或该用户不为顾客");
                            }
                        }
                        break;
                    case 2:
                        for (int i = 0; i < UserDatabase.users.size(); i++) {
                            if (UserDatabase.users.get(i).getRole().equals("CUSTOMER")) {
                                System.out.print(UserDatabase.users.get(i).getName() + ":");
                                System.out.println(UserDatabase.users.get(i).getRegisterTime());
                            }
                        }
                        break;
                }
                break;
            case 4:
                while (true) {
                    System.out.print("1.查询单个用户\n2.查询所有用户\n");
                    System.out.print("请选择你要操作的功能:");
                    choose2 = reader.nextInt();
                    if (choose2 != 1 && choose2 != 2) {
                        System.out.println("找不到对象，请重新输入！");
                    } else {
                        break; // 选择合法选项时跳出循环
                    }
                }
                switch (choose2) {
                    case 1:
                        while (true) {
                            System.out.println("请输入用户名:");
                            tempName = reader.next();
                            targetUser = (Customer)userDatabase.findUserByName(tempName);
                            if (targetUser != null && (targetUser.getRole().equals("CUSTOMER"))) {
                                System.out.println("该用户累计消费总金额为:" + targetUser.getAccumulatedConsumptionAmount());
                                break;
                            } else {
                                System.out.println("用户名错误或该用户不为顾客");
                            }
                        }
                        break;
                    case 2:
                        for (int i = 0; i < UserDatabase.users.size(); i++) {
                            if (UserDatabase.users.get(i).getRole().equals("CUSTOMER")) {
                                Customer customer=(Customer)UserDatabase.users.get(i);
                                System.out.print(customer.getName() + ":");
                                System.out.println(customer.getAccumulatedConsumptionAmount());
                            }
                        }
                        break;
                }
                break;
            case 5:
                while (true) {
                    System.out.print("1.查询单个用户\n2.查询所有用户\n");
                    System.out.print("请选择你要操作的功能:");
                    choose2 = reader.nextInt();
                    if (choose2 != 1 && choose2 != 2) {
                        System.out.println("找不到对象，请重新输入！");
                    } else {
                        break; // 选择合法选项时跳出循环
                    }
                }
                switch (choose2) {
                    case 1:
                        while (true) {
                            System.out.println("请输入用户名:");
                            tempName = reader.next();
                            targetUser = (Customer)userDatabase.findUserByName(tempName);
                            if (targetUser != null && (targetUser.getRole().equals("CUSTOMER"))) {
                                System.out.println("该用户累计消费次数为:" + targetUser.getCumulativeConsumption());
                                break;
                            } else {
                                System.out.println("用户名错误或该用户不为顾客");
                            }
                        }
                        break;
                    case 2:
                        for (int i = 0; i < UserDatabase.users.size(); i++) {
                            if (UserDatabase.users.get(i).getRole().equals("CUSTOMER")) {
                                Customer customer=(Customer)UserDatabase.users.get(i);
                                System.out.print(customer.getName() + ":");
                                System.out.println(customer.getCumulativeConsumption());
                            }
                        }
                        break;
                }
                break;
            case 6:
                while (true) {
                    System.out.print("1.查询单个用户\n2.查询所有用户\n");
                    System.out.print("请选择你要操作的功能:");
                    choose2 = reader.nextInt();
                    if (choose2 != 1 && choose2 != 2) {
                        System.out.println("找不到对象，请重新输入！");
                    } else {
                        break; // 选择合法选项时跳出循环
                    }
                }
                switch (choose2) {
                    case 1:
                        while (true) {
                            System.out.println("请输入用户名或用户ID:");
                            tempName = reader.next();
                            targetUser = (Customer)userDatabase.findUserByName(tempName);
                            if (targetUser != null && (targetUser.getRole().equals("CUSTOMER"))) {
                                System.out.println("该用户绑定手机号为:" + targetUser.getPhoneNum());
                                break;
                            } else {
                                System.out.println("用户名错误或该用户不为顾客");
                            }
                        }
                        break;
                    case 2:
                        for (int i = 0; i < UserDatabase.users.size(); i++) {
                            if (UserDatabase.users.get(i).getRole().equals("CUSTOMER")) {
                                Customer customer=(Customer)UserDatabase.users.get(i);
                                System.out.print(customer.getName() + ":");
                                System.out.println(customer.getPhoneNum());
                            }
                        }
                        break;
                }
                break;
            case 7:
                while (true) {
                    System.out.print("1.查询单个用户\n2.查询所有用户\n");
                    System.out.print("请选择你要操作的功能:");
                    choose2 = reader.nextInt();
                    if (choose2 != 1 && choose2 != 2) {
                        System.out.println("找不到对象，请重新输入！");
                    } else {
                        break; // 选择合法选项时跳出循环
                    }
                }
                switch (choose2) {
                    case 1:
                        while (true) {
                            System.out.println("请输入用户名或用户ID:");
                            tempName = reader.next();
                            targetUser = (Customer)userDatabase.findUserByName(tempName);
                            if (targetUser != null && (targetUser.getRole().equals("CUSTOMER"))) {
                                System.out.println("该用户绑定邮箱为:" + targetUser.getEmail());
                                break;
                            } else {
                                System.out.println("用户名错误或该用户不为顾客");
                            }
                        }
                        break;
                    case 2:
                        for (int i = 0; i < UserDatabase.users.size(); i++) {
                            if (UserDatabase.users.get(i).getRole().equals("CUSTOMER")) {
                                Customer customer=(Customer)UserDatabase.users.get(i);
                                System.out.print(customer.getName() + ":");
                                System.out.println(customer.getPhoneNum());
                            }
                        }
                        break;
                }
                break;
        }
        return true;
    }
}
