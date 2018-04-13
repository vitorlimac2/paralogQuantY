# Project Title

Description.

## Table of Contents

 * [Getting Started](#getting-started)
    * [Genome and transcriptome information](#genome-and-transcriptome-information)
       * [Genome Assembly](#genome-assembly)
       * [Transcriptome annotation](#transcriptome-annotation)
       * [Paralogy information](#paralogy-information)
    * [Mapping and quantification](#mapping-and-quantification)
    * [Visualization, normalization and differential gene expression](#visualization-normalization-and-differential-gene-expression)
 * [Authors](#authors)
 * [References](#references)
 * [Links](#links)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Genome and transcriptome information

Genome and transcriptome data can be downloaded from [TriTrypDB v28](http://tritrypdb.org/common/downloads/release-28/). For our analysis, we perfomed several pre-processings in these data files, as described later.

#### Genome Assembly

* [T. cruzi strain CL Brener \<FASTA\>](http://tritrypdb.org/common/downloads/release-28/TcruziCLBrener/fasta/data/TriTrypDB-28_TcruziCLBrener_Genome.fasta) (TriTrypDB 28);
* [T. cruzi strain CL Brener Esmeraldo-like \<FASTA\>](http://tritrypdb.org/common/downloads/release-28/TcruziCLBrenerEsmeraldo-like/fasta/data/TriTrypDB-28_TcruziCLBrenerEsmeraldo-like_Genome.fasta) (TriTrypDB 28);
* [T. cruzi strain CL Brener Non-Esmeraldo-like \<FASTA\>](http://tritrypdb.org/common/downloads/release-28/TcruziCLBrenerNon-Esmeraldo-like/fasta/data/TriTrypDB-28_TcruziCLBrenerNon-Esmeraldo-like_Genome.fasta) (TriTrypDB 28);

#### Transcriptome annotation

* [T. cruzi strain CL Brener \<GFF\>](http://tritrypdb.org/common/downloads/release-28/TcruziCLBrener/gff/data/TriTrypDB-28_TcruziCLBrener.gff) (TriTrypDB 28);
* [T. cruzi strain CL Brener Esmeraldo-like \<GFF\>](http://tritrypdb.org/common/downloads/release-28/TcruziCLBrenerEsmeraldo-like/gff/data/TriTrypDB-28_TcruziCLBrenerEsmeraldo-like.gff) (TriTrypDB 28);
* [T. cruzi strain CL Brener Non-Esmeraldo-like \<GFF\>](http://tritrypdb.org/common/downloads/release-28/TcruziCLBrenerNon-Esmeraldo-like/gff/data/TriTrypDB-28_TcruziCLBrenerNon-Esmeraldo-like.gff) (TriTrypDB 28);

#### Paralogy information

TriTrypDB (Aslett et al, 2010) provides plain text files containing orthology information of genes for each haplotype of T. cruzi CL Brener. Each gene is paired with it respective orthologous predicted by OrthoMCL (Chen et al, 2006). 
* [T. cruzi strain CL Brener \<TXT\>](http://tritrypdb.org/common/downloads/release-28/TcruziCLBrener/txt/TriTrypDB-28_TcruziCLBrenerGene.txt) (TriTrypDB 28)
* [T. cruzi strain CL Brener Esmeraldo-like \<TXT\>](http://tritrypdb.org/common/downloads/release-28/TcruziCLBrenerEsmeraldo-like/txt/TriTrypDB-28_TcruziCLBrenerEsmeraldo-likeGene.txt) (TriTrypDB 28)
* [T. cruzi strain CL Brener Non-Esmeraldo-like \<TXT\>](http://tritrypdb.org/common/downloads/release-28/TcruziCLBrenerNon-Esmeraldo-like/txt/TriTrypDB-28_TcruziCLBrenerNon-Esmeraldo-likeGene.txt) (TriTrypDB 28)

### Mapping and quantification

Mapping and quantification were performed by the pipeline of [GEMTools library](http://gemtools.github.io/). GEMTools is a wrapper for the GEM-mapper (Santiago et al, 2012) that simplifies the manipulation of the mapper functionalities. Example of the command-line for gemtools RNA pipeline:
```
gemtools rna-pipeline --stats-json -i genome.gem -a transcriptome.gtf -f Reads_R1.fq Reads_R2_.fq -t 20 -q 33 --compress-all --no-bam
```
This command-line performs:
* Mapping to genome and transcriptome indexes
* Filter mappings
* Assign reads to the transcriptome and create GTF counts

For more details, scripts and command-lines, see [bin](https://github.com/vitorlimac2/paralogQuantY/tree/master/bin) folder.

### Visualization, normalization and differential gene expression

Differential expression analysis was performed similarly to the literature (Anders et al, 2013) using the R/Bioconductor package DESeq2 (Love et al, 2014). Genes/OG with < 1 count were filtered out of the raw count table. Next, fractional counts were rounded by the R function “round” since DESeq2 handles only integer counts. Genes/groups were considered differentially expressed when they had a log2 fold-change greater or equals to ±1.5 and False Discovery Rate (FDR) was less or equals to 0.05.

For more details, scripts and command-lines, see [bin](https://github.com/vitorlimac2/paralogQuantY/tree/master/bin) folder.

## Authors

* **Vitor Lima Coelho**, **Michael Sammeth**, and **Luciana Loureiro Penha**

## References

* Anders, S. et al. Count-based differential expression analysis of RNA sequencing data using R and Bioconductor. Nature Protocols 8, 1765–1786 (2013).
* Aslett, M. et al. TriTrypDB: a functional genomic resource for the Trypanosomatidae. Nucleic Acids Research 2010 38(Database issue):D457-D462;
* Chen, F., Mackey, A. J., Stoeckert, C. J. Jr., Roos D. S. OrthoMCL-DB: querying a comprehensive multi-species collection of ortholog groups. Nucleic Acids Res. 2006 Jan 1;34(Database issue):D363-8.
* Love, M. I., Huber, W. and Anders, S. Moderated estimation of fold change and dispersion for RNA-seq data with DESeq2. Genome Biology, 15, pp. 550 (2014).
* Santiago, M. S., Sammeth, M., Guigó, R., Ribeca, P. The GEM mapper: fast, accurate and versatile alignment by filtration. Nature Methods 9, 1185–1188 (2012)

## Links


* [BitFun Lab](https://bitfun.org)
