# Mapping
```
~/git/STAR/bin/Linux_x86_64/STAR --runMode alignReads --seedSearchStartLmaxOverLread 0.5 --outSAMattributes NH --outSAMtype BAM SortedByCoordinate --winAnchorMultimapNmax 50 --outFilterMismatchNoverReadLmax 0.1 --limitOutSAMoneReadBytes 25000 --outSAMunmapped Within --outSAMprimaryFlag AllBestScore --outFilterMultimapNmax 50 --runThreadN 20 --genomeDir /storage/data/genomes/T.cruzi/tryCruBrener/triTrypDB-28_2014-09-16/star/ --readFilesIn Epi-Y-1_trimmed_R1.fq Epi-Y-1_trimmed_R2.fq --outFileNamePrefix Epi1 2>&1 | tee -a log_epi1

~/git/STAR/bin/Linux_x86_64/STAR --runMode alignReads --seedSearchStartLmaxOverLread 0.5 --outSAMattributes NH --outSAMtype BAM SortedByCoordinate --winAnchorMultimapNmax 50 --outFilterMismatchNoverReadLmax 0.1 --limitOutSAMoneReadBytes 25000 --outSAMunmapped Within --outSAMprimaryFlag AllBestScore --outFilterMultimapNmax 50 --runThreadN 20 --genomeDir /storage/data/genomes/T.cruzi/tryCruBrener/triTrypDB-28_2014-09-16/star/ --readFilesIn Epi-Y-2_trimmed_R1.fq Epi-Y-2_trimmed_R2.fq --outFileNamePrefix Epi2 2>&1 | tee -a log_epi2

~/git/STAR/bin/Linux_x86_64/STAR --runMode alignReads --seedSearchStartLmaxOverLread 0.5 --outSAMattributes NH --outSAMtype BAM SortedByCoordinate --winAnchorMultimapNmax 50 --outFilterMismatchNoverReadLmax 0.1 --limitOutSAMoneReadBytes 25000 --outSAMunmapped Within --outSAMprimaryFlag AllBestScore --outFilterMultimapNmax 50 --runThreadN 20 --genomeDir /storage/data/genomes/T.cruzi/tryCruBrener/triTrypDB-28_2014-09-16/star/ --readFilesIn Epi-Y-3_trimmed_R1.fq Epi-Y-3_trimmed_R2.fq --outFileNamePrefix Epi3 2>&1 | tee -a log_epi3

~/git/STAR/bin/Linux_x86_64/STAR --runMode alignReads --seedSearchStartLmaxOverLread 0.5 --outSAMattributes NH --outSAMtype BAM SortedByCoordinate --winAnchorMultimapNmax 50 --outFilterMismatchNoverReadLmax 0.1 --limitOutSAMoneReadBytes 25000 --outSAMunmapped Within --outSAMprimaryFlag AllBestScore --outFilterMultimapNmax 50 --runThreadN 20 --genomeDir /storage/data/genomes/T.cruzi/tryCruBrener/triTrypDB-28_2014-09-16/star/ --readFilesIn Trypo-Y-1_trimmed_R1.fq Trypo-Y-1_trimmed_R2.fq --outFileNamePrefix Trypo1 2>&1 | tee -a log_trypo1

~/git/STAR/bin/Linux_x86_64/STAR --runMode alignReads --seedSearchStartLmaxOverLread 0.5 --outSAMattributes NH --outSAMtype BAM SortedByCoordinate --winAnchorMultimapNmax 50 --outFilterMismatchNoverReadLmax 0.1 --limitOutSAMoneReadBytes 25000 --outSAMunmapped Within --outSAMprimaryFlag AllBestScore --outFilterMultimapNmax 50  --runThreadN 20 --genomeDir /storage/data/genomes/T.cruzi/tryCruBrener/triTrypDB-28_2014-09-16/star/ --readFilesIn Trypo-Y-2_trimmed_R1.fq Trypo-Y-2_trimmed_R2.fq --outFileNamePrefix Trypo2 2>&1 | tee -a log_trypo2

~/git/STAR/bin/Linux_x86_64/STAR --runMode alignReads --seedSearchStartLmaxOverLread 0.5 --outSAMattributes NH --outSAMtype BAM SortedByCoordinate --winAnchorMultimapNmax 50 --outFilterMismatchNoverReadLmax 0.1 --limitOutSAMoneReadBytes 25000 --outSAMunmapped Within --outSAMprimaryFlag AllBestScore --outFilterMultimapNmax 50 --runThreadN 20 --genomeDir /storage/data/genomes/T.cruzi/tryCruBrener/triTrypDB-28_2014-09-16/star/ --readFilesIn Trypo-Y-3_trimmed_R1.fq Trypo-Y-3_trimmed_R2.fq --outFileNamePrefix Trypo3 2>&1 | tee -a log_trypo3

```
# Quantification
```
featureCounts -B -p -O -M --fraction -R -T 30 -a /storage/data/genomes/T.cruzi/tryCruBrener/triTrypDB-28_2014-09-16/tcruzi_complete.gtf -o all.out Epi1Aligned.sortedByCoord.out.bam Epi2Aligned.sortedByCoord.out.bam Epi3Aligned.sortedByCoord.out.bam Trypo1Aligned.sortedByCoord.out.bam Trypo2Aligned.sortedByCoord.out.bam Trypo3Aligned.sortedByCoord.out.bam
```
# Post-processing feature assignment 
```
for file in *.out.bam.featureCounts; do cat $file | awk -v OFS="\t" -v COL_READ=1 -v COL_GENES=3 -v COL_STATUS=2 '{if($COL_STATUS=="Assigned"){if(gene_list[$COL_READ]=="NA" || gene_list[$COL_READ]==""){gene_list[$COL_READ]=$COL_GENES;}else{gene_list[$COL_READ]=gene_list[$COL_READ]","$COL_GENES;}}else{if(gene_list[$COL_READ]==""){gene_list[$COL_READ]="NA";}}} END {for(i in gene_list){n=split(gene_list[i],l,",");for(j in l){print i, l[j];}}}' | sort | uniq | sort -k1,1 > $file.Unique.Sorted; done

for file in *.Unique.Sorted; do cat $file | awk -v OFS="\t" '{reads_mm[$1]+=1; reads_id[NR]=$1;genes_id[NR]=$2} END {for(i in reads_id){if(genes_id[i]=="NA"){mm = 0}else{mm=reads_mm[reads_id[i]]} print reads_id[i],genes_id[i], mm}}' > $file.ReadAssignmentToGene; done

for file in *.Unique.Sorted.ReadAssignmentToGene; do cat $file | awk '$3!=0{gene_count[$2] = gene_count[$2]+1/$3} END {for(i in gene_count){print i, gene_count[i]}}' | sort -k1,1 > $file.NoNormalized.Frac; done
```

Next step is 
