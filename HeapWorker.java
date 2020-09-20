package sortingVisualiserPackage;

import java.util.List;

import javax.swing.SwingWorker;

// Worker to implement the heap sort algorithm
public class HeapWorker extends SwingWorker<Void, Pair> {
	
	// simple class for the max heap
	private class Heap {
		private int[] a;  // array for the heap
	    private int N;    // size of the heap
		
		// simple constructor
		public Heap(int[] nums) {
			a = nums;
			N = a.length;
			
			// call heapify on the array, iterating from node N/2-1 up to the root
			for (int i = N/2 - 1; i>=0; i--) {
				heapify(i);
			}	
		}
		
		// simple method to heapify a node, given that it's two children are already max heaps
		private void heapify(int i) {
			int j = leftChild(i);
			while(j <= N-1) {
				// if the node has a right child, and that child is greater than the left child, set j to point the right child
				if (j + 1 <= N - 1 && a[j+1] > a[j]) j = j+1;
				
				// if the node is greater than the children, return 
				if (a[i] >= a[j]) return;
				
				// otherwise sink it to the bottom of the heap
				swap(i, j);
				
				// draw the animation for the swap
				publish(new Pair(i,j));
				try {
					Thread.sleep(Configurations.DELAY);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// increment i
				i = j;
				j = leftChild(i);
			}
		}
		
		private void delMax() {
			if(N==0) return;
			// swap the max element with the last node
			swap(0, N-1);
			
			/*
			 * For some weird reason I couldn't synch the animations of delMax and heapify, so I had to create another worker just to 
			 * paint the animation of delMax.
			 */
			MaxWorker max = new MaxWorker(N-1,wrapper);
			max.execute();
            
			// decrement the size, leaving the max element out of range for the heap
			N--;
			// heapify the root
			heapify(0);
		}
		
		private void swap(int b, int c) {
			int temp = a[b];
			a[b] = a[c];
			a[c] = temp;
		}
		
		// left child of i-th node
		private int leftChild(int i) {	
			return 2*i + 1;
		}
				
		// Simple worker to draw the green trace, when a maximum element is placed at the end of the array
		private class MaxWorker extends SwingWorker<Void, Integer> {
			private int N;
			PanelWrapper wrapper;
			
			public MaxWorker(int n, PanelWrapper w) {
				wrapper = w;
				N = n;
			}
			
			@Override
			protected Void doInBackground() throws Exception {
				publish(N);
				Thread.sleep(Configurations.DELAY);
				return null;
			}

			@Override
			protected void process(List<Integer> chunks) {
				super.process(chunks);
				//play the sound
				SortingPanel.midiChannel[0].allNotesOff();
				SortingPanel.midiChannel[0].noteOn(N / Configurations.NOTE_GAP ,200);
				
				SortingPanel.greenIndex = N;
				SortingPanel.greenIndex1 = SortingPanel.greenIndex + 1;
				SortingPanel.greenIndex2 = SortingPanel.greenIndex + 2;
				SortingPanel.greenIndex3 =  SortingPanel.greenIndex + 3;
				SortingPanel.greenIndex4 = SortingPanel.greenIndex + 4;
				SortingPanel.aboveThisLav = N;
				
				wrapper.getPanel().repaint();
			}		
		}
	}
	
	
	
	private final int[] nums;
	private final PanelWrapper wrapper;
	private Heap heap;
	
	public HeapWorker(int[] array, PanelWrapper wrap) {
		nums = array;	
		wrapper = wrap;
	}
	
	
	@Override
	protected Void doInBackground() throws Exception {
		heap = new Heap(nums);
		for (int i = 0; i < nums.length; i++) 
			heap.delMax();
		return null;
	}


	@Override
	protected void process(List<Pair> chunks) {
		super.process(chunks);
		
		int i = chunks.get(chunks.size() - 1).getFirst();
		int j = chunks.get(chunks.size() - 1).getSecond();
		
		// play the sound
		SortingPanel.midiChannel[0].allNotesOff();
		SortingPanel.midiChannel[0].noteOn(j / Configurations.NOTE_GAP ,Configurations.NOTE_SPEED);
			
		SortingPanel.yellowIndex = j;
		SortingPanel.yellowIndex1 = SortingPanel.yellowIndex + 1;
		SortingPanel.yellowIndex2 = SortingPanel.yellowIndex + 2;
		SortingPanel.yellowIndex3 =  SortingPanel.yellowIndex + 3;
		SortingPanel.yellowIndex4 = SortingPanel.yellowIndex + 4;
			
		SortingPanel.redIndex = i;
		SortingPanel.redIndex1 = SortingPanel.redIndex + 1;
		SortingPanel.redIndex2 = SortingPanel.redIndex + 2;
		SortingPanel.redIndex3 =  SortingPanel.redIndex + 3;
		SortingPanel.redIndex4 = SortingPanel.redIndex + 4;
			
		wrapper.getPanel().repaint();
	}
	
	@Override
	protected void done() {
		super.done();
	
		FinishWorker finish = new FinishWorker(nums, wrapper);
		finish.execute();

		SortingPanel.sorting = false;
		SortingPanel.redIndex = -1;
		SortingPanel.redIndex1 = -1;
		SortingPanel.redIndex2 = -1;
		SortingPanel.redIndex3 = -1;
		SortingPanel.redIndex4 = -1;
		
		SortingPanel.greenIndex = -1;
		SortingPanel.greenIndex1 = -1;
		SortingPanel.greenIndex2 = -1;
		SortingPanel.greenIndex3 = -1;
		SortingPanel.greenIndex4 = -1;
		
		SortingPanel.yellowIndex = -1;
		SortingPanel.yellowIndex1 = -1;
		SortingPanel.yellowIndex2 = -1;
		SortingPanel.yellowIndex3 = -1;
		SortingPanel.yellowIndex4 = -1;
	}
	
	

}
