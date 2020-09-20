package sortingVisualiserPackage;

import java.awt.*;

import javax.sound.midi.Instrument;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class SortingPanel extends JPanel {
	private int[] nums;
	public static boolean sorting = false;
	public static int previousSpeed = 0;
	
	// A set of indices that indicate which entries of the array are colored with a red palette ; the higher the index
	// the fader the red.
	public static int redIndex = -1;
	public static int redIndex1 = -1;
	public static int redIndex2 = -1;
	public static int redIndex3 = -1;
	public static int redIndex4 = -1;
	// indices for green palette
	public static int greenIndex = -1;
	public static int greenIndex1 = -1;
	public static int greenIndex2 = -1;
	public static int greenIndex3 = -1;
	public static int greenIndex4 = -1;
	// indices for yellow palette
	public static int yellowIndex = -1;
	public static int yellowIndex1 = -1;
	public static int yellowIndex2 = -1;
	public static int yellowIndex3 = -1;
	public static int yellowIndex4 = -1;
	
	// Above this index everything is colored LAVENDER. Used in bubble sort
	public static int aboveThisLav = Configurations.ARRAY_SIZE;
	// Below this index everything is colored LAVENDER. Used in merge sort
	public static int belowThisLav = -1;
	
	// The red palette
	private static final Color RED4 = new Color(255,186,186);
    private static final Color RED3 = new Color(255,123,123);
    private static final Color RED2 = new Color(255,82,82);
    private static final Color RED1 = new Color(255,0,0);
    private static final Color RED = new Color(167,0,0);
    // The green palette
    private static final Color GREEN = new Color(0,255,0);
    private static final Color GREEN1 = new Color(25,255,25);
    private static final Color GREEN2 = new Color(76,255,76);
    private static final Color GREEN3 = new Color(127,255,127);
    private static final Color GREEN4 = new Color(178,255,178);
    // The yellow palette
    private static final Color YELLOW = new Color(255,255,20);
    private static final Color YELLOW1 = new Color(255,255,70);
    private static final Color YELLOW2 = new Color(255,255,90);
    private static final Color YELLOW3 = new Color(255,255,127);
    private static final Color YELLOW4 = new Color(255,255,178);
    
    private static final Color LAVENDER = new Color(216,191,216);

    static JButton bubble;
    static JButton merge;
	static JButton shuffle;
	static JButton insertion;
	static JButton quick;
	static JButton shell;
	static JButton fast;
	static JButton medium;
	static JButton slow;
	static JButton skip;
	static JButton sorted;
	static JButton reversed;
	static JButton partially;
	static JButton heap;
	
	// Synthesizer to play the notes
	public static Synthesizer synth;
	public static MidiChannel[] midiChannel;
	public static Instrument[] inst;
	
	// Constructor for sorting panel. Initialises nums array, creates a button "Start" that executes the BubbleWorker.
	SortingPanel(int[] n) {
		nums = new int[n.length];
		for (int i = 0; i < n.length; i++)
			nums[i] = n[i];
		
		setLayout(null);
		setBackground(new Color(30, 19, 34));
		
		// set up sound stuff
		try {
			synth = MidiSystem.getSynthesizer();
		} catch (MidiUnavailableException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			synth.open();
		} catch (MidiUnavailableException e) {
			//// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		midiChannel = synth.getChannels();
		inst = synth.getDefaultSoundbank().getInstruments();
		synth.loadInstrument(inst[1]);
		
		// Add the bubble sort button
		bubble = createSimpleButton("Bubble");
		bubble.setBounds(Configurations.PANEL_WIDTH - Configurations.START_BTN_HOFFSET, Configurations.START_BTN_VOFFSET , Configurations.BUTTON_WIDTH, Configurations.BUTTON_HEIGHT);
		bubble.addActionListener(ae -> {
			// Initalize the lavender indice
			aboveThisLav = Configurations.ARRAY_SIZE;
			belowThisLav = -1;
			JPanel panel = (JPanel) ((JButton)ae.getSource()).getParent();
			
			PanelWrapper wrapper = new PanelWrapper(panel);
			
			BubbleWorker worker = new BubbleWorker(nums, wrapper);
			sorting = true;
			updateSortButtons();
			updateArrayButtons();
			skip.setVisible(true);
			worker.execute();
		});
		add(bubble);
		
		// Add the merge sort button
		merge = createSimpleButton("Merge");
		merge.setBounds(Configurations.PANEL_WIDTH - Configurations.START_BTN_HOFFSET - 100, Configurations.START_BTN_VOFFSET , Configurations.BUTTON_WIDTH, Configurations.BUTTON_HEIGHT);
		merge.addActionListener(ae -> {
			JPanel panel = (JPanel) ((JButton)ae.getSource()).getParent();
			
			// Initalize the lavender indice
			aboveThisLav = Configurations.ARRAY_SIZE;
			belowThisLav = -1;
			
			PanelWrapper wrapper = new PanelWrapper(panel);
			
			MergeWorker worker = new MergeWorker(nums, wrapper);
			sorting = true;
			updateSortButtons();
			updateArrayButtons();	
			skip.setVisible(true);
			worker.execute(); 
		});
		add(merge);
		
		// Add insertion sort button
		insertion = createSimpleButton("Insertion");
		insertion.setBounds(Configurations.PANEL_WIDTH - Configurations.START_BTN_HOFFSET - 208, Configurations.START_BTN_VOFFSET , Configurations.BUTTON_WIDTH + 8, Configurations.BUTTON_HEIGHT);
		insertion.addActionListener(ae -> {
			JPanel panel = (JPanel) ((JButton)ae.getSource()).getParent();
			// Initalize the lavender indice
			aboveThisLav = Configurations.ARRAY_SIZE;
			belowThisLav = -1;
			
			PanelWrapper wrapper = new PanelWrapper(panel);
			
			InsertionWorker worker = new InsertionWorker(nums, wrapper);
			sorting = true;
			updateSortButtons();
			updateArrayButtons();
			skip.setVisible(true);
			worker.execute(); 
		});
		add(insertion);
		
		// Add quick sort button
		quick = createSimpleButton("Quick");
		quick.setBounds(Configurations.PANEL_WIDTH - Configurations.START_BTN_HOFFSET - 308, Configurations.START_BTN_VOFFSET , Configurations.BUTTON_WIDTH, Configurations.BUTTON_HEIGHT);
		quick.addActionListener(ae -> {
			JPanel panel = (JPanel) ((JButton)ae.getSource()).getParent();
			// Initalize the lavender indice
			aboveThisLav = Configurations.ARRAY_SIZE;
			belowThisLav = -1;
					
			PanelWrapper wrapper = new PanelWrapper(panel);
			
			QuickWorker worker = new QuickWorker(nums, wrapper);
			sorting = true;
			updateSortButtons();
			updateArrayButtons();
			skip.setVisible(true);
			worker.execute(); 
		});
		add(quick);
		
		// Add shell sort button
		shell = createSimpleButton("Shell");
		shell.setBounds(Configurations.PANEL_WIDTH - Configurations.START_BTN_HOFFSET - 408, Configurations.START_BTN_VOFFSET , Configurations.BUTTON_WIDTH, Configurations.BUTTON_HEIGHT);
		shell.addActionListener(ae -> {
			JPanel panel = (JPanel) ((JButton)ae.getSource()).getParent();
					
			// Initalize the lavender indice
			aboveThisLav = Configurations.ARRAY_SIZE;
			belowThisLav = -1;
					
			PanelWrapper wrapper = new PanelWrapper(panel);
					
			ShellWorker worker = new ShellWorker(nums, wrapper);
			sorting = true;
			updateSortButtons();
			updateArrayButtons();
			skip.setVisible(true);
			worker.execute(); 
		});
		add(shell);	
		
		// Add heap sort button
				heap = createSimpleButton("Heap");
				heap.setBounds(Configurations.PANEL_WIDTH - Configurations.START_BTN_HOFFSET - 508, Configurations.START_BTN_VOFFSET , Configurations.BUTTON_WIDTH, Configurations.BUTTON_HEIGHT);
				heap.addActionListener(ae -> {
					JPanel panel = (JPanel) ((JButton)ae.getSource()).getParent();
							
					// Initalize the lavender indice
					aboveThisLav = Configurations.ARRAY_SIZE;
					belowThisLav = -1;
							
					PanelWrapper wrapper = new PanelWrapper(panel);
							
					HeapWorker worker = new HeapWorker(nums, wrapper);
					sorting = true;
					updateSortButtons();
					updateArrayButtons();
					skip.setVisible(true);
					worker.execute(); 
				});
				add(heap);	
		
		// Add shuffle button
		shuffle = createSimpleButton("Shuffle");
		shuffle.setBounds(Configurations.SHUFFLE_BTN_HOFFSET, Configurations.SHUFFLE_BTN_VOFFSET, Configurations.BUTTON_WIDTH, Configurations.BUTTON_HEIGHT);
		shuffle.addActionListener(ae -> {
			if (sorting) return;
			// Initalize the lavender indice
			aboveThisLav = Configurations.ARRAY_SIZE;
			belowThisLav = -1;
			
			sortingVisualiser.shuffle(nums);
			repaint();
		});
		add(shuffle);
		
		// Add buttons with speed options
		fast = createSimpleButton("Fast");
		fast.setBounds(Configurations.PANEL_WIDTH - Configurations.START_BTN_HOFFSET, Configurations.SHUFFLE_BTN_VOFFSET + 40, Configurations.BUTTON_WIDTH, Configurations.BUTTON_HEIGHT);
		fast.addActionListener(ae -> {
			// Change the delay
			Configurations.DELAY = 1;
		});
		add(fast);
		
		medium = createSimpleButton("Medium");
		medium.setBounds(Configurations.PANEL_WIDTH - Configurations.START_BTN_HOFFSET - 100, Configurations.SHUFFLE_BTN_VOFFSET + 40, Configurations.BUTTON_WIDTH, Configurations.BUTTON_HEIGHT);
		medium.addActionListener(ae -> {
			// Change the delay
			Configurations.DELAY = 10;
		});
		add(medium);
		
		slow = createSimpleButton("Slow");
		slow.setBounds(Configurations.PANEL_WIDTH - Configurations.START_BTN_HOFFSET - 200, Configurations.SHUFFLE_BTN_VOFFSET + 40, Configurations.BUTTON_WIDTH, Configurations.BUTTON_HEIGHT);
		slow.addActionListener(ae -> {
			// Change the delay
			Configurations.DELAY = 30;
		});
		add(slow);
		
		skip = createSimpleButton("Skip");
		skip.setBounds(Configurations.PANEL_WIDTH - Configurations.START_BTN_HOFFSET - 300, Configurations.SHUFFLE_BTN_VOFFSET + 40, Configurations.BUTTON_WIDTH, Configurations.BUTTON_HEIGHT);
		skip.addActionListener(ae -> {
			// Change the delay, but keep it saved to restore it later
			midiChannel[0].allNotesOff();
			previousSpeed = Configurations.DELAY;
			Configurations.DELAY = 0;
		});
		skip.setVisible(false);
		add(skip);
		
		// Add buttons that generate different types of arrays
		sorted = createSimpleButton("Sorted");
		sorted.setBounds(Configurations.SHUFFLE_BTN_HOFFSET , Configurations.SHUFFLE_BTN_VOFFSET + 40, Configurations.BUTTON_WIDTH, Configurations.BUTTON_HEIGHT);
		sorted.addActionListener(ae -> {
			if (sorting) return;
			// Initalize the lavender indice
			aboveThisLav = Configurations.ARRAY_SIZE;
			belowThisLav = -1;
			
			sortingVisualiser.sortedArray(nums);
			repaint();
		});
		add(sorted);
		
		reversed = createSimpleButton("Reversed");
		reversed.setBounds(Configurations.SHUFFLE_BTN_HOFFSET +100, Configurations.SHUFFLE_BTN_VOFFSET, Configurations.BUTTON_WIDTH + 5, Configurations.BUTTON_HEIGHT);
		reversed.addActionListener(ae -> {
			if (sorting) return;
			// Initalize the lavender indice
			aboveThisLav = Configurations.ARRAY_SIZE;
			belowThisLav = -1;
			
			sortingVisualiser.reversedArray(nums);
			repaint();
		});
		add(reversed);
		
		partially = createSimpleButton("Partially sorted");
		partially.setBounds(Configurations.SHUFFLE_BTN_HOFFSET + 100, Configurations.SHUFFLE_BTN_VOFFSET + 40, Configurations.BUTTON_WIDTH + 40, Configurations.BUTTON_HEIGHT);
		partially.addActionListener(ae -> {
			if (sorting) return;
			// Initalize the lavender indice
			aboveThisLav = Configurations.ARRAY_SIZE;
			belowThisLav = -1;
			
			sortingVisualiser.partiallySortedArray(nums);
			repaint();
		});
		add(partially);
		
	}
	
	// Pains the bars of the array.
	public void paintComponent(Graphics g){
        super.paintComponent(g);

        g.setColor(Color.white);
        for (int i=0; i<nums.length; i++ ) {
        	g.fillRect(i*Configurations.BAR_WIDTH, Configurations.BAR_LOWEST - nums[i], Configurations.BAR_WIDTH, nums[i]);	        
	        if (greenIndex == i) {
	        	g.setColor(GREEN);
	        	g.fillRect(i*Configurations.BAR_WIDTH, Configurations.BAR_LOWEST - nums[i], Configurations.BAR_WIDTH, nums[i]);
	        	g.setColor(Color.white);
	        }
	        else if (greenIndex1 == i) {
	        	g.setColor(GREEN1);
	        	g.fillRect(i*Configurations.BAR_WIDTH, Configurations.BAR_LOWEST - nums[i], Configurations.BAR_WIDTH, nums[i]);
	        	g.setColor(Color.white);
	        }
	        else if (greenIndex2 == i) {
	        	g.setColor(GREEN2);
	        	g.fillRect(i*Configurations.BAR_WIDTH, Configurations.BAR_LOWEST - nums[i], Configurations.BAR_WIDTH, nums[i]);
	        	g.setColor(Color.white);
	        }
	        else if (greenIndex3 == i) {
	        	g.setColor(GREEN3);
	        	g.fillRect(i*Configurations.BAR_WIDTH, Configurations.BAR_LOWEST - nums[i], Configurations.BAR_WIDTH, nums[i]);
	        	g.setColor(Color.white);
	        }
	        else if (greenIndex4 == i) {
	        	g.setColor(GREEN4);
	        	g.fillRect(i*Configurations.BAR_WIDTH, Configurations.BAR_LOWEST - nums[i], Configurations.BAR_WIDTH, nums[i]);
	        	g.setColor(Color.white);
	        }
	        else if (redIndex == i ) {
	        	g.setColor(RED);
	        	g.fillRect(i*Configurations.BAR_WIDTH, Configurations.BAR_LOWEST - nums[i], Configurations.BAR_WIDTH, nums[i]);
	        	g.setColor(Color.white);
	        }
	        else if (redIndex1 == i ) {
	        	g.setColor(RED1);
	        	g.fillRect(i*Configurations.BAR_WIDTH, Configurations.BAR_LOWEST - nums[i], Configurations.BAR_WIDTH, nums[i]);
	        	g.setColor(Color.white);
	        }
	        else if (redIndex2 == i ) {
	        	g.setColor(RED2);
	        	g.fillRect(i*Configurations.BAR_WIDTH, Configurations.BAR_LOWEST - nums[i], Configurations.BAR_WIDTH, nums[i]);
	        	g.setColor(Color.white);
	        }
	        else if (redIndex3 == i ) {
	        	g.setColor(RED3);
	        	g.fillRect(i*Configurations.BAR_WIDTH, Configurations.BAR_LOWEST - nums[i], Configurations.BAR_WIDTH, nums[i]);
	        	g.setColor(Color.white);
	        }
	        else if (redIndex4 == i ) {
	        	g.setColor(RED4);
	        	g.fillRect(i*Configurations.BAR_WIDTH, Configurations.BAR_LOWEST - nums[i], Configurations.BAR_WIDTH, nums[i]);
	        	g.setColor(Color.white);
	        }
	        else if (yellowIndex == i) {
	        	g.setColor(YELLOW);
	        	g.fillRect(i*Configurations.BAR_WIDTH, Configurations.BAR_LOWEST - nums[i], Configurations.BAR_WIDTH, nums[i]);
	        	g.setColor(Color.white);
	        }
	        else if (yellowIndex1 == i) {
	        	g.setColor(YELLOW1);
	        	g.fillRect(i*Configurations.BAR_WIDTH, Configurations.BAR_LOWEST - nums[i], Configurations.BAR_WIDTH, nums[i]);
	        	g.setColor(Color.white);
	        }
	        else if (yellowIndex2 == i) {
	        	g.setColor(YELLOW2);
	        	g.fillRect(i*Configurations.BAR_WIDTH, Configurations.BAR_LOWEST - nums[i], Configurations.BAR_WIDTH, nums[i]);
	        	g.setColor(Color.white);
	        }
	        else if (yellowIndex3 == i) {
	        	g.setColor(YELLOW3);
	        	g.fillRect(i*Configurations.BAR_WIDTH, Configurations.BAR_LOWEST - nums[i], Configurations.BAR_WIDTH, nums[i]);
	        	g.setColor(Color.white);
	        }
	        else if (yellowIndex4 == i) {
	        	g.setColor(YELLOW4);
	        	g.fillRect(i*Configurations.BAR_WIDTH, Configurations.BAR_LOWEST - nums[i], Configurations.BAR_WIDTH, nums[i]);
	        	g.setColor(Color.white);
	        }
	        else if (aboveThisLav <= i) {
	        	g.setColor(LAVENDER);
	        	g.fillRect(i*Configurations.BAR_WIDTH, Configurations.BAR_LOWEST - nums[i], Configurations.BAR_WIDTH, nums[i]);
	        	g.setColor(Color.white);
	        }
	        else if (i <= belowThisLav) {
	        	g.setColor(LAVENDER);
	        	g.fillRect(i*Configurations.BAR_WIDTH, Configurations.BAR_LOWEST - nums[i], Configurations.BAR_WIDTH, nums[i]);
	        	g.setColor(Color.white);
	        }
        }
    }
	
	// Function to generate simple, stylish buttons.
	private static JButton createSimpleButton(String text) {
		  JButton button = new JButton(text);
		  button.setForeground(Color.BLACK);
		  button.setBackground(Color.WHITE);
		  Border line = new LineBorder(Color.BLACK);
		  Border margin = new EmptyBorder(5, 15, 5, 15);
		  Border compound = new CompoundBorder(line, margin);
		  button.setBorder(compound);
		  return button;
		}
	
	// Function to update visibility of buttons
	public static void updateSortButtons() {
		// If they are visible turn them off
		if (bubble.isVisible()) {
			shuffle.setVisible(false);
			bubble.setVisible(false);
			merge.setVisible(false);
			insertion.setVisible(false);
			quick.setVisible(false);
			shell.setVisible(false);
			heap.setVisible(false);
		}
		// If not turn them on
		else {
			shuffle.setVisible(true);
			bubble.setVisible(true);
			merge.setVisible(true);
			insertion.setVisible(true);
			quick.setVisible(true);
			shell.setVisible(true);
			heap.setVisible(true);
		}
	}
	
	// Function to update visibility of buttons
		public static void updateArrayButtons() {
			// If they are visible turn them off
			if (sorted.isVisible()) {
				sorted.setVisible(false);
				shuffle.setVisible(false);
				reversed.setVisible(false);
				partially.setVisible(false);
			}
			// If not turn them on
			else {
				sorted.setVisible(true);
				shuffle.setVisible(true);
				reversed.setVisible(true);
				partially.setVisible(true);
			}
		}
}
