package com.premilinary;

import java.io.File;

public class Test {
    public static void main(String[] args){
        String dirPath="C:\\Users\\DearYou\\Desktop\\dataset\\tmp";
        File[] fileList=new File(dirPath).listFiles();
        if(fileList!=null)
            System.out.print("yes");
    }
}
