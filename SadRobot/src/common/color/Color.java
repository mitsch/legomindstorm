package common.color;

public enum Color {
	
	UNDEFINED(-1,0,-1), 
	ABYSS(27,28,29), // Orginal von Thomas: 28
	BLACK(20,24,32), //  Orginal von Thomas: 26
	SILVER(45,46,47), //  Orginal von Thomas: 46
	BROWN_DARK(36,37,38), // Orginal von Thomas: 37
	BROWN_LIGHT(35,36,37), 
	BEIGE(19,20,21),
	
	//FARBFELD
	
	 WHITE(100,100,100),
	 YELLOW(100,100,100),
	 RED(100,100,100),
	 BLUE(100,100,100),
	 GREEN(100,100,100);
	 //BLACK(100,100,100);
	
	
	private int min;
	private int avg;
	private int max;
	
	Color(int min,int avg, int max) {
	        this.min = min;
	        this.avg = avg;
	        this.max = max;
	}

	public final int min() { return this.min; }
	public final int avg() { return this.avg; }

	public final int max() { return this.max; }
	
	public final boolean isComprised(int value){
		return ((this.min <= value) &&(this.max >= value));
	}
	
}
