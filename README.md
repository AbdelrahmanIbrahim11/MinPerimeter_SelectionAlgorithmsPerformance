# Problem 1 – Median / Selection Algorithm Performance

Three approaches to finding the median of an array were implemented and compared: a randomized divide-and-conquer selection algorithm, a deterministic linear-time selection algorithm (median-of-medians), and a naive baseline that sorts the array and indexes directly.

## 1. Randomized Selection

Partitions the array around a randomly chosen pivot, then recurses into only the side that contains the target rank. Expected running time is O(n), though the worst case (an unlucky sequence of pivot choices) is O(n²).

```java
int r = Random_Partition(array, p, q); // rank of pivot within [p,q]
if (i == r) return array[p + r - 1];
if (i < r)  return select(array, p, p + r - 2, i);
else        return select(array, p + r, q, i - r);
```

## 2. Deterministic Selection (Median of Medians)

Guarantees O(n) worst-case time by choosing a provably good pivot instead of a random one: the array is split into groups of 5, each group is sorted to find its median, and the median of those group-medians is used recursively as the pivot for partitioning the full array. The recursion falls back to sorting directly once the subarray size is 140 or smaller.

```java
// group into blocks of 5, sort each block, collect the medians
double median = select(medarray, 0, medarray.length - 1,
                        (medarray.length + 1) / 2);
// partition the array around that median, then recurse into
// whichever side contains the target rank
```

## 3. Naive Method

Sorts the entire array and returns the k-th element directly. Simple and reliable, with expected time of O(n log n).

```java
Arrays.sort(array);
return array[k - 1];
```

## Methodology

- Arrays of sizes n = 10, 100, 1,000, …, 10,000,000 (powers of 10) were generated with uniformly random values.
- Each algorithm was timed selecting the median (k = n/2).
- Each data point is the average of 5 independent trials.
- A warm-up phase (1,000 throwaway calls on small arrays) was run before timing began, so results reflect steady-state JIT-compiled performance rather than JVM startup/interpretation overhead.
- The deterministic selection algorithm's base case is n ≤ 140 (sorts directly below this threshold rather than recursing further), so its median-of-medians logic only actually engages for n > 140.

## Results

| n (input size) | Randomized (ms) | Deterministic (ms) | Naive (ms) |
|---|---|---|---|
| 10 | 0.0016 | 0.0012 | 0.0012 |
| 100 | 0.0048 | 0.0124 | 0.0115 |
| 1,000 | 0.0113 | 0.0838 | 0.0639 |
| 10,000 | 0.1018 | 0.5799 | 1.0001 |
| 100,000 | 0.9725 | 4.7958 | 7.5617 |
| 1,000,000 | 11.0966 | 39.1211 | 69.2740 |
| 10,000,000 | 97.6046 | 381.0438 | 808.7994 |

## Analysis

All three methods grow roughly linearly with n on the log-log plot once n is large enough for constant-factor and warm-up effects to become negligible, with two visibly different slopes: the naive method's slope is steeper, consistent with its O(n log n) complexity.

Among the two true selection algorithms, randomized selection is consistently faster in practice. This matches theory: median-of-medians does substantially more constant-factor work per level — grouping into blocks of 5, sorting every block, and an extra recursive call to find the pivot itself — whereas randomized selection does a single partition pass per level with no extra recursive call for pivot selection.

The naive method is competitive at very small n (where O(n log n) and O(n) are close), but falls increasingly behind as n grows; by n = 10,000,000 it takes roughly 8× longer than randomized selection.

# Problem 2 – Minimum Perimeter Triangle

## Approach

The problem asks for three distinct points, out of up to 10⁵ points, whose triangle has the smallest possible perimeter. A brute-force search over all triples is O(n³) and far too slow for the given constraints, so the problem is solved with a divide-and-conquer strategy structurally analogous to the classic closest-pair-of-points algorithm, extended from pairs to triples.

