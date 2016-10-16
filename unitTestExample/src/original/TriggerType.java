package original;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum TriggerType  {

	INTERVAL(1, "interval"),

	CRON(2, "cron"),

	JSON(3, "json");

	public static final List<TriggerType> ALL_TYPES = Collections.unmodifiableList(Arrays.asList(INTERVAL, CRON, JSON));

	private int code;
	private String internalName;

	TriggerType(int code, String internalName) {
		this.code = code;
		this.internalName = internalName;
	}

}
