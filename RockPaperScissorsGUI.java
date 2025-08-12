import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class RockPaperScissorsGUI extends JFrame {
    private static final String[] CHOICES = {"Rock", "Paper", "Scissors"};
    private final Random rand = new Random();

    private JLabel playerChoiceLabel, compChoiceLabel, resultLabel, scoreLabel;
    private int playerScore = 0, compScore = 0;
    private JComboBox<String> difficultyBox;

    public RockPaperScissorsGUI() {
        setTitle("Rock Paper Scissors");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout
        setLayout(new BorderLayout());

        // Top Panel: Difficulty selection
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Difficulty:"));
        difficultyBox = new JComboBox<>(new String[]{"Easy", "Medium", "Hard"});
        topPanel.add(difficultyBox);
        add(topPanel, BorderLayout.NORTH);

        // Center Panel: Choices and Results
        JPanel centerPanel = new JPanel(new GridLayout(5, 1));
        playerChoiceLabel = new JLabel("Your choice: -", SwingConstants.CENTER);
        compChoiceLabel = new JLabel("Computer's choice: -", SwingConstants.CENTER);
        resultLabel = new JLabel("Result: -", SwingConstants.CENTER);
        scoreLabel = new JLabel("Score — You: 0 | Computer: 0", SwingConstants.CENTER);

        centerPanel.add(playerChoiceLabel);
        centerPanel.add(compChoiceLabel);
        centerPanel.add(resultLabel);
        centerPanel.add(scoreLabel);
        add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel: Buttons
        JPanel bottomPanel = new JPanel();
        for (String choice : CHOICES) {
            JButton btn = new JButton(choice);
            btn.addActionListener(new ChoiceListener(choice));
            bottomPanel.add(btn);
        }
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private class ChoiceListener implements ActionListener {
        private final String playerChoice;

        public ChoiceListener(String choice) {
            this.playerChoice = choice;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String compChoice = computerChoice(playerChoice);
            playerChoiceLabel.setText("Your choice: " + playerChoice);
            compChoiceLabel.setText("Computer's choice: " + compChoice);

            int result = decideWinner(playerChoice, compChoice);
            if (result == 1) {
                playerScore++;
                resultLabel.setText("Result: You win!");
            } else if (result == -1) {
                compScore++;
                resultLabel.setText("Result: Computer wins!");
            } else {
                resultLabel.setText("Result: It's a tie!");
            }

            scoreLabel.setText("Score — You: " + playerScore + " | Computer: " + compScore);
        }
    }

    private String computerChoice(String playerChoice) {
        String difficulty = (String) difficultyBox.getSelectedItem();

        // Easy: random
        if ("Easy".equals(difficulty)) {
            return CHOICES[rand.nextInt(3)];
        }

        // Medium & Hard: biased
        double winBias = "Medium".equals(difficulty) ? 0.6 : 0.85;

        if (rand.nextDouble() < winBias) {
            // Pick move that beats player's choice
            switch (playerChoice) {
                case "Rock": return "Paper"; // Paper beats Rock
                case "Paper": return "Scissors"; // Scissors beats Paper
                case "Scissors": return "Rock"; // Rock beats Scissors
            }
        }

        // Otherwise random
        return CHOICES[rand.nextInt(3)];
    }

    private int decideWinner(String player, String comp) {
        if (player.equals(comp)) return 0;
        if ((player.equals("Rock") && comp.equals("Scissors")) ||
            (player.equals("Scissors") && comp.equals("Paper")) ||
            (player.equals("Paper") && comp.equals("Rock"))) {
            return 1;
        }
        return -1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RockPaperScissorsGUI game = new RockPaperScissorsGUI();
            game.setVisible(true);
        });
    }
}
