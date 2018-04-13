# Preprocessing the raw sequencing data

## Trimming and filtering

We used FastQC (Andrews, 2010) to check the raw read quality, GC content, duplication levels, overrepresented sequences and Illumina adaptor presence. When it is necessary, Trim Galore! (Krueger, 2012) removes the adaptors and bases with low quality from 3’ end of reads.

Command-line example:

**Input**
* Illumina read sequence files: Epi-Y-1_R1.fastq and Epi-Y-1_R2.fastq

**Output**
* Trimmed/filtered files: Epi-Y-1_R1_val_1.fq and Epi-Y-1_R2_val_2.fq

```
~$ trim_galore --path_to_cutadapt ~/.local/bin/cutadapt --paired --max_n 5 --stringency 12 -q 20 -e 0.1 --length 30 Epi-Y-1_R1.fastq Epi-Y-1_R2.fastq
```
Options (description from the help tool)
* --paired: This option performs length trimming of quality/adapter/RRBS trimmed reads for paired-end files. To pass the validation test, both sequences of a sequence pair are required to have a certain minimum length which is governed by the option --length (see above). If only one read passes this length threshold the other read can be rescued (see option --retain_unpaired). Using this option lets you discard too short read pairs without disturbing the sequence-by-sequence order of FastQ files which is required by many aligners.

* --max_n 5: The total number of Ns (as integer) a read may contain before it will be removed altogether. In a paired-end setting, either read exceeding this limit will result in the entire pair being removed from the trimmed output files.

* --stringency 12: Overlap with adapter sequence required to trim a sequence. Defaults to a very stringent setting of 1, i.e. even a single bp of overlapping sequence will be trimmed off from the 3' end of any read.

* -q 20: Trim low-quality ends from reads in addition to adapter removal. For RRBS samples, quality trimming will be performed first, and adapter trimming is carried in a second round. Other files are quality and adapter trimmed in a single pass. The algorithm is the same as the one used by BWA (Subtract INT from all qualities; compute partial sums from all indices to the end of the sequence; cut sequence at the index at which the sum is minimal). Default Phred score: 20.

* -e 0.1: Maximum allowed error rate (no. of errors divided by the length of the matching region) (default: 0.1)

* length 30: Discard reads that became shorter than length 30 because of either quality or adapter trimming. A value of '0' effectively disables this behaviour. Default: 20 bp. For paired-end files, both reads of a read-pair need to be longer than               <INT> bp to be printed out to validated paired-end files (see option --paired). If only one read became too short there is the possibility of keeping such unpaired single-end reads (see --retain_unpaired).
