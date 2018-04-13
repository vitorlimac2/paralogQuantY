# Project Title

Description.

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


## Authors

* **Vitor Lima Coelho**, **Michael Sammeth**, and **Luciana Loureiro Penha**

## References

* Aslett, M. et al. TriTrypDB: a functional genomic resource for the Trypanosomatidae. Nucleic Acids Research 2010 38(Database issue):D457-D462;
* Chen, F., Mackey, A. J., Stoeckert, C. J. Jr., Roos D. S. OrthoMCL-DB: querying a comprehensive multi-species collection of ortholog groups. Nucleic Acids Res. 2006 Jan 1;34(Database issue):D363-8.
* Santiago, M. S., Sammeth, M., Guigó, R., Ribeca, P. The GEM mapper: fast, accurate and versatile alignment by filtration. Nature Methods 9, 1185–1188 (2012)

## Links


* [BitFun Lab](https://bitfun.org)
