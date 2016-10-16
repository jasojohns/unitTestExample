/* =============================================================================
 * Confidential Information - Limited distribution to authorized persons only.
 *
 * This software is protected as an unpublished work under the U.S.
 * copyright act of 1976.
 *
 * Copyright SciQuest, Inc., 2003. All Rights Reserved.
 *
 * ======================== CVS Header - Do Not Modify =========================
 * $Source: /home/cvs/sciquest/library/java/com/sciquest/library/general/NullsToBottomComparator.java,v $
 * $Revision: 1.2 $
 * $Date: 2015/03/04 03:42:59 $
 * Note........:
 *
 * ===================== End of CVS Header - Do Not Modify =====================
 */
package original;

import java.util.Comparator;

/**
 * Sorts nulls to the bottom
 * @author alexey
 */
public final class NullsToBottomComparator<T> implements Comparator<T> {

	private final Comparator<T> comparator;

	public NullsToBottomComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	@Override
	public int compare(T o1, T o2) {
		if (o1 == o2) {
			return 0;
		}
		if (o1 == null) {
			return 1;
		}
		if (o2 == null) {
			return -1;
		}
		if (comparator == null) {
			return 0;
		}
		return comparator.compare(o1, o2);
	}

	public static final <T extends Comparable<? super T>> int safeCompare(T o1, T o2) {
		if (o1 == o2) {
			return 0;
		}
		if (o1 == null) {
			return 1;
		}
		if (o2 == null) {
			return -1;
		}
		return o1.compareTo(o2);
	}
}
