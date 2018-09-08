# Pre-processing data

## Create tuples of gene paralogy

We use [awk](https://en.wikipedia.org/wiki/AWK) to extract and filter the gene paralogy tuples of the haplotypes from the TriTrypDB.

**Input (see [README.md](https://github.com/vitorlimac2/paralogQuantY/blob/master/README.md) for more information about the input files):**

* TriTrypDB-28_TcruziCLBrenerEsmeraldo-likeGene.txt 
* TriTrypDB-28_TcruziCLBrenerGene.txt
* TriTrypDB-28_TcruziCLBrenerNon-Esmeraldo-likeGene.txt

**Output:**

* Tcruzi_CLBrener_Paralogy_TriTrypDB28: three-column file. Each column contains a gene, it paralogue and the paralogue function.

**Command-line:**
```
~$ cat TriTrypDB-28_TcruziCLBrenerEsmeraldo-likeGene.txt TriTrypDB-28_TcruziCLBrenerGene.txt TriTrypDB-28_TcruziCLBrenerNon-Esmeraldo-likeGene.txt | awk -v OFS="\t" '{if($0~/^Gene ID:/){isTable=0;gene_id = $3}else if($0~/^TABLE: Orthologs and Paralogs within TriTrypDB/){isTable=1;}else if(isTable==1){if($0!=""){split($0,my_arr, "\t");print gene_id,my_arr[1],my_arr[2],my_arr[3],my_arr[4]}else{isTable=0}}}' | grep -v "is syntenic" | awk -v FS="\t" -v OFS="\t" '$3=="Trypanosoma cruzi CL Brener Esmeraldo-like" || $3=="Trypanosoma cruzi CL Brener Non-Esmeraldo-like" || $3=="Trypanosoma cruzi strain CL Brener"{print $1, $2, $4}' > Tcruzi_CLBrener_Paralogy_TriTrypDB28
```
## Create paralogy groups; 

**Input:**

* [Tcruzi_CLBrener_Paralogy_TriTrypDB28](https://github.com/vitorlimac2/paralogQuantY/tree/master/metafiles);
### Input Example:
```
TcCLB.397937.10	TcCLB.508625.160	Trypanosoma cruzi CL Brener Non-Esmeraldo-like	pumilio/PUF RNA binding protein 1, putative (PUF1)	yes
TcCLB.398343.9	TcCLB.503479.60	Trypanosoma cruzi CL Brener Esmeraldo-like	surface protease GP63, putative	no
TcCLB.398343.9	TcCLB.503783.50	Trypanosoma cruzi CL Brener Esmeraldo-like	surface protease GP63 (pseudogene), putative	no
TcCLB.398343.9	TcCLB.504039.250	Trypanosoma cruzi CL Brener Esmeraldo-like	surface protease GP63 (pseudogene), putative	no
...
```

**Output:**

* Tcruzi_CLBrener_Paralogy_TriTrypDB28_groups: File with the paralog group ids, function list and member genes list;
### Output Example:
```
gPar.1	TcCLB.397937.10|TcCLB.508625.160	pumilio/PUF RNA binding protein 1, putative (PUF1)
gPar.2	TcCLB.398343.9|TcCLB.503479.60|TcCLB.503783.50|TcCLB.504039.250|...	surface protease GP63, putative|surface protease GP63 (pseudogene), putative|surface protease GP63, putative (fragment)

```
**Command-line:**
```
~$ java -jar paralogGroupQuant.jar -group -p Tcruzi_CLBrener_Paralogy_TriTrypDB28 > Tcruzi_CLBrener_Paralogy_TriTrypDB28_groups
```

## Replace the gene ids by group ids; sum up the weigthed counts;

**Input:**

* Tcruzi_CLBrener_Paralogy_TriTrypDB28_groups: File with the paralog group ids and list of group member genes;
* all_counts_joined.txt: replicate counts.

**Output:**

* group_count.txt: tab-separated GTF weighted count file with the paralog group (PG) ID, list of counted genes (pipe-separated) and sum the counts of the same PG. Genes that have not been assigned to any group do not have their counts changed;

**Command-line**
```
~$ java -jar paralogGroupQuant.jar -sum -g ../../paralogy/Tcruzi_CLBrener_Paralogy_TriTrypDB28_groups -c all_counts_joined.txt | sort -k1,1 > group_count.txt
```
***Output (group_count.txt):***
```
...
gPar.1610       TcCLB.508601.30|TcCLB.511801.44 637.0   333.0   725.0   524.0   830.0   989.0
gPar.1611       TcCLB.508413.50|TcCLB.510755.120        2569.0  1023.0  2894.0  2702.0  4056.0  4806.0
gPar.1612       TcCLB.419833.10|TcCLB.503727.29|TcCLB.506401.70|TcCLB.509603.10 1043.0  331.0   1760.0  1350.0  2347.0  2538.0
...
TcCLB.503457.4  TcCLB.503457.4  338.5   222.0   264.167 158.5   293.167 394.333
TcCLB.503459.29 TcCLB.503459.29 87.0    48.0    65.0    47.0    87.0    127.0
TcCLB.503461.40 TcCLB.503461.40 1.0     0.0     0.0     0.0     1.0     1.0
...
```
