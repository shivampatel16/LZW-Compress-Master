# LZW-Compress-Master: A Java-based ASCII and Binary File Compressor and Decompressor

This project was the fifth among the 5 unique projects of the '95-771 - Data Structure and Algorithms for Information Processing' course at [Carnegie Mellon University](https://www.cmu.edu/). Prof. Mike McCarthy, a renowned genius mind in this field, taught the course.

- Implemented Lempel-Ziv Welch compression algorithm in Java, capable of compressing and decompressing both ASCII and binary files

- Read 8-bit bytes from the input file and wrote 12-bit chunks to the output compressed file

- Wrote custom hash table using an array of linked lists to store strings and their corresponding 12-bit values during compression

- Tested program on various text and binary files, including text, HTML, CSV, and MP4 files

- Handled large files that overflow the 12-bit table size by detecting overflow and generating a new table for processing

- Utilized knowledge of bit manipulation and file I/O to successfully complete the project

- Improved understanding of the LZW algorithm through independent research and testing


