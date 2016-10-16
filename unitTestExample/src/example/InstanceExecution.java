package example;

import org.joda.time.Instant;

public class InstanceExecution {

	private final Instant executionDatetime;
	private final int iteration;

	public InstanceExecution(int iteration, Instant execDatetime) {
		this.iteration = iteration;
		this.executionDatetime = execDatetime;
	}

	public Instant getExecutionDatetime() {
		return executionDatetime;
	}

	public int getIteration() {
		return iteration;
	}

}
