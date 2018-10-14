package com.premilinary;

import java.util.Set;
import java.util.TreeSet;

public class Test {
    public static void main(String[] args){
        Set<Integer> set1=new TreeSet<>();
        Set<Integer> set2=new TreeSet<>();
        set1.add(2);
        set1.add(1);
        set2.add(2);
        set2.add(1);
        set2.add(3);
        if(set1.containsAll(set2))
            System.out.print("yes");
    }
}
