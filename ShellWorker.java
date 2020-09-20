package sortingVisualiserPackage;

import java.util.List;

import javax.swing.SwingWorker;

// Worker to implement shell sort
public class ShellWorker extends SwingWorker<Void, Pair>{
	private int[] nums;
	private final PanelWrapper wrapper;
	
	public ShellWorker(int[] array, PanelWrapper wrap) {
		nums = array;	
		wrapper = wrap;
	}

	@Override
	protected Void doInBackground() throws Exception {
		 int N = nums.length;
		 boolean last = false;
		 
		 // Initialize your step variable : h
		 int h = 1;
		 while (h < N/3) h = 3*h + 1; // 1, 4, 13, 40, 121, 364, ...
		
		 while (h >= 1) { 
			 // if this is the last pass, start using belowThisLav to paint the array lav
			 if (h==1) last = true;
			 
			// h-sort the array.
			 for (int i = h; i < N; i++) {
				 if (last && i == N - 1) SortingPanel.belowThisLav = i;
				 for (int j = i; j >= h && nums[j]<nums[j-h]; j -= h) {
					swap(j, j-h);
					if (last) 
						SortingPanel.belowThisLav = Math.max(j, SortingPanel.belowThisLav);
					publish(new Pair(j, j-h));
					Thread.sleep(Configurations.DELAY);
				 }
		 }

		 h = h/3;
		 }
		return null;
	}
	
	@Override
	protected void process(List<Pair> chunks) {
		super.process(chunks);
		int i = chunks.get(chunks.size() - 1).getFirst();
		int j = chunks.get(chunks.size() - 1).getSecond();
		
		// play sound
		SortingPanel.midiChannel[0].allNotesOff();
		SortingPanel.midiChannel[0].noteOn(j / Configurations.NOTE_GAP ,Configurations.NOTE_SPEED);
		SortingPanel.midiChannel[0].noteOn(i / Configurations.NOTE_GAP ,Configurations.NOTE_SPEED);

		SortingPanel.redIndex = i;
		SortingPanel.redIndex1 = SortingPanel.redIndex - 1;
		SortingPanel.redIndex2 = SortingPanel.redIndex - 2;
		SortingPanel.redIndex3 = SortingPanel.redIndex - 3;
		SortingPanel.redIndex4 = SortingPanel.redIndex - 4;

		SortingPanel.greenIndex = j;
		SortingPanel.greenIndex1 = SortingPanel.greenIndex - 1;
		SortingPanel.greenIndex2 = SortingPanel.greenIndex - 2;
		SortingPanel.greenIndex3 = SortingPanel.greenIndex - 3;
		SortingPanel.greenIndex4 = SortingPanel.greenIndex - 4;
		wrapper.getPanel().repaint();
	}

	private void swap(int i, int j) {
		int temp = nums[j];
		nums[j] = nums[i];
		nums[i] = temp;
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
	}
}
