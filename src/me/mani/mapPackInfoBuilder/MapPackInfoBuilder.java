package me.mani.mapPackInfoBuilder;

import java.awt.Component;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import me.mani.molecraft.ArenaMapPack.ArenaMapInfo;

import org.bukkit.configuration.file.YamlConfiguration;

public class MapPackInfoBuilder extends JFrame {

	private static final long serialVersionUID = 1L;

	private YamlConfiguration config;
	private List<ArenaMapInfo> arenaMapInfos = new ArrayList<>();
	
	public MapPackInfoBuilder() {
		setSize(600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationByPlatform(true);
		setLayout(new FlowLayout(FlowLayout.CENTER, 600, 10));
		
		config = new YamlConfiguration();
		
		JTextField displayNameField = new JTextField("Display name", 40);
		JTextField builderNameField = new JTextField("Builder name", 40);
		JTextField displayLoreField = new JTextField("Display lore", 40);
		JTextField mapInfoPathField = new JTextField("Map info path", 40);
		JButton addButton = new JButton("Add");
		JToggleButton themeToggle = new JToggleButton("Toggle theme");
		JTextArea outputArea = new JTextArea(20, 40);
		
		for (Component c : Arrays.asList(displayNameField, builderNameField, displayLoreField, mapInfoPathField, addButton, themeToggle, outputArea))
			getContentPane().add(c);
		
		addButton.addActionListener((ev) -> add(displayNameField.getText(), builderNameField.getText(), displayLoreField.getText(), mapInfoPathField.getText(), outputArea));
		themeToggle.addActionListener((ev) -> {
			if (themeToggle.isSelected())
				try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
			else
				try { UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName()); } catch (Exception e) {}
			SwingUtilities.updateComponentTreeUI(this);
		});
		
		setVisible(true);
	}
	
	private void add(String displayName, String builderName, String displayLore, String mapInfoPath, JTextArea outputArea) {
		arenaMapInfos.add(new ArenaMapInfo(displayName, builderName, displayLore, mapInfoPath));
		config.set("arenaMaps", arenaMapInfos);
		outputArea.setText(config.saveToString());
	}

	public static void main(String[] args) {
		new MapPackInfoBuilder();
	}
	
}
