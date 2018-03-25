package korisnik;

public enum Pol {
	�ENSKI, // 1
	MU�KI; // 2

	public static Pol fromInt(int p) {
		switch (p) {
		case 1:
			return �ENSKI;
		default:
			return MU�KI;
		}
	}

	public static int toInt(Pol pol) {
		switch (pol) {
		case �ENSKI:
			return 1;
		default:
			return 2;
		}
	}
}