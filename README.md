# sorting-visualiser
An application that visualizes sorting algorithms

## Array generation
The application lets the user generate four different kinds of arrays, containing all the natural numbers from 1 to 512 inclusive. 
### Sorted / Reverse sorted array
### Random array 
Simply shuffles the array using the Fisher-Yates algorithm
### Partially sorted array
Generates a random array using a variation of the Fisher - Yates shuffle, in which every array element can be swapped with only the ten next elements of the array.


## Sorting algorithms
### Bubble sort
### Insertion sort
### Merge sort
### Quicksort
### Heapsort
### Shell sort 
The gap sequence used for Shell sort is a_(n+1) = 3*a_n + 1 which guarantees a time compllexity of Θ(Ν^3/2)

For every sort there is a corresponding Worker class that extends SwingWorker. The purpose of the Worker classes is to implement the sorting algorithms and at the same time take care of drawing the visualisation animations.
