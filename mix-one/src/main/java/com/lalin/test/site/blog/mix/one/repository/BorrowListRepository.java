package com.lalin.test.site.blog.mix.one.repository;

import com.lalin.test.site.blog.mix.one.entity.DeviceBorrowList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;


/**
 * Created by frzhao on 2017/10/16.
 */
public interface BorrowListRepository extends JpaRepository<DeviceBorrowList, Integer> {

    public List<DeviceBorrowList> findByDeviceLabel(String label);
    public List<DeviceBorrowList> findByDeviceLabelOrderByStartTimeDesc(String label); //
    public List<DeviceBorrowList> findOneByDeviceLabelOrderByStartTimeDesc(String label); //  List<User> findOneByLastname(String lastname, Pageable pageable);
    public List<DeviceBorrowList> findTop1ByDeviceLabelOrderByStartTimeDesc(String label);  // findTop1ByDeviceLabelOrderByStartTimeDesc

    @Query(nativeQuery = true, value = "select id from device_borrow_list where device_label=?1 order by start_time desc limit 1")
    public int returnId(String label);
    @Modifying
    @Query(nativeQuery = true, value = "update device_borrow_list set return_time=now() where id = (select u.id from (select id from device_borrow_list where device_label=?1 order by start_time desc limit 1) u)")
    public int updateReturnBorrowList(String label);
    public DeviceBorrowList findById(Integer id);
}
