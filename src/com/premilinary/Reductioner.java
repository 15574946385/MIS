package com.premilinary;

import java.util.*;

public class Reductioner {
    private Graph graph;
    private Vertex[] head;

    public Reductioner(Graph graph){
        this.graph=graph;
        this.head=graph.getHead();
    }

    //删除一度结点
    public void DegOneReduction(){
        for(int i=0;i<graph.getVertexNum();i++){
            Set<Integer> set=head[i].adjacent;
            if(set==null) continue;
            //找到一度结点
            Stack<Integer> stack=new Stack<>();
            if(set.size()==1){
                stack.push(i);
                while(!stack.isEmpty()){
                    int tem=stack.pop();
                    set=head[tem].adjacent;
                    if(set==null) continue;
                    for(Integer NeiOfOne:set){
                        //一度点邻居删除，并且要删除掉所有和该邻居相邻的边
                        Set<Integer> tmp= head[NeiOfOne].adjacent;
                        for(Integer NeisOfNei : tmp){
                            head[NeisOfNei].adjacent.remove(NeiOfOne);
                            if(head[NeisOfNei].adjacent.size()==1) stack.push(NeisOfNei);
                        }
                        head[NeiOfOne].adjacent=null;
                    }
                    //该一度点删除
                    head[tem].adjacent=null;
                }
            }
        }
    }

    @Deprecated
    public int sameNeiborEstimate(){
        int sum=0;
        HashMap<Integer,Integer> map=new HashMap<>();
        for(int i=0;i<graph.getVertexNum();i++){
            Set<Integer> set=head[i].adjacent;
            if(set!=null){
                int tmp=-1000000000;
                for(Integer value:set){
                    tmp+=value;
                }
                if(map.get(tmp)!=null) sum++;
                else map.put(tmp,i);
            }
        }
        return sum;
    }

    //寻找邻居完全相同
    @Deprecated
    public Set<Integer> seekToCombine(){
        Set<Integer> res=new TreeSet<>();
        for(int i=0;i<graph.getVertexNum();i++){
            Set<Integer> setI=head[i].adjacent;
            if(setI==null) continue;
            loop:for(int j=i+1;j<graph.getVertexNum();j++){
                Set<Integer> setJ=head[j].adjacent;
                if(setJ==null || setI.size()!=setJ.size())  continue;
                Iterator<Integer> iter1 = setI.iterator();
                Iterator<Integer> iter2 = setJ.iterator();
                while (iter1.hasNext() && iter2.hasNext()) {
                    if(iter1.next()!=iter2.next()) continue loop;
                }
                res.add(i);
                res.add(j);
            }
        }
        return res;
    }
}
