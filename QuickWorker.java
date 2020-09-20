package sortingVisualiserPackage;

import java.util.List;

import javax.swing.SwingWorker;

// Implements quick sorting
public class QuickWorker extends SwingWorker<Void, Pair> {
	private int[] nums;
	private final PanelWrapper wrapper;
	
	// Simple constructor to initialise members.
	public QuickWorker(int[] array, PanelWrapper wrap) {
		nums = array;	
		wrapper = wrap;
	}
	
	private void swap(int i, int j) {
		int temp = nums[j];
		nums[j] = nums[i];
		nums[i] = temp;
	}
	
	private int partition(int lo, int hi) {
		int i = lo;
		int j = hi + 1;
	
		// Paint the partition element yellow
	  /* publish(new Pair(lo, Configurations.ARRAY_SIZE + 1));
		try {
			Thread.sleep(Configurations.DELAY);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */ 
		
		while (true) {
			while(nums[++i] < nums[lo]) {
				publish(new Pair(i,0));
				try {
					Thread.sleep(Configurations.DELAY);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (i >= hi) break;
			}
			while(nums[lo] < nums[--j]) {
				publish(new Pair(j,0));
				try {
					Thread.sleep(Configurations.DELAY);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (j <= lo) break;
			}
			
			if (i >= j) break;
			swap(i, j);
			publish(new Pair(i,j));
			try {
				Thread.sleep(Configurations.DELAY);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		swap(lo,j);
		/*publish(new Pair(lo,j));
		try {
			Thread.sleep(Configurations.DELAY);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return j;
	}
	
	@Override
	protected void process(List<Pair> chunks) {
		super.process(chunks);
		int i = (chunks.get(chunks.size()-1)).getFirst();
		int j = (chunks.get(chunks.size()-1)).getSecond();
		
		// If both are zero, the array was just shuffled, so print it one more time
		if (i==0 && j==0) {
			wrapper.getPanel().repaint();
			return;
		}
		
		// If the second element is zero, it indicates that quicksort still searches for entries to swap.
		// Thus you just color red the cursor of the sort.
		if (j == 0) {
			// play single note
			SortingPanel.midiChannel[0].allNotesOff();
			SortingPanel.midiChannel[0].noteOn(i / Configurations.NOTE_GAP ,Configurations.NOTE_SPEED);
			
			SortingPanel.redIndex = i;
			SortingPanel.redIndex1 = i - 1;
			SortingPanel.redIndex2 = i - 2;
			SortingPanel.redIndex3 = i - 3;
			SortingPanel.redIndex4 = i -  4;
			
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
		
		// If j != 0 the algortithm just did a swap. So you color the two swapped elements with green and yellow
		else {
			// play double note
			SortingPanel.midiChannel[0].allNotesOff();
			SortingPanel.midiChannel[0].noteOn(i / Configurations.NOTE_GAP ,Configurations.NOTE_SPEED);
			SortingPanel.midiChannel[0].noteOn(j / Configurations.NOTE_GAP ,Configurations.NOTE_SPEED);
			
			SortingPanel.redIndex = -1;
			SortingPanel.redIndex1 = -1;
			SortingPanel.redIndex2 = -1;
			SortingPanel.redIndex3 = -1;
			SortingPanel.redIndex4 = -1;
			
			SortingPanel.greenIndex = i;
			SortingPanel.greenIndex2 = i - 2;
			SortingPanel.greenIndex1 = i - 1;
			SortingPanel.greenIndex3 = i - 3;
			SortingPanel.greenIndex4 = i - 4;
			
			SortingPanel.yellowIndex = j;
			SortingPanel.yellowIndex2 = j - 2;
			SortingPanel.yellowIndex1 = j - 1;
			SortingPanel.yellowIndex3 = j - 3;
			SortingPanel.yellowIndex4 = j - 4;
		}
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
	
	private void quicksort(int lo, int hi) {
		if (hi <= lo) return;
		int p = partition(lo, hi);
		quicksort(lo, p - 1);
		quicksort(p + 1,hi);
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		// shuffle for performance guarantee
		// you can comment out the next line if you want to check the performance of quicksort in bad cases
		// e.g. sorted/reverse sorted array
		//sortingVisualiser.shuffle(nums);
		/*publish(new Pair(0,0));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		quicksort(0, nums.length-1);
		return null;
	}

}
