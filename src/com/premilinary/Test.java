package com.premilinary;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args){
        Set<Integer> set=new HashSet<>();
        set.add(1);
        set.add(2);
        Set<Integer> set1=set;
        set=null;
        for (Integer value:set1){
            System.out.print(value);
        }
    }
}
