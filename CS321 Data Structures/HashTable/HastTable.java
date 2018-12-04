import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class HashTable {
	private int m;
	private int numKeys; // current number of keys in the arra
	private boolean isAdded;
	private HashObject[] hTable;
	private int n;
	private String type;
	private String probeT;
	private double lFactor;
	private int numDup;
	private int numProbe;
	private double avgNumProbe;
	private int numElements;
	private String str;
	

	int index;

	public HashTable(double loadFactor, int probeType) {
		if (probeType == 1) {
			probeT = " Linear Hashing";
		} else {
			probeT = " Double Hashing";
		}

		lFactor = loadFactor;
		Prime p = new Prime(95500, 96000);
		int[] arrayP = p.getPrime(95500, 96000);
		m = arrayP[1];

		n = (int) Math.ceil((m * loadFactor));
		hTable = new HashObject[m];
		numElements = 0;

		numKeys = 0;
	}

	public void assType(String type) {
		this.type = type;
	}

	public int getNumInputs() {
		return n;
	}

	public int getTableSize() {
		return m;
	}

	public boolean isDone() {

		return numKeys == n;
	}

	public void Insert(HashObject hO, int probeType) {
		numProbe = 1;
		isAdded = false;
		numElements++;
		index = genH1(hO);
		int jump = 0;
		boolean jumped = false;

		while (isAdded == false) {
			if (index > m-1) {
				index -= m - 1;
				//index = 0;
			}
			if (isEmpty(index)) {
				hTable[index] = hO;
				hTable[index].setProbeCount(numProbe);
				numKeys++;
				isAdded = true;
			} else {
				if (hTable[index].equals(hO)) {
					hTable[index].addDuplicate();
					isAdded = true;
				} else {
					if (probeType == 1) {
						index++;
					} else if (probeType == 2) {
						if (jumped == false) {
							jump = genH2(hO);
							jumped = true;
						}
						index += jump;
						index = index % m;
					}

					numProbe++;
				}

			}

		}

	}

	public int genH1(HashObject O) {

		int h1 = (O.getKey() % m);
		if (h1 < -1 * m) {
			System.out.println(h1);
		}
		if (h1 < 0) {
			h1 += m;
		}

		return h1;
	}

	public int genH2(HashObject O) {
		int h2 = (O.getKey() % (m - 2)) + 1;

		if (h2 < -1 * m) {
			System.out.println(h2);
		}
		if (h2 < 0) {
			h2 += m - 2;
		}
		return h2;
	}

	public boolean isEmpty(int index) {
		return hTable[index] == null;
	}

	public boolean isDuplicate(HashObject O, int index) {
		return hTable[index].equals(O);
	}

	public void getTable() throws FileNotFoundException {
		PrintWriter pWriter = new PrintWriter("linear-dump");
		for (int i = 0; i < hTable.length; i++) {
			if (hTable[i] != null) {
				pWriter.println("table[" + i + "]" + hTable[i].getObject() + hTable[i].getNumDup() + " "
						+ hTable[i].getProbeCount());
			}
			
			
		}
		pWriter.close();
		

	}

	
	public String toString() {

		numDup = 0;
		numProbe = 0;
		avgNumProbe = 0;
		for (int i = 0; i < hTable.length; i++) {
			if (hTable[i] != null) {

				numDup += hTable[i].getNumDup();
				numProbe += hTable[i].getProbeCount();
			}
		}

		avgNumProbe = numProbe / (double) n;
		String str = "";

		str += "\n" + "Using" + probeT;
		str += "\n" + "Input " + numElements + " elements" + ", " + "of which " + numDup + " are duplicates";
		str += "\n" + "load factor = " + lFactor + ", Avg. no. of probes " + avgNumProbe;
		return str;
	}

	public String getInputStats() {
		String str = "";

		str += "A good table size is found: " + getTableSize();
		str += "\n" + "Data source type: " + type;
		return str;
	}

}
