import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BankAccount
{
    private double balance;

    public BankAccount(double initialBalance)
    {
        balance = initialBalance;
    }

    public double getBalance()
    {
        return balance;
    }

    public void deposit(double amount)
    {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount)
    {
        if (amount > 0 && amount <= balance)
        {
            balance -= amount;
            return true;
        }
        return false;
    }
}

class ATM extends JFrame
{
    private BankAccount bankAccount;
    private JTextField amountField;
    private JTextArea messageArea;

    public ATM(BankAccount account)
    {
        bankAccount = account;
        setTitle("ATM Machine");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(164, 221, 237));
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0,45,98));
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel amountLabel = new JLabel("Enter Amount:");
        amountLabel.setForeground(Color.WHITE);
        amountField = new JTextField();

        Font labelFont = new Font("Times New Roman", Font.PLAIN, 20); 
        amountLabel.setFont(labelFont);
        amountField.setFont(labelFont);

        JButton checkBalanceButton = new JButton("Check Balance");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        messageArea = new JTextArea();

        Font textAreaFont = new Font("Times New Roman", Font.PLAIN, 16); 
        messageArea.setFont(textAreaFont);

        Font buttonFont = new Font("Times New Roman", Font.BOLD, 16); 
        checkBalanceButton.setFont(buttonFont);
        depositButton.setFont(buttonFont);
        withdrawButton.setFont(buttonFont);

        checkBalanceButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                messageArea.setText("Current balance: $" + String.format("%.2f", bankAccount.getBalance()));
            }
        });

        depositButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                double amount = Double.parseDouble(amountField.getText());
                bankAccount.deposit(amount);
                messageArea.setText("Deposit successful. New balance: $" + String.format("%.2f", bankAccount.getBalance()));
            }
        });

        withdrawButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                double amount = Double.parseDouble(amountField.getText());
                if (bankAccount.withdraw(amount))
                {
                    messageArea.setText("Withdrawal successful. New balance: $" + String.format("%.2f", bankAccount.getBalance()));
                }
                else
                {
                    messageArea.setText("Invalid withdrawal amount or insufficient balance.");
                }
            }
        });

        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(checkBalanceButton);
        panel.add(depositButton);
        panel.add(withdrawButton);

        JScrollPane scrollPane = new JScrollPane(messageArea);
        panel.add(scrollPane);

        add(panel);
        setVisible(true);
    }
}

class Main
{
    public static void main(String[] args)
    {
        double initialBalance = 1000.0;
        BankAccount userAccount = new BankAccount(initialBalance);

        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                new ATM(userAccount);
            }
        });
    }
}
