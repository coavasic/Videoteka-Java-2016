package korisnik;

public enum Aktivnost {
	AKTIVAN, // 1
	NIJE_AKTIVAN; // 2

	public static Aktivnost fromInt(int a) {
		switch (a) {
		case 1:
			return AKTIVAN;
		default:
			return NIJE_AKTIVAN;
		}
	}

	public static int toInt(Aktivnost aktivnost) {
		switch (aktivnost) {
		case AKTIVAN:
			return 1;
		default:
			return 2;
		}
	}
}
