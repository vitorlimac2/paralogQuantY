# Preprocessing genome and transcriptome data for mapping and quantification

## Prepare genome assemblies
This step is required to remove meta-information from the FASTA headers and merge the assemblies. The contig/chromosome names are unique.

```
~$ cat TriTrypDB-28_TcruziCLBrener_Genome.fasta TriTrypDB-28_TcruziCLBrenerEsmeraldo-like_Genome.fasta TriTrypDB-28_TcruziCLBrenerNon-Esmeraldo-like_Genome.fasta | awk '{if($0~/^>/)$0=$1; print}' > genome.gtf
```
## Prepare transcriptome annotation

### Convert to GTF

```
~$ gffread TriTrypDB-28_TcruziCLBrener.gff -T -o TriTrypDB-28_TcruziCLBrener.gtf
~$ gffread TriTrypDB-28_TcruziCLBrenerEsmeraldo-like.gff -T -o TriTrypDB-28_TcruziCLBrenerEsmeraldo-like.gtf
~$ gffread TriTrypDB-28_TcruziCLBrenerNonEsmeraldo-like.gff -T -o TriTrypDB-28_TcruziCLBrenerNonEsmeraldo-like.gtf
~$ cat TriTrypDB-28_TcruziCLBrener.gtf TriTrypDB-28_TcruziCLBrenerEsmeraldo-like.gtf TriTrypDB-28_TcruziCLBrenerNonEsmeraldo-like.gtf > transcriptome.gtf

```
