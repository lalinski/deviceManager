package com.lalin.test.site.blog.mix.one.Dao;

import com.lalin.test.site.blog.mix.one.entity.DeviceBorrowList;
import com.lalin.test.site.blog.mix.one.entity.Employee;
import com.lalin.test.site.blog.mix.one.entity.Probe;
import com.lalin.test.site.blog.mix.one.repository.BorrowListRepository;
import com.lalin.test.site.blog.mix.one.repository.EmployeeRepository;
import com.lalin.test.site.blog.mix.one.repository.ProbeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;



import javax.annotation.PostConstruct;


import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

/**
 * Created by frzhao on 2017/10/7.
 */

@Repository
public class SqlServerConn {


    public  String driverName;
    public  String dbURL;
    public  String userName;
    public  String userPwd;

    @Autowired
    public EmployeeRepository employeeRepository;
    @Autowired
    public ProbeRepository probeRepository;
    @Autowired
    public BorrowListRepository borrowListRepository;
    public Connection dbConn = null;
    public PreparedStatement psmt = null;
    public ResultSet rst = null;
    public Statement stmt;
    List<Employee> employees = new ArrayList<Employee>();
    Employee employee = null;
    public String sql = "select * from userinfo;";

    public SqlServerConn(){
        InputStream inputStream = SqlServerConn.class.getClassLoader().getResourceAsStream("sqlserver.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputStream);
            driverName = properties.getProperty("sqlServer.driverName");
            dbURL = properties.getProperty("sqlServer.dbURL");
            userName = properties.getProperty("sqlServer.userName");
            userPwd  = properties.getProperty("sqlServer.userPwd");

//            System.out.println(driverName);
//            System.out.println(dbURL);
//            System.out.println(userName);
//            System.out.println(userPwd);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void getConnection() {


        try {
            Class.forName(driverName);
            dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
            System.out.println("Connection Successful!");
            psmt = dbConn.prepareStatement(sql);
            rst = psmt.executeQuery();
            Employee employee = null;

            while (rst.next()) {
                employee = new Employee();
                employee.setId(rst.getInt("id"));
                SimpleGrantedAuthority grantAdmin = null;
                if(!StringUtils.isEmpty(rst.getString("Password"))){
                    employee.setPassword(rst.getString("Password").trim());
                    grantAdmin = new SimpleGrantedAuthority("ROLE_ADMIN");
                }else{
                    employee.setPassword(rst.getString("Password"));
                }
      //          employee.setPassword(rst.getString("Password").trim());
                employee.setEmail(rst.getString("Email").trim());
                employee.setUsername(rst.getString("UserName").trim());
                employee.setDisplayName(rst.getString("DisplayName").trim());

                SimpleGrantedAuthority grant = new SimpleGrantedAuthority("ROLE_USER");
                Set<SimpleGrantedAuthority> set = new HashSet<>();
                set.add(grant);
                if(grantAdmin != null){
                    set.add(grantAdmin);
                }
                employee.setAuthorities(set);

                employeeRepository.save(employee);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rst.close();
                psmt.close();
                dbConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
    @PostConstruct
    public void printLog(){
        System.out.println("it is post construct");
    }
    public void getConnectionForProbe() {
        try {
            Class.forName(driverName);
            dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
            System.out.println("Connection Successful!");
            psmt = dbConn.prepareStatement("select * from Probe;");
            rst = psmt.executeQuery();
            Probe probe = null;
            while (rst.next()) {
                probe = new Probe();
                probe.setId(rst.getInt("id"));
                if(rst.getString("Name") != null){
                    probe.setName(rst.getString("Name").trim());
                }
                if(rst.getString("BarCode") != null){
                    probe.setBarCode(rst.getString("BarCode").trim());
                }
                if(rst.getString("SN") != null){
                    probe.setSn(rst.getString("SN").trim());
                }
                if(rst.getString("Label") != null){
                    probe.setLabel(rst.getString("Label").trim());
                }
                if(rst.getString("Description") != null){
                    probe.setDescription(rst.getString("Description").trim());
                }
                if(rst.getString("Status") != null){
                    probe.setStatus(rst.getString("Status").trim());
                }
                if(rst.getString("Groups") != null){
                    probe.setGroups(rst.getString("Groups").trim());
                }
                if(rst.getString("Owner") != null){
                    probe.setOwner(rst.getString("Owner").trim());
                }
                if(rst.getString("Borrower") != null){
                    probe.setBorrower(rst.getString("Borrower").trim());
                }
                if(rst.getString("Purpose") != null){
                    probe.setPurpose(rst.getString("Purpose").trim());
                }
                if(rst.getString("Location") != null){
                    probe.setLocation(rst.getString("Location").trim());
                }
                probe.setStartTime(rst.getDate("StartTime"));
                probe.setEndTime(rst.getDate("EndTime"));
                if(rst.getString("Note") != null){
                    probe.setNote(rst.getString("Note").trim());
                }

                probeRepository.save(probe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rst.close();
                psmt.close();
                dbConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
    public void getConnectionForBorrowList() {
        try {
            Class.forName(driverName);
            dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
            System.out.println("Connection Successful!");
            stmt = dbConn.createStatement(); //"select * from DeviceBorrowList;"
            rst = stmt.executeQuery("select * from DeviceBorrowList;");
            DeviceBorrowList deviceBorrowItem = null;
            Class.forName(driverName);
        //    DriverManager.getConnection(dbURL, )
            while (rst.next()) {
                deviceBorrowItem = new DeviceBorrowList();
                deviceBorrowItem.setId(rst.getInt("id"));

                if(rst.getString("DeviceName") != null){
                    deviceBorrowItem.setDeviceName(rst.getString("DeviceName").trim());
                }
                if(rst.getString("DeviceLabel") != null){
                    deviceBorrowItem.setDeviceLabel(rst.getString("DeviceLabel").trim());
                }
                if(rst.getString("DeviceID") != null){
                    deviceBorrowItem.setDeviceId(rst.getString("DeviceID").trim());
                }
                if(rst.getString("Lender") != null){
                    deviceBorrowItem.setLender(rst.getString("Lender").trim());
                }
                if(rst.getString("Borrower") != null){
                    deviceBorrowItem.setBorrower(rst.getString("Borrower").trim());
                }
                if(rst.getString("Status") != null){
                    deviceBorrowItem.setStatus(rst.getString("Status").trim());
                }
                if(rst.getDate("StartTime") != null){
                    deviceBorrowItem.setStartTime(rst.getDate("StartTime"));
                }
                if(rst.getDate("EndTime") != null){
                    System.out.println(rst.getDate("EndTime"));
                    deviceBorrowItem.setEndTime(rst.getDate("EndTime"));
                }
                if(rst.getDate("ReturnTime") != null){
                    deviceBorrowItem.setReturnTime(rst.getDate("ReturnTime"));
                }
                if(rst.getString("Purpose") != null){
                    deviceBorrowItem.setPurpose(rst.getString("Purpose").trim());
                }

                deviceBorrowItem.setDuration(rst.getInt("Duration"));
                deviceBorrowItem.setOverdays(rst.getInt("Overdays"));
                borrowListRepository.save(deviceBorrowItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rst.close();
                stmt.close();
                dbConn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}
