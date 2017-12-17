package com.lalin.test.site.blog.mix.one.controller;

import com.lalin.test.site.blog.mix.one.entity.BorrowingProbe;
import com.lalin.test.site.blog.mix.one.entity.DeviceBorrowList;
import com.lalin.test.site.blog.mix.one.entity.Employee;
import com.lalin.test.site.blog.mix.one.entity.Probe;
import com.lalin.test.site.blog.mix.one.service.BorrowingService;
import com.lalin.test.site.blog.mix.one.service.DeviceBorrowService;
import com.lalin.test.site.blog.mix.one.service.EmployeeService;
import com.lalin.test.site.blog.mix.one.service.ProbeService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import sun.security.util.Resources;


import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by frzhao on 2017/11/13.
 */
@Controller
@RequestMapping("/admin")
public class ProbeController {

    @Autowired
    ProbeService probeService;
    @Resource
    DeviceBorrowService deviceBorrowService;
    @Autowired
    EmployeeService employeeService;

    @Autowired
    private BorrowingService borrowingService;

    @RequestMapping(path={"/groupBy", "/groupBy/{current}"})
    public String filterByType(Model model, @PathVariable(value = "current", required = false) Integer searchNum){


        if(searchNum == null) {
            searchNum = 0;
        }else{
            searchNum--;
        }

        Page<Probe> page = probeService.pageProbe(searchNum);
        probeService.addBorrowInfo(page.getContent());

        int[] pageNum = null;
        if(page.getTotalPages() > 0) {
            pageNum = new int[page.getTotalPages()];
            for(int i = 0; i < pageNum.length; i++){
                pageNum[i] = i + 1;
            }
        }
        int currentPage = page.getNumber() + 1;
        List<Probe> probesKind = probeService.queryUsingGroupBy();

        model.addAttribute("probeList", page.getContent());
        model.addAttribute("probeKind", probesKind);
        System.out.println("page.getNumber: " + page.getNumber());

        model.addAttribute("probeNum", pageNum);
        model.addAttribute("currentPage", currentPage);

        return "index/ProbeClassify";
    }
    @RequestMapping("/searchGroup")
    @ResponseBody
    public ResponseEntity probesByGroup(@RequestParam String code, Model model){
        System.out.println("code = " + code);
        List<Probe> probes = null;
        if(!StringUtils.isEmpty(code)) {
            probes = probeService.findByNameLike(code);
        }

        return new ResponseEntity(probes, HttpStatus.OK);
    /*    model.addAttribute("probeList", probes);
        List<Probe> probesKind = probeService.queryUsingGroupBy();
        model.addAttribute("probeKind", probesKind);

        model.addAttribute("probeNum", 1);
        model.addAttribute("currentPage", 1);

        return "index/ProbeClassify";  */

    }
    @RequestMapping(value = "/return", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Integer> ProbeReturn(@RequestParam String num){
        System.out.println("the return label is " + num);
        Probe probe = probeService.findByLabel(num);
        probe.setEndTime(new Date());
        int probeUpdatInt = probeService.updateReturnTimeByLabel(new Date(), num);
        int borrowListUpdatInt = deviceBorrowService.updateReturnBorrowList(num);
        System.out.println("probeUpdatInt " + probeUpdatInt);
        System.out.println("borrowListUpdatInt " + borrowListUpdatInt);
        if(borrowListUpdatInt == 1){
            return new ResponseEntity<Integer>(1, HttpStatus.OK);
        }else{
            return new ResponseEntity<Integer>(0, HttpStatus.OK);
        }

    }
    @RequestMapping("/searchProbe")
    public String searchProbe(@RequestParam(name="num") String deviceCode, Model model){
        System.out.println("post data from serach:" + deviceCode);
        List<Probe> probes = probeService.findProbeByLabel(deviceCode.trim());
        if(probes == null || probes.size() == 0){
            probes = probeService.findProbeBySN(deviceCode.trim());
        }
        probeService.addBorrowInfo(probes);

        List<Probe> probesKind = probeService.queryUsingGroupBy();
        model.addAttribute("probeList", probes);
        model.addAttribute("probeNum", 1);
        model.addAttribute("probeKind", probesKind);
        model.addAttribute("currentPage", 1);
        return "index/ProbeClassify";

    }
    @RequestMapping(value = "/borrowProbe", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity borrowProbe(@RequestParam String borrowerName, @RequestParam(required = false) String borrowerEmail, @RequestParam("label") String labelName, HttpServletResponse response) throws MessagingException, IOException {
        System.out.println("borrow name: " + borrowerName);
        System.out.println("borrow email: " + borrowerEmail);
        System.out.println("label name: " + labelName);
        Employee employee = employeeService.existEmployee(borrowerName);
        if(employee != null) {
            response.getWriter().write("0");
        }
        System.out.println("employee : " + employee);
        Probe probe = probeService.findByLabel(labelName);
        System.out.println("probe : " + probe);
        if(employee != null) {
         //   response.getWriter().write("0");
         //   response.getWriter().flush();
            deviceBorrowService.borrowProbe(employee, probe);
        }

        return null;//new ResponseEntity(0, HttpStatus.OK);
    }
    @RequestMapping("/email")
    public String emialLook(Model model){
        List<DeviceBorrowList> deviceBorrowLists = new ArrayList<DeviceBorrowList>(); //deviceBorrowService.findAll();  //findByDeivceLabelSortDate("DV-CRP-0002"); // findAll()
        List<BorrowingProbe> borrowingProbes = borrowingService.findAll();
        for(BorrowingProbe borrowingProbe : borrowingProbes){
            deviceBorrowLists.add(deviceBorrowService.findById(borrowingProbe.getBorrowListId()));
        }
        model.addAttribute("borrowLists", deviceBorrowLists);
        return "index/borrowByPerson";
    }
}
