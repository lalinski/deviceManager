package com.lalin.test.site.blog.mix.one.service;

import com.lalin.test.site.blog.mix.one.entity.DeviceBorrowList;
import com.lalin.test.site.blog.mix.one.entity.Probe;
import com.lalin.test.site.blog.mix.one.repository.BorrowListRepository;
import com.lalin.test.site.blog.mix.one.repository.ProbeRepository;
import com.lalin.test.site.blog.mix.one.utils.ConstUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by frzhao on 2017/10/21.
 */
@Service
public class ProbeService {
    @Autowired
    private ProbeRepository probeRepository;
    @Autowired
    @Qualifier("deviceBorrowService")
    private DeviceBorrowService deviceBorrowService;
    public static int PageSize = ConstUtil.PAGE_SIZE;
    public List<Probe> getProbes(){
        List<Probe> probeList = probeRepository.findAll();
        return probeList;
    }

    public Page pageProbe(int pageNum){
        PageRequest pageable = new PageRequest(pageNum, PageSize);
        Page<Probe> page = probeRepository.findAll(pageable);
      //  Page<Probe> page = probeRepository.;
        return page;
    }
    public List<Probe> findProbeBySN(String sn){
        System.out.println("trimed sn is:" + sn);
        sn = "%" + sn + "%";
        List<Probe> probes = probeRepository.findBySnLike(sn);
        if(probes != null && probes.size() > 0) {
            System.out.println("probe search by SN" + probes.get(0).toString());
        }
        return probes;
    }
    public Probe findBySn(String sn){
        Probe probe = null;
        if(!StringUtils.isEmpty(sn)){
            probe  = probeRepository.findBySn(sn);
            System.out.println("the delete probe is :" + probe);
        }
        return probe;
    }
    public List<Probe> findProbeByLabel(String label){
        System.out.println("trimed sn label:" + label);
        label = "%" + label + "%";
        List<Probe> probes = probeRepository.findByLabelLike(label);
        if(probes != null && probes.size() > 0) {
            System.out.println("probe search by Label" + probes.get(0).toString());
        }
        return probes;
    }
    public Probe findByLabel(String label){
        Probe probe = null;
        if(!StringUtils.isEmpty(label)){
            probe  = probeRepository.findByLabel(label);
            System.out.println("the choosed probe is :" + probe);
        }
        return probe;
    }
    public void addBorrowInfo(List<Probe> probes){
        if(probes != null && probes.size() > 0) {
            for (Probe probe : probes) {
                if (!StringUtils.isEmpty(probe.getLabel())) {
                    List<DeviceBorrowList> probeLabels = deviceBorrowService.findByDeivceLabelSortDate(probe.getLabel());
                    if(probeLabels.size() > 0) {
                        DeviceBorrowList firstOne = probeLabels.get(0);
                        if (!StringUtils.isEmpty(firstOne.getBorrower())) {
                            probe.setBorrower(firstOne.getBorrower());
                        }
                        if (firstOne.getStartTime() != null) {
                            probe.setStartTime(firstOne.getStartTime());
                        }
                        probe.setEndTime(firstOne.getReturnTime());

                    }
                }
            }
        }
    }
    public List<Probe> queryUsingGroupBy(){
        return probeRepository.queryUsingGroupBy();
    }
    public List<Probe> findByNameLike(String name){
        if(name.startsWith("TCA") || name.startsWith("TPA") || name.startsWith("PP007") || name.startsWith("BNC")) {
            name = '%' + name.trim() + '%';
        }else{
            name = name.replace('-', ' ');
            name = '%' + name.trim() + '%';
        }
        List<Probe> probes = probeRepository.findByNameLike(name);
        addBorrowInfo(probes);
        return probes;
    }
    @Transactional
    public int updateReturnTimeByLabel(Date date, String label){
        int i = probeRepository.updateReturnTimeByLabel(date, label);
        return i;
    }
    @Transactional
    public int updateBorrowProbe(Probe probe){
        return probeRepository.updateBorrowProbe(probe.getBorrower(), probe.getStartTime(), probe.getId());
    }
    public void save(Probe probe){
       probeRepository.save(probe);
    }
    @Transactional
    public int deleteProbe(String [] labels){
        int i = 0;
        for(String label : labels){
            i = i + probeRepository.deleteProbe(label);
        }
        return i;
    }

}
