package com.reduction;

import com.graph.Graph;
import com.graph.Vertex;

import java.util.HashSet;
import java.util.Set;

/**
 * @author gujiewei
 * @create 2018/10/17
 * @desc
 **/
public class Reducer {
    private Graph graph;
    private Vertex[] head;
    private int vertexNum;

    public Reducer(Graph graph){
        this.graph=graph;
        head=graph.getHead();
    }

    public void setVertexSum(int vertexNum){
        this.vertexNum=vertexNum;
    }

    //singleVertex 时，迭代删除该点权 > 邻居权之和；
    public int singleVerHeavier(){
        int  turn=0,delcount=0;
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
            System.out.println("第"+(++turn)+"轮删除"+count+"个结点。");
        }
        return delcount;
    }

    //singleVertex 迭代删除邻居中一条边
    public int singleVerOneEdge(){
        int turn=0,delcount=0;
        while(true){
            int count=0;
            loop:for(int i=0;i<vertexNum;i++){
                Set<Integer> set=head[i].adjacent;
                int weightSum=0;
                if(set!=null){
//                    HashSet<Integer> list=new HashSet<>();
                    int edgeCount=0;
                    int[] list=new int[2];
                    for(Integer nei : set){
                        Set<Integer> set1=head[nei].adjacent;
                        boolean flag=true;
                        for (Integer neiOfNei:set1) {
                            if(set.contains(neiOfNei)){
                                if( nei > neiOfNei) {
                                    //大于一条边 就跳转到 下一个点
                                    if((++edgeCount)>1) continue loop;
                                    list[0]=nei;
                                    list[1]=neiOfNei;
                                }
                                flag=false;
                            }
                        }
                        //如果该邻居的邻居没有和父点的邻居相邻，说明相对独立，则要计算在weightsum里面。
                        if(flag) weightSum+=head[nei].weight;
                    }
                    weightSum+=oneEdge(list);
                    //如果得到的最大权值 小于 该点的权值，则可以将这个点删除掉。
                    count+=delVerAndNei(weightSum,i);
                }
                else if(set==null && head[i].weight!=0){
                    count++;
                    graph.delVer(i);
                }
            }
            delcount+=count;
            if(count==0)  break;
            System.out.println("第"+(++turn)+"轮删除了"+count+"个结点");
        }
        return delcount;
    }

    //singleVertex 迭代删除邻居中两条边的
    public int singleVerTwoEdge(){
        int turn=0,delcount=0;
        while(true){
            int count=0;
            loop:for (int i = 0; i < vertexNum; i++) {
                Set<Integer> set=head[i].adjacent;

                int weightSum=0;
                if(set!=null){
                    int edgeCount=0;   //这个变量用来对边计数
                    //遍历父点邻居
                    int[] list=new int[4];int index=0;
                    for (Integer nei: set){
                        Set<Integer> set1=head[nei].adjacent;
                        boolean flag=true;
                        for(Integer neiOfNei : set1){
                            //如果他的邻居也是父点的邻居
                            if(set.contains(neiOfNei)) {
                                if(nei > neiOfNei){
                                    if((++edgeCount)>2) continue loop;
                                    list[index++]=nei;                                    // 2 1 3 1
                                    list[index++]=neiOfNei;                                        // 2 1 4 3
                                }
                                flag=false;
                            }
                        }
                        if(flag) weightSum+=head[nei].weight;
                    }
                    if(edgeCount==2){
                        weightSum+=twoEdge(list);
                        count+=delVerAndNei(weightSum,i);
                    }
                }
                else if(set==null && head[i].weight!=0){
                    count++;
                    graph.delVer(i);
                }
            }
            delcount+=count;
            if(count==0) break;
           System.out.println("第"+(++turn)+"轮删除了"+count+"个结点");
        }
        return delcount;
    }

    public int oneEdge(int[] list){
        int weight1=head[list[0]].weight;
        int weight2=head[list[1]].weight;
        return  weight1 > weight2 ? weight1 : weight2;
    }

    public int twoEdge(int[] list){
        //四个数字找到重复的id，这个id就是2条边的中间结点
        HashSet<Integer> set=new HashSet<>();

        int weightSum=0;
        int flag=-1;
        for(int i=0;i<list.length;i++){
            weightSum+=head[list[i]].weight;
            if(set.contains(list[i])){
                flag=list[i];
            }
            set.add(list[i]);
        }
        if(flag!=-1){
            weightSum-= 2*head[flag].weight;
            return Math.max(weightSum,head[flag].weight);
        }
        return Math.max(head[list[0]].weight,head[list[1]].weight)+Math.max(head[list[2]].weight,head[list[3]].weight);
    }

    public int delVerAndNei(int weight,int id){
        int count=0;
        if(weight<=head[id].weight){
            Set<Integer> nei=head[id].adjacent;
            graph.delVer(id);
            count++;
            for(Integer value : nei){
                graph.delVer(value);
                count++;
            }
        }
        return count;
    }
}
