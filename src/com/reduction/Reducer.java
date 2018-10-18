package com.reduction;

import com.premilinary.Graph;
import com.premilinary.Vertex;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author gujiewei
 * @create 2018/10/17
 * @desc
 **/
public class Reducer {
    private Graph graph;
    private Vertex[] head;
    private int vertexSum;
    private int vertexNum;

    public Reducer(Graph graph){
        this.graph=graph;
        head=graph.getHead();
    }

    public void setVertexSum(int vertexSum,int vertexNum){
        this.vertexSum=vertexSum;
        this.vertexNum=vertexNum;
    }

    //singleVertex 时，迭代删除该点权 > 邻居权之和；
    public int singleVerNoEdge(){
        int  index=1,delcount=0;
        while(true){
            int count=0;
            for (int i = 0; i <vertexNum; i++) {
                Set<Integer> set=head[i].adjacent;
                if(set!=null){
                    int sum=0;
                    for (Integer value: set){
                        sum+=head[value].weight;
                    }
                    if(sum<=head[i].weight) {
                        count++;
                        //删除这个点
//                        System.out.println(i);
                        graph.delVer(i);
                        //删除它的邻居
                        for (Integer value:set){
//                            System.out.print(value+" ");
                            graph.delVer(value);
                            count++;
                        }
//                        System.out.println();
                    }
                }else if(set==null && head[i].weight!=0){
                    count++;
                    graph.delVer(i);
                }
            }

            delcount+=count;
            if(count==0) break;
            System.out.println("第"+index+"轮删除"+count+"个结点。");
            index++;
        }
        return delcount;
    }

    //singleVertex 迭代邻居中一条边
    public int singleVerOneEdge(){
        int turn=0,delcount=0;
        while(true){
            int count=0;
            for(int i=0;i<vertexNum;i++){
                Set<Integer> set=head[i].adjacent;
                int weightSum=0;
                if(set!=null){
                    ArrayList<Integer> list=new ArrayList<>();
                    for(Integer nei:set){
                        Set<Integer> set1=head[nei].adjacent;

                        boolean flag=true;
                        for (Integer neiOfNei:set1) {
                            if(set.contains(neiOfNei)){
                                list.add(nei);
                                list.add(neiOfNei);
                                flag=false;
                            }
                        }
                        //如果该邻居的邻居没有和父点的邻居相邻，说明相对独立，则要计算在weightsum里面。
                        if(flag) weightSum+=head[nei].weight;
                    }

                    if(list.size()==2){
                        int weight1=head[list.get(0)].weight;
                        int weight2=head[list.get(1)].weight;
                        weightSum+=(weight1 > weight2 ? weight1:weight2);
                    }

                    //如果得到的最大权值 小于 该点的权值，则可以将这个点删除掉。
                    if(weightSum <= head[i].weight){
                        graph.delVer(i);
                        count++;
                        for(Integer value:set){
                            graph.delVer(value);
                            count++;
                        }
                    }
                }
            }
            delcount+=count;
            if(count==0)  break;
            System.out.println("第"+(++turn)+"轮删除了"+count+"个结点");
        }
        return delcount;
    }

    public int twoEdge(Graph graph,int edge){
        int weight=0;
        Vertex[] tmp=graph.getHead();
        for(int i=0;i<graph.getVertexNum();i++){
            if(tmp[i].adjacent==null){
                weight+=tmp[i].weight;
                tmp[i].weight=0;
            }
            else if(tmp[i].adjacent.size()==2){
                Set<Integer> set=tmp[i].adjacent;
                int sum=0;
                for(Integer value:set){
                    sum+=value;
                }
                weight=tmp[i].weight;
                if(sum>tmp[i].weight) weight=sum;
            }
//            else if(){
//
//            }
        }
        return weight;
    }

    public int oneEdge(Graph graph){
        int weight=0;
        Vertex[] tmp=graph.getHead();

        for(int i=0;i<graph.getVertexNum();i++){
            if(tmp[i].adjacent==null){
                weight+=tmp[i].weight;
                tmp[i].weight=0;
            }
            else if(tmp[i].adjacent.size()==1){
                Set<Integer> set=tmp[i].adjacent;
                Iterator<Integer> iter=set.iterator();
                int nei=iter.next();
                int weight1=tmp[i].weight;
                int weight2=tmp[nei].weight;
                weight=(weight1>weight2) ? weight1:weight2;
            }
        }
        return weight;
    }

    public int threeLinked(){
        int weight=0;
        return weight;
    }
}
