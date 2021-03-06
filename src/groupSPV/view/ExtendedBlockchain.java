package groupSPV.view;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.math.RoundingMode;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

import org.bitcoinj.core.Block;
import org.bitcoinj.core.Transaction;

import groupSPV.controller.ConversionRate;
import groupSPV.controller.Utils;
import groupSPV.model.BigCoin;

/** ExtendedBlockchain is a popup window that is displayed upon clicking
 * the more info button in the BlockchainGUI
*
* @author Frank Fasola
* @author James Donnell
* @author Spencer Escalante
* @author Trevor Silva */
public class ExtendedBlockchain extends JFrame {

	private static final long serialVersionUID = -4831226248088383161L;
	
	/** Full block received from peer. */
	private Block fullBlock;
	/** Transaction JTable. */
	private JTable table;
	/** ScrollPane for Transaction Table. */
	private JScrollPane scrollPane;
	/** JLabel for Block information. */
	private JLabel lblAmount, lblDifficulty, lblNonce, lblMessageSize, lblMerkleRoot, lblWork, lblA, lblDPW, lblN, lblMS, lblMR, lblW;
	
	public ExtendedBlockchain(int blockNumber, Block b) {
		super("Block: " + blockNumber + (Utils.isTestNetwork() ? " (TESTNET)" : ""));
		fullBlock = b;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(new Rectangle(new Dimension(550,525)));
		SpringLayout springLayout = new SpringLayout();
		getContentPane().setLayout(springLayout);
		
		
		/* *************************************
		/	      Setup of the table	   	   /
		/		  with scrollpane			   /
		/* ************************************/
		table = new JTable();
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Transactoin Hash" }) {
			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		});
		scrollPane = new JScrollPane(table);
		
		/* *************************************
		/	      Setup of the JPanel   	   /
		/									   /
		/* ************************************/		
		BigCoin totalCoins = BigCoin.totalTransactions(fullBlock.getTransactions());
		ConversionRate.update(ConversionRate.SupportedCurrency.America);
		String usdString = ConversionRate.convert(totalCoins).setScale(2, RoundingMode.HALF_UP).toPlainString();
		
		lblAmount = new JLabel("Total Amount of Bitcoins: ");
		lblDifficulty = new JLabel("Difficulty of the Proof of Work: ");
		lblNonce = new JLabel("Nonce:");
		lblMessageSize = new JLabel("Message Size:");
		lblMerkleRoot = new JLabel("Merkle Root:");
		lblWork = new JLabel("Work:");
		lblA = new JLabel(totalCoins.getBitcoin().toPlainString() + " BTC ($" + usdString + " USD)");
		lblDPW = new JLabel(String.valueOf(fullBlock.getDifficultyTarget()));
		lblN = new JLabel(String.valueOf(fullBlock.getNonce()));
		lblMS = new JLabel(String.valueOf(fullBlock.getMessageSize()));
		lblMR = new JLabel(String.valueOf(fullBlock.getMerkleRoot().toString()));
		lblW = new JLabel(String.valueOf(fullBlock.getWork())  + " tries");
		
		/* *************************************
		/	      Add Everything to 	   	   /
		/		     contentpane			   /
		/* ************************************/
		getContentPane().add(scrollPane);
		getContentPane().add(lblAmount);
		getContentPane().add(lblDifficulty);
		getContentPane().add(lblNonce);
		getContentPane().add(lblMessageSize);
		getContentPane().add(lblMerkleRoot);
		getContentPane().add(lblWork);
		getContentPane().add(lblA);
		getContentPane().add(lblDPW);
		getContentPane().add(lblN);
		getContentPane().add(lblMS);
		getContentPane().add(lblMR);
		getContentPane().add(lblW);
		
		update();		

		/* *************************************
		/	      Constraint setup		   	   /
		/									   /
		/* ************************************/
		springLayout.putConstraint(SpringLayout.NORTH, scrollPane, 175, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, scrollPane, -10, SpringLayout.SOUTH, getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, scrollPane, -10, SpringLayout.EAST, getContentPane());

		springLayout.putConstraint(SpringLayout.WEST, scrollPane, 0, SpringLayout.WEST, lblAmount);
		springLayout.putConstraint(SpringLayout.NORTH, lblAmount, 34, SpringLayout.NORTH, getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblAmount, 10, SpringLayout.WEST, getContentPane());

		springLayout.putConstraint(SpringLayout.NORTH, lblDifficulty, 6, SpringLayout.SOUTH, lblAmount);
		springLayout.putConstraint(SpringLayout.WEST, lblDifficulty, 0, SpringLayout.WEST, lblAmount);
		
		springLayout.putConstraint(SpringLayout.NORTH, lblNonce, 6, SpringLayout.SOUTH, lblDifficulty);
		springLayout.putConstraint(SpringLayout.WEST, lblNonce, 0, SpringLayout.WEST, lblDifficulty);

		springLayout.putConstraint(SpringLayout.NORTH, lblMessageSize, 6, SpringLayout.SOUTH, lblNonce);
		springLayout.putConstraint(SpringLayout.WEST, lblMessageSize, 0, SpringLayout.WEST, lblAmount);
		
		springLayout.putConstraint(SpringLayout.NORTH, lblMerkleRoot, 6, SpringLayout.SOUTH, lblMessageSize);
		springLayout.putConstraint(SpringLayout.WEST, lblMerkleRoot, 0, SpringLayout.WEST, lblAmount);
		
		springLayout.putConstraint(SpringLayout.NORTH, lblWork, 6, SpringLayout.SOUTH, lblMerkleRoot);
		springLayout.putConstraint(SpringLayout.WEST, lblWork, 0, SpringLayout.WEST, lblAmount);
		
		springLayout.putConstraint(SpringLayout.WEST, lblA, 6, SpringLayout.EAST, lblAmount);
		springLayout.putConstraint(SpringLayout.SOUTH, lblA, 0, SpringLayout.SOUTH, lblAmount);

		springLayout.putConstraint(SpringLayout.WEST, lblDPW, 6, SpringLayout.EAST, lblDifficulty);
		springLayout.putConstraint(SpringLayout.SOUTH, lblDPW, 0, SpringLayout.SOUTH, lblDifficulty);

		springLayout.putConstraint(SpringLayout.NORTH, lblN, 6, SpringLayout.SOUTH, lblDifficulty);
		springLayout.putConstraint(SpringLayout.WEST, lblN, 6, SpringLayout.EAST, lblNonce);

		springLayout.putConstraint(SpringLayout.WEST, lblMS, 6, SpringLayout.EAST, lblMessageSize);
		springLayout.putConstraint(SpringLayout.SOUTH, lblMS, 0, SpringLayout.SOUTH, lblMessageSize);
		
		springLayout.putConstraint(SpringLayout.NORTH, lblMR, 0, SpringLayout.NORTH, lblMerkleRoot);
		springLayout.putConstraint(SpringLayout.WEST, lblMR, 6, SpringLayout.EAST, lblMerkleRoot);
		
		springLayout.putConstraint(SpringLayout.WEST, lblW, 6, SpringLayout.EAST, lblWork);
		springLayout.putConstraint(SpringLayout.SOUTH, lblW, 0, SpringLayout.SOUTH, lblWork);
		
		Utils.setWindowCenterOfScreen(this);
	}
	
	/** Updates the table that contains Transaction Hashes */
	public void update(){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		for (Transaction t : fullBlock.getTransactions()) {
			Object[] row = {t.getHashAsString()};
			model.addRow(row);
		}
	}
}
