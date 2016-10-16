package example.performance;

import org.joda.time.Instant;

import example.InstanceExecution;

public class TestObligationInstance {

	private final InstanceExecution instance;

	public TestObligationInstance(InstanceExecution instance) {
		this.instance = instance;
	}

	public Instant getDueDate() {
		return instance.getExecutionDatetime();
	}
}
