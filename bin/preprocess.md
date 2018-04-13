# Pre-processing data

## Create tuples of gene paralogy

```
~$ cat TriTrypDB-28_TcruziCLBrenerEsmeraldo-likeGene.txt TriTrypDB-28_TcruziCLBrenerGene.txt TriTrypDB-28_TcruziCLBrenerNon-Esmeraldo-likeGene.txt | awk -v OFS="\t" '{if($0~/^Gene ID:/){isTable=0;gene_id = $3}else if($0~/^TABLE: Orthologs and Paralogs within TriTrypDB/){isTable=1;}else if(isTable==1){if($0!=""){split($0,my_arr, "\t");print gene_id,my_arr[1],my_arr[2],my_arr[3],my_arr[4]}else{isTable=0}}}' | grep -v "is syntenic" | awk -v FS="\t" -v OFS="\t" '$3=="Trypanosoma cruzi CL Brener Esmeraldo-like" || $3=="Trypanosoma cruzi CL Brener Non-Esmeraldo-like" || $3=="Trypanosoma cruzi strain CL Brener"{print $1, $2}'
```
