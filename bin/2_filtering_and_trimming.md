# Preprocessing the raw sequencing data

## Trimming and filtering

```
~$ trim_galore --path_to_cutadapt ~/.local/bin/cutadapt --paired --max_n 5 --stringency 12 -q 20 -e 0.1 --length 30 Epi-Y-1_R1.fastq Epi-Y-1_R2.fastq

~$ trim_galore --path_to_cutadapt ~/.local/bin/cutadapt --paired --max_n 5 --stringency 12 -q 20 -e 0.1 --length 30 Epi-Y-2_R1.fastq Epi-Y-2_R2.fastq

~$ trim_galore --path_to_cutadapt ~/.local/bin/cutadapt --paired --max_n 5 --stringency 12 -q 20 -e 0.1 --length 30 Epi-Y-3_R1.fastq Epi-Y-3_R2.fastq

~$ trim_galore --path_to_cutadapt ~/.local/bin/cutadapt --paired --max_n 5 --stringency 12 -q 20 -e 0.1 --length 30 Trypo-Y-1_R1.fastq Trypo-Y-1_R2.fastq

~$ trim_galore --path_to_cutadapt ~/.local/bin/cutadapt --paired --max_n 5 --stringency 12 -q 20 -e 0.1 --length 30 Trypo-Y-2_R1.fastq Trypo-Y-2_R2.fastq

~$ trim_galore --path_to_cutadapt ~/.local/bin/cutadapt --paired --max_n 5 --stringency 12 -q 20 -e 0.1 --length 30 Trypo-Y-3_R1.fastq Trypo-Y-3_R2.fastq
```
