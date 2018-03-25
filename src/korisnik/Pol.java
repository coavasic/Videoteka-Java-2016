package korisnik;

public enum Pol {
	ŽENSKI, // 1
	MUŠKI; // 2

	public static Pol fromInt(int p) {
		switch (p) {
		case 1:
			return ŽENSKI;
		default:
			return MUŠKI;
		}
	}

	public static int toInt(Pol pol) {
		switch (pol) {
		case ŽENSKI:
			return 1;
		default:
			return 2;
		}
	}
}