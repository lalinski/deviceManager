package com.lalin.test.site.blog.mix.one.repository;

import com.lalin.test.site.blog.mix.one.entity.Employee;
import com.lalin.test.site.blog.mix.one.entity.Probe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by frzhao on 2017/4/22.
 */
public interface ProbeRepository extends JpaRepository<Probe, Integer> {

    public List<Probe> findByName(String name);
    public List<Probe> findByLabelLike(String label);
    public List<Probe> findBySnLike(String sn);
    public List<Probe> findByNameLike(String name);
    @Query("select o from Probe o Group By name")
    public List<Probe> queryUsingGroupBy();
    public Probe findBySn(String sn);
    public Probe findByLabel(String label);

    @Modifying
    @Query("update Probe o set endTime = ?1, Status='Storage' where label = ?2")
    public int updateReturnTimeByLabel(Date date, String label);

    @Modifying
    @Query("update Probe o set Status='loan', borrowedNow=true, borrower=?1, startTime=?2, endTime=null where id=?3")
    public int updateBorrowProbe(String borrower, Date startTime, int id);
    /*
        probe.setStatus("loan");
        probe.setBorrowedNow(true);
        probe.setBorrower(employee.getDisplayName());
        probe.setStartTime(new Date());
        probe.setEndTime(null);
     */
    @Modifying
    @Query("delete Probe where label = ?1")
    public int deleteProbe(String label);
}
