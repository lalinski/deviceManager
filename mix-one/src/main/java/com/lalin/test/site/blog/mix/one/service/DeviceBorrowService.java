package com.lalin.test.site.blog.mix.one.service;

import com.lalin.test.site.blog.mix.one.entity.BorrowingProbe;
import com.lalin.test.site.blog.mix.one.entity.DeviceBorrowList;
import com.lalin.test.site.blog.mix.one.entity.Employee;
import com.lalin.test.site.blog.mix.one.entity.Probe;
import com.lalin.test.site.blog.mix.one.repository.BorrowListRepository;
import com.lalin.test.site.blog.mix.one.utils.EmailEmbeddThymeleaf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by frzhao on 2017/10/30.
 */
@Service
public class DeviceBorrowService {

    @Autowired
    private BorrowListRepository borrowListRepository;
    @Autowired
    private ProbeService probeService;
    @Autowired
    private BorrowingService borrowingService;
    @Autowired
    private EmailEmbeddThymeleaf emailEmbeddThymeleaf;
    public List<DeviceBorrowList> findAll(){
        return borrowListRepository.findAll();
    }
    public List<DeviceBorrowList> findByDeivcelabel(String label){
        return borrowListRepository.findByDeviceLabel(label);
    }
    public List<DeviceBorrowList> findByDeivceLabelSortDate(String label){           // findOneByLastname
      //  return borrowListRepository.findByDeviceLabelOrderByStartTimeDesc(label);  // List<User> findTop10ByLastname(String lastname, Pageable pageable);
      //  return borrowListRepository.findOneByDeviceLabelOrderByStartTimeDesc(label);
        return borrowListRepository.findTop1ByDeviceLabelOrderByStartTimeDesc(label);
    }
    @Transactional
    public int updateReturnBorrowList(String label){
     //   System.out.println("return ID " + returnId(label));
        borrowingService.deleteOne(returnId(label));
        return borrowListRepository.updateReturnBorrowList(label);
    }
    public int returnId(String label){
        return borrowListRepository.returnId(label);
    }

    public int borrowProbe(Employee employee, Probe probe) throws MessagingException {
        DeviceBorrowList deviceBorrowOne = new DeviceBorrowList();
    //    deviceBorrowOne.setDeviceType("Probe");
        deviceBorrowOne.setDeviceName(probe.getName());
        deviceBorrowOne.setDeviceLabel(probe.getLabel());
        deviceBorrowOne.setDeviceId(probe.getId().toString());
        deviceBorrowOne.setLender("Joe Zhao");
        deviceBorrowOne.setBorrower(employee.getDisplayName());
        deviceBorrowOne.setStartTime(new Date());
        deviceBorrowOne.setStatus("loan");
        int saveId = borrowListRepository.save(deviceBorrowOne).getId();
        BorrowingProbe borrowingProbe = new BorrowingProbe();
        borrowingProbe.setBorrowListId(saveId);
        borrowingService.save(borrowingProbe);
     //   System.out.println("save " + borrowListRepository.save(deviceBorrowOne));
        emailEmbeddThymeleaf.sendBorrowingInfo(deviceBorrowOne);
        probe.setStatus("loan");
        probe.setBorrowedNow(true);
        probe.setBorrower(employee.getDisplayName());
        probe.setStartTime(new Date());
        probe.setEndTime(null);
        probeService.updateBorrowProbe(probe);
        return 1;
    }
    public DeviceBorrowList findById(Integer id){
        return borrowListRepository.findOne(id);
    }
}
