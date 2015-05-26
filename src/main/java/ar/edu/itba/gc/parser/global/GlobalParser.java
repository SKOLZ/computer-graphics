package ar.edu.itba.gc.parser.global;

import java.util.Scanner;

import ar.edu.itba.gc.camera.Camera;
import ar.edu.itba.gc.world.World;

public class GlobalParser {

	private World world;
	private LookAt lookAt = null;

	private LookAtParser lookAtParser = LookAtParser.instance();
	private CameraParser cameraParser = CameraParser.instance();
	private FilmParser filmParser = FilmParser.instance();

	public void parse(String s, World world) {
		this.world = world;

		Scanner scanner = new Scanner(s);
		parse(scanner);
		scanner.close();
	}

	private void parse(Scanner scanner) {
		while (scanner.hasNext()) {
			String[] line = scanner.nextLine().trim().split(" ");
			switch (line[0]) {
			case "Camera":
				if (this.lookAt == null)
					throw new IllegalStateException(
							"LookAt should be specified before Camera");
				Camera camera = cameraParser.parse(scanner, "perspective",
						lookAt);
				camera.computeUVW();
				world.camera = camera;
				break;
			case "Film":
				Film film = filmParser.parse(scanner);
				world.vp.setGamma(film.getGamma());
				world.vp.setHorizontalRes(film.getHres());
				world.vp.setVerticalRes(film.getVres());
				break;
			case "LookAt":
				if (this.lookAt != null)
					throw new IllegalStateException("LookAt already setted");
				lookAt = lookAtParser.parse(line);
				break;
			default:
				break;
			}
		}
	}

}
