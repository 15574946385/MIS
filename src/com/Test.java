package com;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class Test {
    public static void main(String[] args){
        Set<Integer> set=new HashSet<>();
        Set<Integer> set1=new HashSet<>();
        set.add(1);
        set.add(2);
        set1.add(2);
        set1.add(3);
        set.addAll(set1);
        for (Integer value:set){
            System.out.print(value);
        }
    }
}
