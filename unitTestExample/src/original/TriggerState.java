package original;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum TriggerState  {

	ACTIVE(1, "active"),
	INACTIVE(2, "inactive"),
	COMPLETED(3, "completed");

	public static final List<TriggerState> ALL_STATES = Collections.unmodifiableList(Arrays.asList(ACTIVE, INACTIVE, COMPLETED));

	private int code;
	private String internalName;

	TriggerState(int code, String internalName) {
		this.code = code;
		this.internalName = internalName;
	}

}
