package example.performance;

import java.util.Comparator;

import original.DateUtils;


public class TestObligationInstanceDueDateComparator implements Comparator<TestObligationInstance> {

	public static final TestObligationInstanceDueDateComparator INSTANCE = new TestObligationInstanceDueDateComparator();

	private TestObligationInstanceDueDateComparator() {
		//do nothing
	}

	@Override
	public int compare(TestObligationInstance o1, TestObligationInstance o2) {
		return DateUtils.safeCompare(o1.getDueDate() , o2.getDueDate());
	}
}
