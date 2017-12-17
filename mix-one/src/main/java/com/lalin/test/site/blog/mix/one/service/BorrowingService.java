package com.lalin.test.site.blog.mix.one.service;

import com.lalin.test.site.blog.mix.one.entity.BorrowingProbe;
import com.lalin.test.site.blog.mix.one.repository.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.annotation.Target;
import java.util.List;

/**
 * Created by frzhao on 2017/12/13.
 */
@Service
public class BorrowingService {
    @Autowired
    BorrowingRepository borrowRepository;

    public void save(BorrowingProbe borrowingProbe){
        borrowRepository.save(borrowingProbe);
    }
    @Transactional
    public int deleteOne(int id){
        return borrowRepository.deleteOne(id);
    }
    public List<BorrowingProbe> findAll(){
        return borrowRepository.findAll();
    }
}
