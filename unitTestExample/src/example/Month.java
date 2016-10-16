/* =======================================================================================
 * Confidential Information - Limited distribution to authorized persons only.
 *
 * This software is protected as an unpublished work under the U.S.
 * copyright act of 1976.
 *
 * Copyright SciQuest, Inc., 2010. All Rights Reserved.
 *
 * ============================ CVS Header - Do Not Modify ===============================
 * $Source: /home/cvs/sciquest/applications/java/com/sciquest/app/siteprofile/general/ui/Month.java,v $
 * $Revision: 1.2 $
 * $Date: 2016/01/12 20:28:32 $
 *
 * ======================== End of CVS Header - Do Not Modify ============================
 */
package example;

public class Month  {

	public static final Month JAN = new Month(1, "Jan");
	public static final Month FEB = new Month(2, "Feb");
	public static final Month MAR = new Month(3, "Mar");
	public static final Month APR = new Month(4, "Apr");
	public static final Month MAY = new Month(5, "May");
	public static final Month JUN = new Month(6, "Jun");
	public static final Month JUL = new Month(7, "Jul");
	public static final Month AUG = new Month(8, "Aug");
	public static final Month SEP = new Month(9, "Sep");
	public static final Month OCT = new Month(10, "Oct");
	public static final Month NOV = new Month(11, "Nov");
	public static final Month DEC = new Month(12, "Dec");

	private final int code;
	private final String abbrev;

	protected Month(int code, String abbrev) {
		this.code = code;
		this.abbrev = abbrev;
	}

	public int getCode() {
		return code;
	}

}