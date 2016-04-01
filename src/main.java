import javax.swing.JFileChooser;
import javax.swing.SwingWorker;
import java.io.File;

public class Main extends javax.swing.JFrame
{
	class SearchWorker extends SwingWorker<String, Object>
	{
		private Updater informer;
		private Analyzer aa;
		private String text = "";

		public SearchWorker(Updater informer, Analyzer aa)
		{
			this.informer = informer;
			this.aa = aa;
		}

		public String doInBackground()
		{
			try
			{
				text = aa.findDuplicates(informer);
			}
			catch (Exception e)
			{
				text = "Invalid path!";
				labelStatus.setText("Error!");
			}
			return text;
		}

		public void done()
		{
			if (text.equals(""))
			{
				text = "No duplicates found!"
			}
			areaResult.setText(text);
			setSearchingStatus(false);
		}
	} 

	public class Updater
	{
		private int current = 1;

		private void reset()
		{
			this.current = 1;
		}

		public void setCurrent(String path)
		{
			fieldCurrent.setText(path);
			labelCurrent.setText(this.current + "");
			++this.current;
		}

		public void setFinished()
		{
			labelStatus.setText("Finished!");
		}

		public void setPreparing()
		{
			labelOf.setText("");
			labelCurrent.setText("");
			labelTotal.setText("");
			labelStatus.setText("Preparing...");
		}

		public void setScanning()
		{
			labelof.setText("of");
			labelStatus.setText("Scanning...");
		}

		public void setTotal()
		{
			labelTotal.setText(n + "");
		}		
	}

