package org.bitfun;

import java.util.List;

public class FeatureCount {
    private String groupId;
    private List<String> geneList;
    private float[] counts;

    public FeatureCount(String groupId) {
        this.groupId = groupId;
    }

    public List<String> getGeneList() {
        return geneList;
    }

    public void setGeneList(List<String> geneList) {
        this.geneList = geneList;
    }

    public float[] getCounts() {
        return counts;
    }

    public void setCounts(float[] counts) {
        this.counts = counts;
    }

    public void updateCounts(int index, float value){

        this.counts[index] += value;

    }

    public void updateGeneList(String gene){
        this.geneList.add(gene);
    }

    public String getGeneListToString(){
        String output = "";
        if (geneList.size() ==1 )
            return geneList.get(0);
        for (String s: geneList){
            if(output.equals("")){
                output = s;
            }else{
                output = output + "|" + s;
            }
        }
        return output;
    }

    public String getCountListToString(){
        String output = "";
        if(counts.length == 1)
            return String.valueOf(counts[0]);
        for(float f : counts){
            if(output.equals(""))
                output = String.valueOf(f);
            else
                output = output + "\t" + String.valueOf(f);
        }
        return output;
    }
}
