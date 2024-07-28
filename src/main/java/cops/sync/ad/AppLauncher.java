package cops.sync.ad;

import io.quarkus.runtime.Quarkus;

public class AppLauncher
{
	public static void main(String... args)
	{
		Quarkus.run(App2.class, args);
	}
}
