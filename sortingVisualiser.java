package sortingVisualiserPackage;


import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;
import javax.swing.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;


public class sortingVisualiser {
	
	private JFrame frame;
	int[] nums = new int[Configurations.ARRAY_SIZE];

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					sortingVisualiser window = new sortingVisualiser();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	// Randomised array
	public static void shuffle (int[] array) {
		Random rand = new Random();
				
				for (int i = 0; i < array.length; i++) {
					int randomIndexToSwap = rand.nextInt(array.length);
					int temp = array[randomIndexToSwap];
					array[randomIndexToSwap] = array[i];
					array[i] = temp;
				}
	}
	
	// Sorted array
	public static void sortedArray(int[] array) {
		for (int i=0; i<array.length; i++)
			array[i] = i + 1;
	}
	
	// Reverse sorted array
	public static void reversedArray(int[] array) {
		for (int i=0; i<array.length; i++)
			array[i] = array.length - i;
	}
	
	// Partially sorted array, using a variant of the fischer - yates algorithm
	public static void partiallySortedArray(int[] array) {
		sortedArray(array);
		for (int i = 0; i < array.length - 1; i++) {
			int upperBound = Math.min(array.length - 1, i + 10) - i;
			int random = ThreadLocalRandom.current().nextInt(0, upperBound + 1);
			swap(array, i, i + random);
		}
	}
	
	private static void swap(int[] n, int a, int b) {
		int temp = n[a];
		n[a] = n[b];
		n[b] = temp;
	}
	/**
	 * Create the application.
	 */
	public sortingVisualiser() {
		frame = new JFrame();
		frame.setBounds(100, 0, Configurations.PANEL_WIDTH , Configurations.PANEL_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
		
		for (int i=0; i<Configurations.ARRAY_SIZE; i++)
			nums[i]=i + 1;
		shuffle(nums);
		
		SortingPanel panel = new SortingPanel(nums);
		frame.add(panel);
	}
}

