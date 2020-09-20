package sortingVisualiserPackage;

// Simple class to hold all the constant values of the project
public class Configurations {
	public static final int BAR_WIDTH = 2;
	public static final int ARRAY_SIZE = 512;
	public static final int PANEL_WIDTH = ARRAY_SIZE * BAR_WIDTH + 15;
	public static final int PANEL_HEIGHT = ARRAY_SIZE * 3 /2 ;
	public static final int BUTTON_WIDTH = 80;
	public static final int BUTTON_HEIGHT = 30;
	public static final int BAR_LOWEST = PANEL_HEIGHT - 37;
	public static final int START_BTN_VOFFSET = 70;
	public static final int START_BTN_HOFFSET = 150;
	public static final int SHUFFLE_BTN_VOFFSET = 70;
	public static final int SHUFFLE_BTN_HOFFSET = 30;
	public static int DELAY = 1;
	// there are 128 available notes in midi, so divide array size by 128 to get the note gap.
	public static final int NOTE_GAP = ARRAY_SIZE / 128;
	public static final int NOTE_SPEED = 200;
}