	public static void main(String args[])
	{
		try
		{
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
			{
				if ("Nimbus".equals(info.getName()))
				{
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		}
		catch (ClassNotFoundException ex)
		{
			java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		catch (InstantiationException ex)
		{
			java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		catch (IllegalAccessException ex)
		{
			java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		catch (javax.swing.UnsupportedLookAndFeelException ex)
		{
			java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
	}

	private SearchWorker sw;

	private javax.swing.JButton buttonAbort;

	private javax.swing.JButton buttonFile;

	private javax.swing.JButton buttonSearch;
	
	private javax.swing.JTextField fileCurrent;

	private javax.swing.JTextField fileChooser;

	private javax.swing.JScrollPane jScrollPane1;

	private javax.swing.JLabel labelCurrent;

	private javax.swing.JLabel labelOf;

	private javax.swing.JLabel labelStatus;
	
	private javax.swing.JLabel labelTotal;

	private javax.swing.JTextArea areaResult;

	private javax.swing.JTextArea fieldPath;

	private Updater updater = new Updater();

	public Main()
	{
		initComponents();
		fileChooser.setFileSelectionMode(JFileChoose.DIRECTORIES_ONLY);
		this.setSearchingStatus(false);
	}

	private void buttonAbortActionPerformed(java.awt.event.ActionEvent evt)
	{
		this.sw.cancel(true);
		this.setSearchingStatus(true);
		this.labelStatus.setText("Aborted!");
		this.areaResult.setText("Operation aborted!");
	}
	
	private void buttonFileActionPerformed(java.awt.event.ActionEvent evt)
	{
		this.fileChoose.showOpenDialog(this);
	}

	private void buttonSearchActionPerformed(java.awt.event.ActionEvent evt)
	{
		String path = fieldpath.getText();
		Analyzer aa = Analyzer.getMD5Analyzer(new File(path));
		this.areaResult.setText("");
		setSearchingStatus(true);
		this.fieldCurrent.setText(0 + "");
		this.updater.reset();
		this.sw = new SearchWorker(this.updater, aa);
		sw.execute();
	}

	private void fileChooserActionPerformed(java.awt.event.ActionEvent evt)
	{
		File f = this.fileChooser.getSelectedFile();
		if (f != null)
		{
			this.fieldPath.setText(f.getAbsolutePath());
		}
	}

	private void initComponents()
	{
		fileChooser = new javax.swing.JFileChooser();
		fieldPath = new javax.swing.JTextField();
		buttonFile = new javax.swing.JButton();
		buttonSearch = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		areaResult = new javax.swing.JTextArea();
		labelStatus = new javax.swing.JLabel();
		labelCurrent = new javax.swing.JLabel();
		labelOf = new javax.swing.JLabel();
		labelTotal = new javax.swing.JLabel();
		fieldCurrent = new javax.swing.JTextField();
		buttonAbort =new javax.swing.JButton();

		fileChooser.setApproveButtonText("Select");
		fileChooser.setCurrentDirectory(new java.io.File("$HOME"));
		fileChooser.setDialogTitle("Choose a directory...");
		fileChooser.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				fileChooserActionPerformed(evt);
			}
		});

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Duplicate File Finder \u00a9 Victoria Mengqi LIU");
		setLocationByPlatform(true);
		setResizable(false);

		fieldPath.setFont(new java.awt.Font("Serif", 0, 18));

		buttonFile.setText("...");
		buttonFile.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				buttonFileActionPerformed(evt);
			}
		});

		buttonSearch.setFont(new java.awt.Font("Serif", 1, 24);
		buttonSearch.setText("Search");
		buttonSearch.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				buttonSearchActionPerformed(evt);
			}
		});

		areaResult.setEditable(false);
		areaResult.setColumns(20);
		areaResult.setFont(new java.awt.Font("Serif", 0, 14));
		areaResult.setRows(5);
		areaResult.setOpaque(false);
		jScrollPane1.setViewportView(areaResult);

		labelStatus.setFont(new java.awt.Font("Segoe UI", 0, 14));
		labelStatus.setText("Ready");
		labelStatus.setOpaque(true);
		
		labelCurrent.setFont(new java.awt.Font("Segoe UI", 0, 14));
		labelCurrent.setOpaque(true);

		labelOf.setFont(new java.awt.Font("Segoe UI", 0, 14));
		labelOf.setOpaque(true);

		labelTotal.setFont(new java.awt.Font("Segoe UI", 0, 14));
		labelTotal.setOpaque(true);

		fieldCurrent.setEditable(false);
		fieldCurrent.setFont(new java.awt.Font("Segoe UI", 0, 14));

		buttonAbort.setFont(new java.awt.Font("Calibri", 2, 24));
		buttonAbort.setForeground(new java.awt.color(255, 0, 0));
		buttonAbort.setText("Abort");
		buttonAbort.setEnable(false);
		buttonAbort.addActionListener(new java.awt.event.ActionListener()
		{
			public void actionPerformed(java.awt.event.ActionEvent evt)
			{
				buttonAbortActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout( getContentPane() );
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
			.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addContainerGap().addGroup(layout
					.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
					.addComponent(jScrollPane1).addGroup(layout
						.createSequentialGroup().addGroup(layout
							.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
							.addComponent(fieldCurrent)
							.addComponent(fieldPath, 
								javax.swing.GroupLayout.Alignment.TRAILING)
							.addGroup(Layout.createSequentialGroup()
								.addComponent(labelStatus, 
									javax.swing.GroupLayout.PREFERED_SIZE, 91, 
									javax.swing.GroupLayout.PREFERED_SIZE)
								.addPreferredGap(
									javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(labelCurrent, 
									javax.swing.GroupLayout.PREFERED_SIZE, 60, 
									javax.swing.GroupLayout.PREFERED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(labelOf)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
								.addComponent(labelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 376, Short.MAX_VALUE)
							)
						)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(buttonFile, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addGap(26, 26, 26)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
							.addComponent(buttonSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(buttonAbort, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						)
					)
				)
				.addContainerGap()
			)
		);

		layout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
			new java.awt.Component[] {buttonAbort, buttonSearch});

		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addContainerGap(17, Short.MAX_VALUE)
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
					.addComponent(fieldPath, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
					.addCompponent(buttonFile)
					.addComponent(buttonSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)

	}

	private void setSearchingStatus(boolean b)
	{

	}
	
}
