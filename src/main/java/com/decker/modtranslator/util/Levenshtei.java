/*
 * The MIT License
 *
 * Copyright 2014 Decker.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.decker.modtranslator.util;

/**
 *
 * @author Decker
 */
public class Levenshtei {

    /**
     * Compute Levenshtein distance Memory efficient version
     *
     * @param sRow
     * @param sCol
     * @return
     * @throws Exception
     */
    public int iLD(String sRow, String sCol) throws Exception {
        int RowLen = sRow.length();  // length of sRow
        int ColLen = sCol.length();  // length of sCol
        int RowIdx;                // iterates through sRow
        int ColIdx;                // iterates through sCol
        char Row_i;                // ith character of sRow
        char Col_j;                // jth character of sCol
        int cost;                   // cost

        /// Test string length
        if (Math.max(sRow.length(), sCol.length()) > Math.pow(2, 31)) {
            throw (new Exception("\nMaximum string length in Levenshtein.iLD is " + Math.pow(2, 31) + ".\nYours is " + Math.max(sRow.length(), sCol.length()) + "."));
        }

        // Step 1
        if (RowLen == 0) {
            return ColLen;
        }

        if (ColLen == 0) {
            return RowLen;
        }

        /// Create the two vectors
        int[] v0 = new int[RowLen + 1];
        int[] v1 = new int[RowLen + 1];
        int[] vTmp;

        /// Step 2
        /// Initialize the first vector
        for (RowIdx = 1; RowIdx <= RowLen; RowIdx++) {
            v0[RowIdx] = RowIdx;
        }

        // Step 3
        /// Fore each column
        for (ColIdx = 1; ColIdx <= ColLen; ColIdx++) {
            /// Set the 0'th element to the column number
            v1[0] = ColIdx;

            //  Col_j = sCol[ColIdx - 1];
            Col_j = sCol.charAt(ColIdx - 1);
            // Step 4
            /// Fore each row
            for (RowIdx = 1; RowIdx <= RowLen; RowIdx++) {
                // Row_i = sRow[RowIdx - 1];
                Row_i = sRow.charAt(RowIdx - 1);
                // Step 5
                if (Row_i == Col_j) {
                    cost = 0;
                } else {
                    cost = 1;
                }

                // Step 6
                /// Find minimum
                int m_min = v0[RowIdx] + 1;
                int b = v1[RowIdx - 1] + 1;
                int c = v0[RowIdx - 1] + cost;

                if (b < m_min) {
                    m_min = b;
                }
                if (c < m_min) {
                    m_min = c;
                }

                v1[RowIdx] = m_min;
            }

            /// Swap the vectors
            vTmp = v0;
            v0 = v1;
            v1 = vTmp;

        }

        // Step 7
        /// Value between 0 - 100
        /// 0==perfect match 100==totaly different
        /// 
        /// The vectors where swaped one last time at the end of the last loop,
        /// that is why the result is now in v0 rather than in v1
        //   System.Console.WriteLine("iDist=" + v0[RowLen]);
        int max = Math.max(RowLen, ColLen);
        return ((100 * v0[RowLen]) / max);
    }
}
