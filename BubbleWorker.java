package sortingVisualiserPackage;
import java.util.List;

import javax.swing.SwingWorker;


public class BubbleWorker extends SwingWorker<Void,Integer> {
	private int[] nums;
	private final PanelWrapper wrapper;
	
	public BubbleWorker(int[] array, PanelWrapper wrap) {
		nums = array;	
		wrapper = wrap;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
			int n = nums.length;  
			boolean swaps = false;
	        int temp = 0;  
	         for(int i=0; i < n; i++){  
	        	 swaps = false;
	                 for(int j=1; j < (n-i); j++){  
	                          if(nums[j-1] > nums[j]){  
	                                 //swap elements  
	                                 temp = nums[j-1];  
	                                 nums[j-1] = nums[j];  
	                                 nums[j] = temp;
	                                 swaps = true;
	                                 Thread.sleep(Configurations.DELAY);
	                                 publish(j);
	                          }          
	                 }
	                 if (!swaps) {
	                	 SortingPanel.aboveThisLav = 0;
	                	 break;
	                 }
	                 SortingPanel.aboveThisLav--;
	         }  
			return null;
	}
		
	@Override
	protected void process(List<Integer> chunks) { 
		int red = chunks.get(chunks.size() - 1) + 1;
		
		// play the sounds
		SortingPanel.midiChannel[0].allNotesOff();
		SortingPanel.midiChannel[0].noteOn(red / Configurations.NOTE_GAP ,Configurations.NOTE_SPEED);
		
		// update the set of red indices
		SortingPanel.redIndex = red;
		SortingPanel.redIndex1 = SortingPanel.redIndex - 1;
		SortingPanel.redIndex2 = SortingPanel.redIndex - 2;
		SortingPanel.redIndex3 = SortingPanel.redIndex - 3;
		SortingPanel.redIndex4 = SortingPanel.redIndex - 4;
		wrapper.getPanel().repaint();
	}

	@Override
	protected void done() {
		// TODO Auto-generated method stub
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
