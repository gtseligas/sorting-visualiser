package sortingVisualiserPackage;

import java.util.List;

import javax.swing.SwingWorker;

/*
 * Worker class to implement the merge sorting algorithm
 */
public class MergeWorker extends SwingWorker<Void, Integer>{
	private int[] nums;
	private final PanelWrapper wrapper;
	
	// Simple constructor to initialise members.
	public MergeWorker(int[] array, PanelWrapper wrap) {
		nums = array;	
		wrapper = wrap;
	}
	
	// Recursive mergesort function
	private void mergesort(int[] a, int lo, int hi) {
		if (hi <= lo) return;
		int mid = lo + (hi - lo) / 2;
		
		mergesort(a,lo,mid);
		mergesort(a,mid+1,hi);
		merge(a,lo,mid,hi);
	}
	
	// Merge routine
	private void merge(int[] a, int lo, int mid, int hi)  {
		// Check if this is the final merging, and if yes, start coloring with LAVENDER
		boolean last = lo == 0 && hi == nums.length - 1;
		
		int[] b = new int[hi - lo + 1];
		int i = lo, j = mid + 1, k = 0;
		while(i <= mid && j <= hi) {
			if (a[j] < a[i]) {
				b[k++] = a[j++];
			}
			else {
				b[k++] = a[i++];
			}
		}
		
		while(i <= mid) {
			b[k++] = a[i++];
		}
		while(j <= hi) {
			b[k++] = a[j++];
		}
		
		for (int p = 0; p < k; p++) {
			a[lo + p] = b[p];
			// If this is the last merging start incrementing belowThisLav, to make the array lavender.
			if (last) {
				SortingPanel.belowThisLav++;
			}
			try {
				Thread.sleep(Configurations.DELAY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			publish(lo+p);
		}
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		mergesort(nums, 0, nums.length - 1);
		return null;
	}

	@Override
	protected void process(List<Integer> chunks) {
		super.process(chunks);
		int i = chunks.get(chunks.size()-1) + 1;
		
		// add sound
		SortingPanel.midiChannel[0].allNotesOff();
		SortingPanel.midiChannel[0].noteOn(i / Configurations.NOTE_GAP ,Configurations.NOTE_SPEED);
		
		SortingPanel.redIndex = i;
		SortingPanel.redIndex1 = SortingPanel.redIndex - 1;
		SortingPanel.redIndex2 = SortingPanel.redIndex - 2;
		SortingPanel.redIndex3 = SortingPanel.redIndex - 3;
		SortingPanel.redIndex4 = SortingPanel.redIndex - 4;
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
	}
	
}
