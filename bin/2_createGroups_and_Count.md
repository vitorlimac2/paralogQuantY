# Pre-processing data

## Create tuples of gene paralogy

We use [awk](https://en.wikipedia.org/wiki/AWK) to extract and filter the gene paralogy tuples of the haplotypes from the TriTrypDB.

**Input (see [README.md](https://github.com/vitorlimac2/paralogQuantY/blob/master/README.md) for more information about the input files):**

* TriTrypDB-28_TcruziCLBrenerEsmeraldo-likeGene.txt 
* TriTrypDB-28_TcruziCLBrenerGene.txt
* TriTrypDB-28_TcruziCLBrenerNon-Esmeraldo-likeGene.txt

**Output:**

* Tcruzi_CLBrener_Paralogy_TriTrypDB28: three-column file. Each column contains a gene, it paralogue and the paralogue function.

```
~$ cat TriTrypDB-28_TcruziCLBrenerEsmeraldo-likeGene.txt TriTrypDB-28_TcruziCLBrenerGene.txt TriTrypDB-28_TcruziCLBrenerNon-Esmeraldo-likeGene.txt | awk -v OFS="\t" '{if($0~/^Gene ID:/){isTable=0;gene_id = $3}else if($0~/^TABLE: Orthologs and Paralogs within TriTrypDB/){isTable=1;}else if(isTable==1){if($0!=""){split($0,my_arr, "\t");print gene_id,my_arr[1],my_arr[2],my_arr[3],my_arr[4]}else{isTable=0}}}' | grep -v "is syntenic" | awk -v FS="\t" -v OFS="\t" '$3=="Trypanosoma cruzi CL Brener Esmeraldo-like" || $3=="Trypanosoma cruzi CL Brener Non-Esmeraldo-like" || $3=="Trypanosoma cruzi strain CL Brener"{print $1, $2, $4}' > Tcruzi_CLBrener_Paralogy_TriTrypDB28
```
## Create paralogy groups; 

**Input:**

* [Tcruzi_CLBrener_Paralogy_TriTrypDB28](https://github.com/vitorlimac2/paralogQuantY/tree/master/metafiles);

**Output:**

* Tcruzi_CLBrener_Paralogy_TriTrypDB28_groups: File with the paralog group ids, function list and member genes list;

```
~$ java -jar paralogGroupQuant.jar --group -p Tcruzi_CLBrener_Paralogy_TriTrypDB28 > Tcruzi_CLBrener_Paralogy_TriTrypDB28_groups
```

## Replace the gene ids by group ids; sum up the weigthed counts;

**Input:**

* Tcruzi_CLBrener_Paralogy_TriTrypDB28_groups: File with the paralog group ids and list of group member genes;
* \*.filtered.gtf.counts.txt: GTF weighted counts created by gemtools;

**Output:**

* Lib1.filtered.gtf.counts.WithParG.txt: tab-separated GTF weighted count file with the paralog group (PG) ID, list of counted genes (comma-separated) and sum the counts of the same PG. Genes that have not been assigned to any group do not have their counts changed;
```
~$ java -jar paralogGroupQuant.jar --sum -g Tcruzi_CLBrener_Paralogy_TriTrypDB28_groups -c Lib1.filtered.gtf.counts.txt > Lib1.filtered.gtf.counts.WithParG.txt
```

