package com.lalin.test.site.blog.mix.one.controller;

import com.lalin.test.site.blog.mix.one.Dao.SqlServerConn;
import com.lalin.test.site.blog.mix.one.Dao.User;
import com.lalin.test.site.blog.mix.one.entity.BorrowingProbe;
import com.lalin.test.site.blog.mix.one.entity.DeviceBorrowList;
import com.lalin.test.site.blog.mix.one.entity.Employee;
import com.lalin.test.site.blog.mix.one.entity.Probe;
import com.lalin.test.site.blog.mix.one.repository.EmployeeRepository;
import com.lalin.test.site.blog.mix.one.repository.ProbeRepository;
import com.lalin.test.site.blog.mix.one.service.BorrowingService;
import com.lalin.test.site.blog.mix.one.service.DeviceBorrowService;
import com.lalin.test.site.blog.mix.one.service.ProbeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by frzhao on 2017/4/22.
 */
@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    public SqlServerConn sqlServerConn;

    @Autowired
    private ProbeRepository probeRepository;

    @Resource
    private ProbeService probeService;

    @Autowired
    private DeviceBorrowService deviceBorrowService;

    @Autowired
    private BorrowingService borrowingService;
    public static Logger logger = LoggerFactory.getLogger(UserController.class);
    @RequestMapping("/register")
    public String userSave(@RequestParam("username") String name, @RequestParam("password") String password){
        Employee employee = new Employee();
        employee.setUsername(name);
        employee.setPassword(password);
        SimpleGrantedAuthority grant = new SimpleGrantedAuthority("ROLE_USER");
        Set<SimpleGrantedAuthority> set = new HashSet<SimpleGrantedAuthority>();
        set.add(grant);
        employee.setAuthorities(set);
        employeeRepository.save(employee);
     //   return "forward:/index";
        return "username: " + name +"<br/>" + "password: " + password;
    }

    @RequestMapping("/find/{id}")
    @ResponseBody
    public String find(@PathVariable("id") Integer ids){
         Employee user = employeeRepository.findOne(ids);
         System.out.println(user.toString());
         return user.toString();
    }
    @RequestMapping("/findname/{name}")
    public String find(@PathVariable("name") String name){
        List<Employee> list = new ArrayList<Employee>();
        list = employeeRepository.findByUsername(name);
        if(list != null && list.size() > 0)
        System.out.println(list.get(0).toString());
        return list.get(0).toString();
    }
    @RequestMapping("/userNvidia")
    public String findNVidiaer(Model model){
        sqlServerConn.getConnection();
        sqlServerConn.getConnectionForProbe();
        return "index/login";
    }

    @RequestMapping("/bootLogin")
    public String bootLogin(){
    //    sqlServerConn.getConnection();
        return "index/signin";
    }
    @RequestMapping("/bootLogin2")
    public String bootLogin2(){
        //    sqlServerConn.getConnection();
        return "index/Probe";
    }
    @RequestMapping("/bootLogin3")
    public String bootLogin3(Model model){
        //    sqlServerConn.getConnection();
        List<Probe> probeList = probeRepository.findAll();//then the borrower and the borrow time information will be added
        model.addAttribute("probeList", 6);


        return "index/Probe";
    }
    @RequestMapping(value={"/probeList/{current}", "/probeList"})
    public String ProbeList(Model model, @PathVariable(value = "current", required = false) Integer searchNum){
        //    sqlServerConn.getConnection();
       // List<Probe> probeList = probeRepository.findAll();//then the borrower and the borrow time information will be added
    //    List<Probe> probeList = probeService.getProbes();
        if(searchNum == null) {
            searchNum = 0;
        }else{
            searchNum--;
        }
        System.out.println("searchNum=" + searchNum.toString());
        Page<Probe> page = probeService.pageProbe(searchNum);
        List<Probe> probeList = page.getContent();
        model.addAttribute("probeList", page.getContent());
        System.out.println("page.getTotalPages: " + page.getTotalPages());
        System.out.println("page.getTotalElements: " + page.getTotalElements());
        System.out.println("page.getNumberOfElements: " + page.getNumberOfElements());
     //   System.out.println(probeList.size());
        int[] pageNum = null;
        if(page.getTotalPages() > 0) {
            pageNum = new int[page.getTotalPages()];
            for(int i = 0; i < pageNum.length; i++){
                pageNum[i] = i + 1;
            }
        }
        int currentPage = page.getNumber() + 1;
        System.out.println("page.getNumber: " + page.getNumber());
    /*    if(probeList.size() < 35){
            pageNum = new int[1];
        }else{
            if(probeList.size() % 35 != 0)
                pageNum = new int[probeList.size() / 35 + 1];
            else
                pageNum = new int[probeList.size() / 35];
        }
        for(int i = 1; i < pageNum.length; i++){
            pageNum[i] = i;
        }*/

        model.addAttribute("probeNum", pageNum);
        model.addAttribute("currentPage", currentPage);
        List<Probe> probesKind = probeService.queryUsingGroupBy();
    //    System.out.println(probesKind);
        model.addAttribute("probeKind", probesKind);
        return "index/ProbeTest";
    }

    @RequestMapping("/probeListTest")
    public String ProbeListTest(Model model, @PathVariable(value = "current", required = false) Integer searchNum){

        if(searchNum == null) {
            searchNum = 0;
        }else{
            searchNum--;
        }

        Page<Probe> page = probeService.pageProbe(searchNum);
        probeService.addBorrowInfo(page.getContent());
        model.addAttribute("probeList", page.getContent());
        int[] pageNum = null;
        if(page.getTotalPages() > 0) {
            pageNum = new int[page.getTotalPages()];
            for(int i = 0; i < pageNum.length; i++){
                pageNum[i] = i + 1;
            }
        }
        int currentPage = page.getNumber() + 1;
        System.out.println("page.getNumber: " + page.getNumber());

        model.addAttribute("probeNum", pageNum);
        model.addAttribute("currentPage", currentPage);
        return "index/ProbeTest";
    }
    //searchProbe
    @RequestMapping(value = "/searchProbe")
    public String searchProbe(@RequestParam(name="num") String deviceCode, Model model){
        System.out.println("post data from serach:" + deviceCode);
        List<Probe> probes = probeService.findProbeByLabel(deviceCode.trim());
        if(probes == null || probes.size() == 0){
            probes = probeService.findProbeBySN(deviceCode.trim());
        }

        model.addAttribute("probeList", probes);
        model.addAttribute("probeNum", 1);
        model.addAttribute("currentPage", 1);


        return "index/ProbeTest";
    }
    @RequestMapping("/searchGroup")
    public String probesByGroup(@RequestParam String code, Model model){
        System.out.println("code = " + code);
        List<Probe> probes = null;
        if(!StringUtils.isEmpty(code)) {
            probes = probeService.findByNameLike(code);
        }

        model.addAttribute("probeList", probes);
        List<Probe> probesKind = probeService.queryUsingGroupBy();
        model.addAttribute("probeKind", probesKind);

        model.addAttribute("probeNum", 1);
        model.addAttribute("currentPage", 1);

        return "index/ProbeTest";

    }

    @RequestMapping(value = "/add/probe", method = RequestMethod.POST)
    public ResponseEntity addPobe(@RequestParam String name, @RequestParam String sn, @RequestParam String label, @RequestParam String barcode){
        Probe probe = new Probe();
        Probe probeSameLabel = probeService.findByLabel(label);
        System.out.println("probeSamelabel " + probeSameLabel);
        if(probeSameLabel != null){
            return new ResponseEntity(2, HttpStatus.OK);
        }
        probe.setName(name);
        probe.setLabel(label);
        probe.setSn(sn);
        probe.setBarCode(barcode);
        probe.setStatus("Storage");
        probe.setBorrowedNow(false);
        probeService.save(probe);
        System.out.println("add probe" + probe);
        return new ResponseEntity(1, HttpStatus.OK);
    }
    @RequestMapping(value = "/delete/probe", method = RequestMethod.POST)
    public ResponseEntity deleteProbe(@RequestParam String labels){
        System.out.println("labels" + labels);
        String [] label = labels.trim().split("\\ ");
        System.out.println(Arrays.toString(label));
        if(probeService.deleteProbe(label) == label.length) {
            return new ResponseEntity(1, HttpStatus.OK);
        }else{
            return new ResponseEntity(0, HttpStatus.OK);
        }
    }


    @RequestMapping("/userBorrow")
    public String findNVidiaBorrow(Model model){
        sqlServerConn.getConnectionForBorrowList();
        return "index/login";
    }
    @RequestMapping("/borrowList")
    public String borrowList(Model model, HttpServletRequest request){
        List<DeviceBorrowList> deviceBorrowLists = new ArrayList<DeviceBorrowList>(); //deviceBorrowService.findAll();  //findByDeivceLabelSortDate("DV-CRP-0002"); // findAll()
        List<BorrowingProbe> borrowingProbes = borrowingService.findAll();
        logger.info("borrowingProbes size is {}", borrowingProbes.size());
        for(BorrowingProbe borrowingProbe : borrowingProbes){
            deviceBorrowLists.add(deviceBorrowService.findById(borrowingProbe.getBorrowListId()));
        }
        model.addAttribute("borrowerNum", new int[]{1});
        model.addAttribute("currentPage", 1);
        model.addAttribute("borrowLists", deviceBorrowLists);
        model.addAttribute("rq", new Date());
        return "index/borrowList2";

    }
}
