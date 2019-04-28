package view;

import service.WordManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class Game {
    private String wordToGuess;
    private int tries;
    private char[] guessesChars;
    private final Font generalFont = new Font("Roboto", Font.PLAIN, 16);
    private final Map<Character, JLabel> alphabetChars;
    private final Hangman hangman;

    public Game() {
        this.alphabetChars = new LinkedHashMap<>();
        alphabetChars.put('A', new JLabel("A"));
        alphabetChars.put('B', new JLabel("B"));
        alphabetChars.put('C', new JLabel("C"));
        alphabetChars.put('D', new JLabel("D"));
        alphabetChars.put('E', new JLabel("E"));
        alphabetChars.put('F', new JLabel("F"));
        alphabetChars.put('G', new JLabel("G"));
        alphabetChars.put('H', new JLabel("H"));
        alphabetChars.put('I', new JLabel("I"));
        alphabetChars.put('J', new JLabel("J"));
        alphabetChars.put('K', new JLabel("K"));
        alphabetChars.put('L', new JLabel("L"));
        alphabetChars.put('M', new JLabel("M"));
        alphabetChars.put('N', new JLabel("N"));
        alphabetChars.put('O', new JLabel("O"));
        alphabetChars.put('P', new JLabel("P"));
        alphabetChars.put('Q', new JLabel("Q"));
        alphabetChars.put('R', new JLabel("R"));
        alphabetChars.put('S', new JLabel("S"));
        alphabetChars.put('T', new JLabel("T"));
        alphabetChars.put('U', new JLabel("U"));
        alphabetChars.put('V', new JLabel("V"));
        alphabetChars.put('W', new JLabel("W"));
        alphabetChars.put('X', new JLabel("X"));
        alphabetChars.put('Y', new JLabel("Y"));
        alphabetChars.put('Z', new JLabel("Z"));

        this.hangman = new Hangman();
    }

    private void buildGUI() {
        var frame = new JFrame("Galgenmännchen");
        var layout = new GridBagLayout();
        frame.setLayout(layout);
        var constraints = new GridBagConstraints();
        constraints.ipadx = 20;
        constraints.ipady = 20;

        var triesLabel = new JLabel("Tries: " + this.tries);
        var guessLabel = new JLabel("Guess: " + arrayToString(guessesChars));
        var usedLabel = new JLabel("Used Chars: ");
        var textBox = new JTextBox("letter", true);
        textBox.setPreferredSize(new Dimension(150, 25));
        var goButton = new JButton("Go!");
        var wordButton = new JButton("Use your own word!");
        var alphabetPanel = new JPanel();
        alphabetChars.forEach((key, value) -> {
            applyFont(value);
            alphabetPanel.add(value);
        });

        goButton.addActionListener(event -> {
            var input = textBox.getText().toUpperCase();
            if (input.length() > 1) {
                JOptionPane.showMessageDialog(frame, "Input can only be one letter", "Error", JOptionPane.WARNING_MESSAGE);
                textBox.setText("");
                return;
            }
            if (input.length() < 1) {
                JOptionPane.showMessageDialog(frame, "Please enter at least one letter", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!alphabetChars.containsKey(input.charAt(0))) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid character", "You dumb fuck!", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (alphabetChars.get(input.charAt(0)).getForeground() == Color.RED) {
                JOptionPane.showMessageDialog(frame, "Please do not enter the same letter", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            var decrement = replaceChars(input, guessLabel);
            if (decrement) {
                tries--;
                triesLabel.setText("Tries: " + tries);
                hangman.drawNext();
            }

            alphabetChars.get(input.charAt(0)).setForeground(Color.RED);

            if (Arrays.equals(wordToGuess.toCharArray(), guessesChars)) {
                var result = JOptionPane.showConfirmDialog(frame, "You won! New game?", "Success", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    frame.dispose();
                    initNewGame();
                } else {
                    System.exit(0);
                }
                return;
            }
            if (tries == 0) {
                var result = JOptionPane.showConfirmDialog(null, "Would you like to start a new game?", "New view.Game?", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    frame.dispose();
                    initNewGame();
                } else {
                    System.exit(0);
                }
            }
        });
        wordButton.addActionListener(e -> {
            var result = JOptionPane.showInputDialog(frame, "Please enter your own word!", "Selection", JOptionPane.PLAIN_MESSAGE);
            if (result != null && !result.isBlank() && !result.contains(" ")) {
                result = result.substring(0, 1).toUpperCase() + result.substring(1).toLowerCase();
                if (!WordManager.getWords().contains(result)) WordManager.addToWords(result);
                wordToGuess = result.toUpperCase();
                guessesChars = new char[wordToGuess.length()];
                for (int i = 0; i < wordToGuess.length(); i++) guessesChars[i] = '_';
                guessLabel.setText("Guess: " + arrayToString(guessesChars));
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter a valid word", "You dump fuck!", JOptionPane.WARNING_MESSAGE);
            }
        });
        textBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
                    goButton.doClick();
                    textBox.setText("");
                }
            }
        });
        textBox.requestFocus();

        applyFont(triesLabel, guessLabel, textBox, goButton, wordButton, usedLabel);

        constraints.gridx = 0;
        constraints.gridy = 0;
        frame.add(inPanel(triesLabel), constraints);
        constraints.gridy = 1;
        frame.add(inPanel(guessLabel), constraints);
        constraints.gridy = 2;
        frame.add(inPanel(usedLabel), constraints);
        constraints.gridy = 3;
        frame.add(inPanel(textBox), constraints);
        constraints.gridx = 1;
        frame.add(inPanel(goButton), constraints);
        constraints.gridx = 2;
        frame.add(inPanel(wordButton), constraints);
        constraints.gridy = 2;
        constraints.gridx = 1;
        frame.add(alphabetPanel, constraints);
        constraints.gridx = 1;
        constraints.gridy = 0;
        frame.add(hangman, constraints);

        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private boolean replaceChars(String input, JLabel guessLabel) {
        var inputChar = input.charAt(0);
        if (wordToGuess.contains(input)) {
            for (int i = 0; i < guessesChars.length; i++) {
                if (wordToGuess.charAt(i) == inputChar) {
                    guessesChars[i] = inputChar;
                }
            }
            guessLabel.setText("Guess: " + arrayToString(guessesChars));
            return false;
        }
        return true;
    }

    private <C extends JComponent> JPanel inPanel(C c) {
        var panel = new JPanel(new BorderLayout());
        var subPanel = new JPanel();
        subPanel.add(c);
        panel.setPreferredSize(new Dimension(300, 50));
        panel.add(subPanel, BorderLayout.WEST);
        return panel;
    }

    @SafeVarargs
    private <C extends JComponent> void applyFont(C... components) {
        for (C component : components) {
            component.setFont(generalFont);
        }
    }

    private String arrayToString(char[] array) {
        var builder = new StringBuilder();
        for (char character : array)
            builder.append(character).append(" ");
        return builder.toString();
    }

    public void initNewGame() {
        tries = 10;
        wordToGuess = WordManager.getRandomWord(true);
        guessesChars = new char[wordToGuess.length()];
        for (int i = 0; i < wordToGuess.length(); i++) guessesChars[i] = '_';
        alphabetChars.forEach((key, value) -> value.setForeground(Color.BLACK));
        hangman.reset();
        buildGUI();
    }
}
