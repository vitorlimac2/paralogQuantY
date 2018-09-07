package org.bitfun;

import java.util.List;

class FeatureCount {
    private String groupId;
    private List<String> geneList;
    private float[] counts;

    void setGeneList(List<String> geneList) {
        this.geneList = geneList;
    }

    void setCounts(float[] counts) {
        this.counts = counts;
    }

    void updateCounts(int index, float value){

        this.counts[index] += value;

    }

    void updateGeneList(String gene){
        this.geneList.add(gene);
    }

    String getGeneListToString(){
        StringBuilder output = new StringBuilder();
        if (geneList.size() ==1 )
            return geneList.get(0);
        for (String s: geneList){
            if(output.toString().equals("")){
                output = new StringBuilder(s);
            }else{
                output.append("|").append(s);
            }
        }
        return output.toString();
    }

    String getCountListToString(){
        StringBuilder output = new StringBuilder();
        if(counts.length == 1)
            return String.valueOf(counts[0]);
        for(float f : counts){
            if(output.toString().equals(""))
                output = new StringBuilder(String.valueOf(f));
            else
                output.append("\t").append(String.valueOf(f));
        }
        return output.toString();
    }

    void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
