package com.lalin.test.site.blog.mix.one;

import groovy.transform.ASTTest;
import org.junit.Test;

/**
 * Created by frzhao on 2017/11/15.
 */
public class testReplace {

    @Test
    public void replace(){
        String a = "Agilent-1169A-12GHz";
        String b = a.replace('-', ' ');
        System.out.println(a);
        System.out.println(b);
    }
}
