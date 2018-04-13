0 - initial : Map to index

    mismatches : 0.06
    strata_after_best : 1
    quality_threshold : 26
    max_decoded_matches : 25
    max_big_indel_length : 15
    mismatch_alphabet : ACGT
    min_matched_bases : 0.8
    min_decoded_strata : 1
    max_edit_distance : 0.2

1 - annotation-mapping : Map to transcript-index

    mismatches : 0.06
    strata_after_best : 1
    quality_threshold : 26  
    max_big_indel_length : 15
    mismatch_alphabet : ACGT
    max_decoded_matches : 150
    min_matched_bases : 0.8
    min_decoded_strata : 1
    max_edit_distance : 0.2
    denovo : False

2 - denovo-index : Create denovo transcript index

    refinement_step_size : 2
    mismatches : 0.04
    strata_after_best : 0
    min_split_size : 15
    matches_threshold : 75
    junctions_consensus : [('GT', 'AG'), ('GC', 'AG'), ('ATATC', 'A.'), ('GTATC', 'AT')]
    filter : ordered,non-zero-distance
    max_split_length : 500000
    min_split_length : 4
    coverage : 2
    max_junction_matches : 5

3 - denovo-mapping : Map to denovo transcript-index

    trim : None
    mismatches : 0.06
    strata_after_best : 1
    quality_threshold : 26
    create_index : 2
    max_big_indel_length : 15
    mismatch_alphabet : ACGT
    max_decoded_matches : 150
    min_matched_bases : 0.8
    min_decoded_strata : 1
    max_edit_distance : 0.2
    denovo : True

4 - merge_and_pair : Merge and Pair alignments

    quality_threshold : 26
    max_extendable_matches : 0
    max_decoded_matches : 25
    min_insert_size : 0
    same_content : True
    min_matched_bases : 0.8
    max_insert_size : 500000
    min_decoded_strata : 1
    max_edit_distance : 0.3
    max_matches_per_extension : 0

5 - stats : Create stats

6 - filtered : Create filtered .map

    max_multi_maps : 5
    min_block : 5
    max_strata : 0
    level : 0
    filter_annotation : True
    min_intron : 20
    gene_pairing : True
    junction_filter : True

7 - filtered.counts : Create GTF gene counts and stats

    paired : True
    counts_weighted : True
    counts_multimaps : True
    counts_exon_threshold : 1.0

8 - filtered.stats : Create stats

9 - counts : Create GTF gene counts and stats

     paired : True
     counts_weighted : True
     counts_multimaps : True
     counts_exon_threshold : 1.0
