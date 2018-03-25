package korisnik;

public enum TipZaposlenog {
	RADNIK, // 666
	UREDNIK; // 999

	public static TipZaposlenog fromInt(int tip) {
		switch (tip) {
		case 666:
			return RADNIK;
		default:
			return UREDNIK;
		}

	}

	public static int toInt(TipZaposlenog tipZaposlenog) {
		switch (tipZaposlenog) {
		case RADNIK:
			return 666;
		default:
			return 999;
		}
	}

}