The algorithm proceeds as follows:

- Pre-sort all points once by x-coordinate and once by y-coordinate.
- Split the point set into a left half and a right half using the median x-coordinate.
- Recursively solve each half independently, obtaining the best (smallest) perimeter found entirely within the left half and entirely within the right half.
- Let δ be the smaller of those two perimeters — the best answer found so far.
- Build a strip containing only the points within δ of the median line (on either side), still sorted by y-coordinate.
- Scan the strip in y-order. For each point p, examine only the other strip points whose y-distance from p is less than δ, and test every pair of them together with p as a candidate triangle, updating δ whenever a smaller perimeter is found.
- Return the final value of δ — it is now the true minimum perimeter over the whole point set, since it accounts for triangles entirely in the left half, entirely in the right half, and every triangle that straddles the dividing line.

## Why the Strip is 2δ Wide (δ on Each Side)

The strip's width is derived directly from the definition of δ as the current best perimeter, rather than being chosen arbitrarily. If a candidate triangle crossing the median line is to beat δ, its perimeter — the sum of its three (non-negative) side lengths — must be strictly less than δ. Since all three sides are non-negative, this forces every individual side of that triangle to be less than δ as well: if even one side were ≥ δ, the total perimeter could not be smaller than δ.

From this, any point whose distance from the median line exceeds δ can be ruled out immediately: an edge connecting it to any point on the opposite side of the line would itself have length ≥ δ, which already disqualifies that triangle from being an improvement. Since a winning triangle may have a point up to δ away from the line on the left side and another point up to δ away on the right side, the region that must be searched spans δ to the left of the median plus δ to the right — a strip of total width 2δ.

This width is necessary and sufficient: narrower (for example, δ/2 on each side) would risk discarding a genuine winning triangle whose point lies between δ/2 and δ from the line, while anything wider would needlessly include points that are already provably too far away to contribute to a better answer. The same reasoning bounds the y-direction: any two points of a winning triangle must also be within δ of each other in y, since the edge joining them is one of the triangle's three sides and is therefore also less than δ. This is exactly why, when scanning the y-sorted strip, each anchor point only needs to look at other points within y-distance δ — anything farther away in y cannot belong to a triangle that improves on δ.

## Combine Step: Bounding the Work per Point

A further geometric argument (in the same style as the closest-pair proof, though with a different bound) shows that the number of strip points within the δ-tall y-window of any given anchor point is bounded by a constant, independent of n. Briefly: if perimeter ≤ δ is already known for any three points on the same side (guaranteed by the recursive call on that side), no three same-side points can all be mutually closer than δ/3 to one another, or their perimeter would be forced below δ. This limits how densely points can be packed into any small region of the window, which in turn bounds the number of candidate points any anchor needs to compare against to a constant — so the triple-checking work done per anchor point is O(1), not O(n).

## Time Complexity

Each level of the recursion does the following work, beyond the two recursive calls themselves:

- Partitioning the y-sorted array into left/right subsequences while preserving y-order: O(n). This is done without re-sorting, by tagging each point as belonging to the left or right half (matching the x-sorted split exactly, including ties) and then bucketing the y-sorted sequence in a single linear pass.
- Building the δ-wide strip from the restored y-sorted sequence: O(n).
- Scanning the strip and checking candidate triangles: each anchor point does O(1) work (bounded window, as argued above), so the whole strip scan is O(n).

This gives a per-level combine cost of O(n), yielding the recurrence:

```
T(n) = 2T(n/2) + O(n)
```

which solves to O(n log n) by the Master Theorem (same form as merge sort and the classic closest-pair algorithm). Including the one-time initial sort of the points by x and by y, which is also O(n log n), the overall time complexity of the algorithm is:

**O(n log n)**

This comfortably handles the assignment's constraint of up to n = 10⁵ points per test case, in contrast to the O(n³) brute-force approach of examining every triple, which would be far too slow at that scale.