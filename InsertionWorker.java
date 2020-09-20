package sortingVisualiserPackage;

import java.util.List;

import javax.swing.SwingWorker;


public class InsertionWorker extends SwingWorker<Void, Pair> {
	
	private int[] nums;
	private final PanelWrapper wrapper;
	
	public InsertionWorker(int[] array, PanelWrapper wrap) {
		nums = array;	
		wrapper = wrap;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		int i, key, j;  
	    for (i = 1; i < nums.length; i++) {  
	        key = nums[i];  
	        j = i - 1;  
	  
	        /* Move elements of arr[0..i-1], that are  
	        greater than key, to one position ahead  
	        of their current position */
	        while (j >= 0 && nums[j] > key) {  
	            nums[j + 1] = nums[j]; 
	            nums[j] = key; 
	            j = j - 1; 
	            Thread.sleep(Configurations.DELAY);
	            publish(new Pair(j,i));
	        }  
	        nums[j + 1] = key; 
            Thread.sleep(Configurations.DELAY);
	        publish(new Pair(j,i));
	    }  
		return null;
	}


	@Override
	protected void process(List<Pair> chunks) {
		super.process(chunks);
		// Leading index of the red trail
		int j = (chunks.get(chunks.size()-1)).getFirst();
		// Index that works as upper bound for the trail
		int i = (chunks.get(chunks.size()-1)).getSecond();
		
		// Initialize the indices
		SortingPanel.redIndex1 = -1;
		SortingPanel.redIndex2 = -1;
		SortingPanel.redIndex3 = -1;
		SortingPanel.redIndex4 = -1;
		
		
		// play sounds
		SortingPanel.midiChannel[0].allNotesOff();
		SortingPanel.midiChannel[0].noteOn(j / Configurations.NOTE_GAP ,Configurations.NOTE_SPEED);
		
		// Update the head of the trail and the rest of them only if they are below i ; which is the upper bound 
		SortingPanel.redIndex = j;
		if(SortingPanel.redIndex + 1 <= i)
			SortingPanel.redIndex1 = SortingPanel.redIndex + 1;
		if(SortingPanel.redIndex + 2 <= i)
			SortingPanel.redIndex2 = SortingPanel.redIndex + 2;
		if(SortingPanel.redIndex + 3 <= i)
			SortingPanel.redIndex3 = SortingPanel.redIndex + 3;
		if(SortingPanel.redIndex + 4 <= i)
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
	}
	
}
