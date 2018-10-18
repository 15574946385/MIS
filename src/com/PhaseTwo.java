package com;

import com.premilinary.Graph;
import com.premilinary.Vertex;
import com.reduction.Reducer;

import java.io.*;
import java.util.*;

/**
 *  gujiewei
 *  2018/10/10
 *   experiment subject: 点带权无向图。
 **/
public class PhaseTwo {
    public static void main(String[] args){
        //vertexNum---图最大点编号  vertexSum----图实际点个数

        int vertexNum=1200000;
        String dirPath="C:\\Users\\DearYou\\Desktop\\dataset\\tmp";
        File[] fileList=new File(dirPath).listFiles();

        for(int k=0;k<fileList.length;k++){
            String fileName=fileList[k].getName();
            System.out.println(fileName);
            String tag;
            if(fileName.endsWith(".csv")) tag=",";
            else tag="\\s+";
            Graph graph = new Graph(vertexNum);
            Vertex[] head=graph.getHead();
            int vertexSum=0;
            try(BufferedReader br=new BufferedReader(new FileReader(fileList[k].getAbsolutePath()))){
                String line;
                //读取所有边  并且为每个新出现的点附一个新的权值。
                while((line=br.readLine())!=null){
                    String[] content=line.split(tag);
                    int from=Integer.parseInt(content[0]);
                    int to=Integer.parseInt(content[1]);
                    graph.setEdge(from,to);

                    //对图中点进行随机赋值
                    Random random=new Random();
                    if(head[from].weight==0){
                        head[from].weight= Integer.parseInt(content[2]);
//                        head[from].weight= random.nextInt(100)+1;
                        vertexSum++;
                    }
                    if(head[to].weight==0) {
                        head[to].weight= Integer.parseInt(content[3]);
//                        head[to].weight= random.nextInt(100)+1;
                        vertexSum++;
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("一共"+vertexSum+"个结点");
            //
            Reducer reducer=new Reducer(graph);
            reducer.setVertexSum(vertexSum,vertexNum);

            //singleVertex 邻居无边的迭代删除
            System.out.println("对于该点权 > 邻居点权和  的迭代删除开始");
            int count1=reducer.singleVerNoEdge();
            System.out.println("此次迭代一共删除了"+count1+"个结点");

            System.out.println("\n对于邻居存在一条边的情况的迭代删除开始");
            //singleVertex 邻居内一条边迭代删除
            int count2=reducer.singleVerOneEdge();
            System.out.println("此次迭代一共删除了"+count2+"个结点");



            //寻找邻居完全相同的子图
//            HashMap<String,ArrayList<Integer>> map=new HashMap<>();
//            for(int i=0;i<vertexNum;i++){
//                Set<Integer> set=head[i].adjacent;
//                if(set!=null){
//                    String tmp=set.toString();
//                    if(!map.containsKey(tmp)){
//                        map.put(tmp,new ArrayList<Integer>());
//                    }
//                    map.get(tmp).add(i);
//                }
//            }
//
//            int sameNeiClass=0;
//            Iterator<Map.Entry<String,ArrayList<Integer>>> iter=map.entrySet().iterator();
//            while(iter.hasNext()){
//                Map.Entry<String, ArrayList<Integer>> e = iter.next();
//                if(e.getValue().size()>1){
//                    sameNeiClass++;
//                }
//            }
//            System.out.println(sameNeiClass);
//            System.out.println();
        }
    }
}
