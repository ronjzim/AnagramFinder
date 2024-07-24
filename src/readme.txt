Ron Zimmermann - rjz2117 - Capstone Project - readme.txt

Program execution time results:

    bst:
    1. java MostAnagramsFinder words.txt bst  1.73s user 0.05s system 117% cpu 1.522 total
    2. java MostAnagramsFinder words.txt bst  1.77s user 0.04s system 118% cpu 1.533 total
    3. java MostAnagramsFinder words.txt bst  1.78s user 0.04s system 118% cpu 1.537 total
    4. java MostAnagramsFinder words.txt bst  1.74s user 0.04s system 118% cpu 1.499 total
    5. java MostAnagramsFinder words.txt bst  1.72s user 0.05s system 116% cpu 1.520 total
    Average time: 1.748 seconds

    rbt:
    1. java MostAnagramsFinder words.txt rbt  0.81s user 0.05s system 156% cpu 0.551 total
    2. java MostAnagramsFinder words.txt rbt  0.78s user 0.04s system 161% cpu 0.510 total
    3. java MostAnagramsFinder words.txt rbt  0.83s user 0.04s system 169% cpu 0.515 total
    4. java MostAnagramsFinder words.txt rbt  0.81s user 0.04s system 163% cpu 0.515 total
    5. java MostAnagramsFinder words.txt rbt  0.82s user 0.04s system 164% cpu 0.518 total
    Average time: 0.81 seconds

    hash:
    1. java MostAnagramsFinder words.txt hash  0.59s user 0.04s system 201% cpu 0.313 total
    2. java MostAnagramsFinder words.txt hash  0.55s user 0.04s system 204% cpu 0.285 total
    3. java MostAnagramsFinder words.txt hash  0.58s user 0.04s system 209% cpu 0.293 total
    4. java MostAnagramsFinder words.txt hash  0.56s user 0.04s system 196% cpu 0.304 total
    5. java MostAnagramsFinder words.txt hash  0.54s user 0.04s system 202% cpu 0.286 total
    Average time: 0.564 seconds

Program expectations:
I expected the hash table data structure to have the fastest runtime in this program.
This is because insertion and retrieval from the specified data structure is the primary task
that has to be done for n words in the text file. In a hash map, insertion and retrieval runs
in constant time. In binary search trees, each one of these operations runs in average lg(n) runtime.
Worst case being O(n), making them way slower than hash tables for a large number of these operations.
However, Red Black trees are auto-balancing and therefore insertion and retrieval take worst case
O(lg(n)) runtime. Therefore, I expected Red Black trees to outperform Binary Search Trees but still
be slower than the hash table.

Program results:
The programs results matched my expectations for the most part. Red Black Trees performed more
closely in runtime to hash tables than I thought. Binary Search Trees lagged far behind,
with average runtime being more than twice that of the Red Black Tree and 3 times that of the
hash table.