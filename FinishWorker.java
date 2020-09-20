package sortingVisualiserPackage;

import java.util.List;

import javax.swing.SwingWorker;

public class FinishWorker extends SwingWorker<Void,Integer>{
	private int[] nums;
	private final PanelWrapper wrapper;
	
	public FinishWorker(int[] array, PanelWrapper wrap) {
		nums = array;	
		wrapper = wrap;
	}
	
	@Override
	protected Void doInBackground() throws Exception {
			int n = nums.length;  
	        
			for (int i = 0; i < n; i++) {
				publish(i);
				Thread.sleep(1);
			}
			publish(-1);
			return null;
	}
		
	@Override
	protected void process(List<Integer> chunks) { 
		int red = chunks.get(chunks.size() - 1);
		
		//play sounds
		SortingPanel.midiChannel[0].allNotesOff();
		SortingPanel.midiChannel[0].noteOn(red / Configurations.NOTE_GAP ,Configurations.NOTE_SPEED);

		
		// update the set of red indices
		SortingPanel.redIndex = red;
		SortingPanel.redIndex1 = SortingPanel.redIndex - 1;
		SortingPanel.redIndex2 = SortingPanel.redIndex - 2;
		SortingPanel.redIndex3 = SortingPanel.redIndex - 3;
		SortingPanel.redIndex4 = SortingPanel.redIndex - 4;
		
		SortingPanel.greenIndex = -1;
		SortingPanel.greenIndex1 = -1;
		SortingPanel.greenIndex2 = -1;
		SortingPanel.greenIndex3 = -1;
		SortingPanel.greenIndex4 = -1;
		
		wrapper.getPanel().repaint();
	}

	@Override
	protected void done() {
		super.done();
		SortingPanel.updateSortButtons();
		SortingPanel.skip.setVisible(false);
		SortingPanel.updateArrayButtons();
		
		// turn off the sounds you generated during sorting
		SortingPanel.midiChannel[0].allNotesOff();
		
		if (Configurations.DELAY==0) Configurations.DELAY = SortingPanel.previousSpeed;
	}
}

