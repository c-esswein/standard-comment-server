import CommentScore.CommentScore;

public class Main {

	public static void main(String[] args) {
		String goodComment = "Das ist ein zufälliger Kommentar der richtig geschrieben ist und einen hohen Quality score haben sollte";
		String badComment = "dieser komment isch ein schlechta kommi scheiße dreck";
		String testComment = "Ich würde jeden der hier postet und sich als ich kenn mich aus benennt, einladen mit Griechen zu sprechen. Unterhalten Sie sich mit Griechen (jungen, alten). Die werden Ihnen sagen wie es lief mit der vorherigen Regierung. Jede Woche eine neue Steuer auf irgendetwas, Arbeitslosenentgelt auf € 350,- gekürzt (max. Zeit 3 Monate), Mindestlohn auf € 350.- gekürzt, usw. usf. Ich möchte Sie eindringlichst ersuchen mit normalen Griechen zu reden und fragen zu stellen. Dann kommen Sie neu auf die Welt und brauchen nicht mehr auf die Griechen einzu.....  Danke.";
		
		CommentScore score = new CommentScore(100, 50, 80, 20);
		
		String newString = testComment.replaceAll("[^a-zA-ZäöüÄÜÖß ]","");
		System.out.println(newString);
		System.out.println(score.rateComment(newString) + "%");

	}
}
