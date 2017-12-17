package com.lalin.test.site.blog.mix.one.repository;

import com.lalin.test.site.blog.mix.one.entity.BorrowingProbe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * Created by frzhao on 2017/12/13.
 */
public interface BorrowingRepository extends JpaRepository<BorrowingProbe, Integer> {
    @Modifying
    @Query("delete BorrowingProbe where borrowListId = ?1")
    public int deleteOne(int id);

}
