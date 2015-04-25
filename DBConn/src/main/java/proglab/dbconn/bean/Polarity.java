package proglab.dbconn.bean;

public enum Polarity {
	POSITIVE("positive"), NEGATIVE("negative"), NEUTRAL("neutral");

	private final String text;

	Polarity(final String text) {
		this.text = text;
	}

	public String getText() {
		return this.text;
	}

	public static Polarity fromString(final String text) {
		if (text != null) {
			for (Polarity p : Polarity.values()) {
				if (text.equalsIgnoreCase(p.text)) {
					return p;
				}
			}
		}
		return null;
	}
}
