/* =======================================================================================
 * Confidential Information - Limited distribution to authorized persons only.
 *
 * This software is protected as an unpublished work under the U.S.
 * copyright act of 1976.
 *
 * Copyright SciQuest, Inc., 2002. All Rights Reserved.
 *
 * ============================ CVS Header - Do Not Modify ===============================
 * $Source: /home/cvs/sciquest/library/java/com/sciquest/library/general/StringUtils.java,v $
 * $Revision: 2.122 $
 * $Date: 2016/09/27 19:54:57 $
 *
 * ======================== End of CVS Header - Do Not Modify ============================
 */
package original;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

/**
 * Collection of String manipulation functions that can replace oft-repeated code.
 * Ideally, functions should be static and final so the compiler will inline them.
 *
 * @author Ronnie Angerer
 */
public class StringUtils {

	/** A blank string. Useful in JSP tag calls so that Lomboz does not complain about quotes inside attribute values */
	public static final String BLANK_STRING = "";
	public static final String SPACE = " ";
	public static final String COMMA = ",";
	public static final String PERIOD = ".";
	public static final String VERTICAL_BAR = "|";
	public static final String DASH_WITH_SPACES = " - ";
	public static final String POUND="#";
	public static final String PERCENT_SIGN = "%";

	private StringUtils() {
		// prevent instantiation
	}

	/**
	 * If the input string is a zero length string, returns null, otherwise
	 * the input string is trimmed and returned.  Input string can be null.
	 *
	 * @return java.lang.String
	 * @param inString any String object, it may be null
	 */
	public static final String blankToNull(String inString) {
		if (inString == null) {
			return null;
		}
		String trimmedString = inString.trim();
		if (trimmedString.length() == 0) {
			return null;
		}

		return trimmedString;
	}

	/**
	 * Trims whitespace from the front and end of strings, safe to use for null strings.
	 * @return java.lang.String
	 * @param inString any String object, it may be null
	 */
	public static final String safeTrim(String inString) {

		if (inString == null) {
			return null;
		}

		return inString.trim();
	}

	/**
	 * If the input string is null, returns zero length string, otherwise
	 * the input string is trimmed and returned.
	 *
	 * @return java.lang.String
	 * @param inString any String object, it may be null
	 */
	public static final String nullToBlank(String inString) {
		if (inString == null) {
			return BLANK_STRING;
		}

		return inString.trim();
	}


	/**
	 * If the input Integer is null, returns a zero length string, otherwise
	 * returns a simple string representation of the Integer using String.valueOf
	 *
	 * @param inInteger any Integer object, it may be null
	 * @return java.lang.String
	 */
	public static final String nullToBlank(Integer inInteger) {
		if (inInteger == null) {
			return BLANK_STRING;
		}
		return String.valueOf(inInteger);
	}

	/**
	 * If the input Long is null, returns a zero length string, otherwise
	 * returns a simple string representation of the Long using String.valueOf
	 *
	 * @param inLong any Long object, it may be null
	 * @return java.lang.String
	 */
	public static final String nullToBlank(Long inLong) {
		if (inLong == null) {
			return BLANK_STRING;
		}
		return String.valueOf(inLong);
	}

	/**
	 * If the input Double is null, returns a zero length string, otherwise
	 * returns a simple string representation of the Double using String.valueOf
	 *
	 * @param inDouble any Double object, it may be null
	 * @return java.lang.String
	 */
	public static final String nullToBlank(Double inDouble) {
		if (inDouble == null) {
			return BLANK_STRING;
		}
		return String.valueOf(inDouble);
	}

	/**
	 * If the input BigDecimal is null, returns a zero length string, otherwise
	 * returns a simple string representation of the BigDecimal using String.valueOf
	 *
	 * @param inDecimal any BigDecimal object, it may be null
	 * @return java.lang.String
	 */
	public static final String nullToBlank(BigDecimal inDecimal) {
		if (inDecimal == null) {
			return BLANK_STRING;
		}
		return String.valueOf(inDecimal);
	}

	/**
	 * If the input Boolean is null, returns a zero length string, otherwise
	 * returns a simple string representation of the Boolean using String.valueOf
	 *
	 * @param inBoolean any Boolean object, it may be null
	 * @return java.lang.String
	 */
	public static final String nullToBlank(Boolean inBoolean) {
		if (inBoolean == null) {
			return BLANK_STRING;
		}
		return String.valueOf(inBoolean);
	}

	/**
	 * Null-safe equals comparison for Strings that allow either or both
	 * to be null.
	 * @return boolean true if the two are equal (both null or equal)
	 * @param str1 java.lang.String
	 * @param str2 java.lang.String
	 */
	public static final boolean safeEquals(String str1, String str2) {
		if (str1 == null) {
			return str1 == str2;
		} else {
			return str1.equals(str2);
		}
	}

	/**
	 * Null-safe equals comparison for Strings that allow either or both
	 * to be null.
	 * @return boolean true if the two are NOT equal
	 * @param str1 java.lang.String
	 * @param str2 java.lang.String
	 */
	public static final boolean safeNotEquals(String str1, String str2) {
		return !safeEquals(str1, str2);
	}

	/**
	 * Either or both strings may be null, and null is equal to blank for
	 * the purposes of this comparison.
	 *
	 * @return true if strings are equal or if they are both either null or blank
	 */
	public static final boolean safeEqualsNullEqualsBlank(String str1, String str2) {
		return safeEquals(blankToNull(str1),blankToNull(str2));
	}

	/**
	 * Null-safe ignore case equals comparison for Strings that allow either or both
	 * to be null.
	 * @return boolean true if the two are equal (both null or equal)
	 * @param str1 java.lang.String
	 * @param str2 java.lang.String
	 */
	public static final boolean safeEqualsIgnoreCase(String str1, String str2) {
		if (str1 == null) {
			return str1 == str2;
		} else {
			return str1.equalsIgnoreCase(str2);
		}
	}

	/**
	 * Returns the upper case version of the string or null if the string is null
	 *
	 * @return String upper case version of the input (or null if input is null)
	 * @param str1 java.lang.String
	 */
	public static final String safeToUpperCase(String str1) {
		if (str1 == null) {
			return null;
		} else {
			return str1.toUpperCase();
		}
	}

	/**
	 * Returns the lower case version of the string or null if the string is null
	 *
	 * @return String lower case version of the input (or null if input is null)
	 * @param str1 java.lang.String
	 */
	public static final String safeToLowerCase(String str1) {
		if (str1 == null) {
			return null;
		} else {
			return str1.toLowerCase();
		}
	}


	/**
	 * Checks if the string is too long.  Returns true if the string is longer
	 * than the maxLength parameter
	 * <br>
	 * If the string is null or empty, returns false because the string
	 * is not too long
	 *
	 * @param str1
	 * @param maxLength
	 */
	public static final boolean isTooLong (String str1, int maxLength) {
		//If the string is null return false because the string is not too long
		if (isEmpty(str1)) {
			return false;
		}

		//If the string is longer than maxLength return true
		return str1.length() > maxLength;
	}

	/**
	 * <p>
	 * Double escapes any found backslashes in a string.
	 * </p>
	 *
	 * @param val
	 *            java.lang.String
	 * @return java.lang.String
	 */
	public static String escapeJSBackslash(String val) {
		if (val == null) {
			return null;
		}

		return val.replace("\\", "\\\\");
	}

	/**
	 * <p>
	 * Double escapes a single quote for use in a JavaScript string, e.g., "Sam's" becomes "Sam\\'s".
	 * </p>
	 * <p>
	 * When the escaped String is output (in a JSP, for example), Java strips off one of the backslashes
	 * so that the resulting String conforms to the Javascript escaped single-quote:   "Sam\'s".
	 * </p>
	 *
	 * @param val java.lang.String
	 * @return java.lang.String
	 */
	public static String escapeJSQuote(String val) {
		// PERFORMANCE:   may need some performance improvment
		if (val == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder(val);

		int j = 0;
		int start = 0;
		String stringToEvaluate = val;
		while ((j = stringToEvaluate.indexOf('\'', start)) != -1) {
			sb.insert(j, "\\");
			stringToEvaluate = sb.toString();
			start = j + 2;
		}

		return sb.toString();
	}

	/**
	 *
	 * Double escapes all instances of character encode from String val.
	 *
	 * @param val
	 * @param encode
	 * @return java.lang.String
	 */
	public static String escapeCharacter(String val, char encode) {
		if (val == null) {
			return null;
		}

		return val.replace(String.valueOf(encode), "\\" + encode);
	}


	/**
	 * Takes a string and prepares the string to be concatenated with other
	 * strings to produce a string that can be re-parsed into a list using the
	 * CSV parser.
	 *
	 * 1. If the specified delimiter exists in the string, put double quotes
	 * around the string 2. If there are double quotes within the string, double
	 * them ( '"' becomes '""' ) and make sure there are double quotes around
	 * the whole string
	 *
	 */
	private static final String prepareCSVFieldForConcatenation(String val,
			char delimiter) {
		return prepareCSVFieldForConcatenation(val, Character.toString(delimiter));
	}

	private static final String prepareCSVFieldForConcatenation(String val, String delimiter) {
		if (val == null) {
			return BLANK_STRING;
		}

		StringBuilder sb = new StringBuilder(val);

		int j = 0;
		int start = 0;
		String stringToEvaluate = val;
		while ((j = stringToEvaluate.indexOf('"', start)) != -1) {
			sb.insert(j, "\"");
			stringToEvaluate = sb.toString();
			start = j + 2;
		}

		if (sb.indexOf(delimiter) >= 0 || sb.indexOf("\"") >= 0) {
			return "\"" + sb.toString() + "\"";
		} else {
			return sb.toString();
		}
	}

	/**
	 * Takes a lsit of strings and prepares and concatenates the strings into a string that can be re-parsed into a list
	 * using the CSV parser.
	 *
	 * 1. If the specified delimeter exists in the string, puts double quotes around the string
	 * 2. If there are double quotes within the string, doubles them ( '"' becomes '""' ) and make sure there are double quotes around the whole string
	 *
	 */
	public static final String convertListToCSVString(Collection<String> strings, char delimiter) {
		if (strings == null || strings.isEmpty()) {
			return null;
		}
		StringBuilder sb = new StringBuilder(10 * strings.size());
		boolean first = true;
		for (String text: strings) {
			if (first) {
				first = false;
			}
			else {
				sb.append(delimiter);
			}

			sb.append(prepareCSVFieldForConcatenation(text, delimiter));

		}
		return sb.toString();
	}

	public static final String convertListToCSVString(Collection<String> strings, String delimiter) {
		if (strings == null || strings.isEmpty()) {
			return null;
		}
		StringBuilder sb = new StringBuilder(10 * strings.size());
		boolean first = true;
		for (String text: strings) {
			if (first) {
				first = false;
			}
			else {
				sb.append(delimiter);
			}

			sb.append(prepareCSVFieldForConcatenation(text, delimiter));

		}
		return sb.toString();
	}

	/**
	 * Takes a collection of objects and a delimitter and creates a String using the toString that is implemented
	 * on the object that makes up the collection.
	 * @param collection
	 * @param delimiter
	 * @return A string representation of the collection using the toString method.
	 */
	public static final String convertCollectionToString(Collection<? extends Object> collection, String delimiter) {
		if (CollectionUtils.isEmpty(collection)) {
			return null;
		}
		StringBuilder sb = new StringBuilder(10*collection.size());
		boolean first = true;
		for (Object obj: collection) {
			if (first) {
				first = false;
			} else {
				sb.append(delimiter);
			}
			sb.append(obj.toString());
		}
		return sb.toString();
	}




	/**
	 * This method is to check a hex color value.  Returns true if <b>val</b> starts with #, is 7 characters long, and has no white space.
	 * Creation date: (8/29/2001 9:15:53 AM)
	 * @return boolean returns true if the String value meets the if requirements, returns false otherwise
	 * @param val value to validate.
	 * TODO: Move to HtmlUtils
	 */
	public static boolean isValidHexColor(String val) {
		if (val.charAt(0) == '#'
			&& val.length() == 7
			&& val.indexOf(' ') == -1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks whether String is empty or null.
	 * Creation date: (10/22/2002 9:15:53 AM)
	 *
	 * NOTE:  strings made up of white space will return true
	 *
	 * @param val value to validate.
	 * @return boolean returns true if the String is empty or null
	 */
	public static boolean isEmpty(String val) {
		if (val == null) {
			return true;
		} else if (val.trim().length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks whether all of the passed in string values are empty
	 *
	 * @param values
	 * @return true, if all of the passed string is empty. False, otherwise.
	 */
	public static boolean isAllEmpty(String... values) {
		for (String val : values) {
			if (isNotEmpty(val)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks whether String not empty and not null
	 * @see #isEmpty(String)
	 *
	 * @param val value to validate.
	 * @return boolean returns true if the String is NOT empty and NOT null
	 */
	public static boolean isNotEmpty(String val) {
		return !isEmpty(val);
	}

	private static final char MULTI_CHAR_SEARCH_CONSTANT	= '*';
	private static final char UNI_CHAR_SEARCH_CONSTANT 		= '?';
	private static final char DB_MULTI_CHAR_SEARCH_CONSTANT	= '%';
	private static final char DB_UNI_CHAR_SEARCH_CONSTANT	= '_';

	/**
	 * Method processWildCards.
	 * Process the wildcards for the given search string.
	 * If it contains
	 * @param str - String which will be processed for wild card.
	 * @return String - the processed string with wildcard formatted.
	 */
	public static String processWildCards(String str) {
		return processWildCards(str,false,false);
	}
	/**
	 * Method processWildCards.
	 * Process the wildcards for the given search string.
	 * If it contains
	 * @param str 	- String which will be processed for wild card.
	 * @param performAction - <True>wild card will be appended at both ends to
	 * 							facilitate contains search.
	 * 					  	- <False>wild card will not be appended at all,
	 * 							only the existing will be replaced.
	 * @return String - the processed string with wildcard formatted.
	 */

	public static String processWildCards(String str,boolean performAction) {
			return processWildCards(str,performAction,false);
	}

	/**
	 * Method processWildCards.
	 * Process the wildcards for the given search string.
	 * If it contains
	 * @param searchStr - String which will be processed for wild card.
	 * @param performAction - <True> wild card will be appended at both ends to
	 * 						facilitate contains search.
	 * 					  	- False  wild card will not be appended at all,
	 * 							only the existing will be replaced. This will
	 * 							override 'startswith' option.
	 * @param startswith - <True> wild card will be appended only at the end.
	 * 						facilitate Starts with search.
	 * 					  - <False>  wild card will be appended at both ends to
	 * 						facilitate contains search.
	 * @return String - the processed string with wildcard formatted.
	 */
	public static String  processWildCards(String searchStr, boolean performAction, boolean startswith) {
		// TODO: Make some sense out of all these processWildCard methods
		// This will never happen because the search string is already checked.
		if (searchStr == null) {
			return null;
		}

		String processedSearchStr = searchStr;

		//replace * with equivalent Multicharacter search in DB
		processedSearchStr = processedSearchStr.replace(MULTI_CHAR_SEARCH_CONSTANT,DB_MULTI_CHAR_SEARCH_CONSTANT);
		//replace ? with equivalent Singlecharacter search in DB
		processedSearchStr = processedSearchStr.replace(UNI_CHAR_SEARCH_CONSTANT,DB_UNI_CHAR_SEARCH_CONSTANT);

		// If no Appending has to be performed simply return the string with the wildcard changed.
		if(!performAction) {
			return processedSearchStr;
		}
		if((processedSearchStr.indexOf(DB_MULTI_CHAR_SEARCH_CONSTANT) >= 0) ||(processedSearchStr.indexOf(DB_UNI_CHAR_SEARCH_CONSTANT) >= 0)){
			// Contains wild card so no process return the string
			return processedSearchStr;
		}
		else{
			if (startswith){
				// Process for Starts with search.
				return (processedSearchStr + DB_MULTI_CHAR_SEARCH_CONSTANT);
			}
			else{
				// Process for contains with search.
				return (DB_MULTI_CHAR_SEARCH_CONSTANT + processedSearchStr + DB_MULTI_CHAR_SEARCH_CONSTANT);
			}
		}
	}

	/**
	 * Removes one or more characters from a string.
	 * Creation date: (11/01/2001 10:37:29 AM)
	 * @return java.lang.String
	 * @param inputString java.lang.String The string to be processed.
	 * @param charsToRemove char[] A char array of the chars to be removed.
	 */
	public static String removeChars(String inputString, char... charsToRemove) {
		if (inputString == null
			|| inputString.length() <= 0
			|| charsToRemove == null
			|| charsToRemove.length <= 0) {

			return inputString;
		}

		StringBuilder sb = new StringBuilder(inputString.length());
outerLoop:
		for (int i = 0; i < inputString.length(); i++) {
			char ic = inputString.charAt(i);
			for (char c : charsToRemove) {
				if (ic == c) {
					continue outerLoop;
				}
			}
			sb.append(ic);
		}
		if (sb.length() < inputString.length()) {
			return sb.toString();
		}
		else {	// Nothing changed - no reason to create unnecessary garbage
			return inputString;
		}
	}

	/**
	 * Spreads out a string to have spaces between each character for headers.
	 * @param inputString The string to be processed.
	 * @return String
	 * @deprecated For now it simply does nothing. Should be deleted once all references have been removed.
	 */
	@Deprecated
	public static String spaceHeader(
		String inputString) {
		return inputString;
	}


	/**
	 * Method replaceString. This method will find and replace the string with
	 * the given string.
	 * @param text String
	 * @param find String
	 * @param with String
	 * @return String
	 *
	 */
	public static String replaceString(String text, String find, String with) {
		String resultString = text;

		int index = resultString.indexOf(find);
		if (index >= 0) {
			resultString = resultString.substring(0, index) + with +
				resultString.substring(index + find.length());
		}

		return resultString;
	}

	/**
	 * Method replaceString. This method will find and replace the string with
	 * the given string.
	 * @param text String
	 * @param find String
	 * @param with String
	 * @return String
	 *
	 */
	public static String replaceAllString(String text, String find, String with) {
		String resultString = text;

		int index = resultString.indexOf(find);
		while (index >= 0) {
			resultString = resultString.substring(0, index) + with +
				resultString.substring(index + find.length());

			index = resultString.indexOf(find);
		}

		return resultString;
	}

	/**
	 * Method replaceInString. This method will find strings bound between the two
	 * strings and then replaces the bounded string with the given string.
	 * @param text String
	 * @param find1 String
	 * @param find2 String
	 * @param with String
	 * @return String
	 *
	 */
	public static String replaceInString(String text, String find1, String find2, String with) {
		String resultString = text;

		int start = resultString.indexOf(find1);
		int end = resultString.indexOf(find2);

		if ((start >= 0) && (end >= 0)) {
			resultString = resultString.substring(0, start) + find1 + with + find2 +
				resultString.substring(end + find2.length());
		}

		return resultString;
	}

	/**
	 * Pads the string with the given character. Determines the number of
	 * characters that is less than the maxchar in the inString and pads
	 * the remaining characters to the string. It will cut if the
	 * length of the character exceeds the given max no of character.
	 *
	 * @param inString - The string to be padded.
	 * @param maxchar - Maximum no of characters to be in the string
	 * @param padchar - The characters to be padded
	 * @param lpad as boolean
	 * @return String
	 *
	 */
	public static String stringPad(
		String inString,
		int maxchar,
		char padchar,
		boolean lpad) {

		return stringPad(inString,maxchar, String.valueOf(padchar),lpad);
	}

	/**
	* Pad the string with the given string
	*
	* @param inString - The string to be padded.
	* @param maxchar - Maximum no of characters to be in the string
	* @param padString - The string to be padded. Here the length of the string is considered as 1. Ex. &nbsp;.
	* @param lpad as boolean
	* @return String
	*
	*/
	public static String stringPad(String inString, int maxchar, String padString, boolean lpad) {

		StringBuilder outString = null;

		if (inString != null) {
			if (inString.length() < maxchar) {

				int nLength = inString.length();
				outString = new StringBuilder(inString);

				for (int nIndex = nLength; nIndex < maxchar; nIndex++) {
					if (lpad) {
						outString.insert(0,padString);
					} else {
						outString.append(padString);
					}
				}
			}else {
				outString = new StringBuilder(inString.substring(0,maxchar));
			}
		}else{
			outString = new StringBuilder();
		}

		return outString.toString();
	}

	/**
	 * Returns true if the string contains '*' or '?'.
	 * @param value		String
	 */

	public static boolean containsWildCards(String value) {
		return
			value.indexOf('*') >= 0
			|| value.indexOf('?') >= 0;
	}
	/**
	 * Deserializes a String written by {@link #writeStringAsBytes(String, DataOutput)}.
	 * Not suitable for unicode or double-byte character strings.
	 */
	public static String readStringAsBytes(DataInput input) throws IOException {
		int len = input.readInt();
		if (len < 0) {
			return null;
		}
		else if (len == 0) {
			return BLANK_STRING;
		}
		else {
			byte[] b = new byte[len];
			input.readFully(b);
			return new String(b);
		}
	}	/**
	 * Serializes a String as a bunch of bytes. The first byte has the length.
	 * Not suitable for unicode or double-byte character strings.
	 */
	public static void writeStringAsBytes(String s, DataOutput output) throws IOException {
		if (s == null) {
			output.writeInt(-1);
		}
		else if (s.length() == 0) {
			output.writeInt(0);
		}
		else {
			output.writeInt(s.length());
			output.writeBytes(s);
		}
	}
	private static final int WRAP_LENGTH = 20;
	public static String wrapString(String wrapstring, int wrapLen )	{

		int wrapLength = wrapLen;

		if(wrapstring == null ) {
			return null;
		}

		if(wrapLength <= 0) {
			wrapLength = WRAP_LENGTH;
		}

		StringBuilder strbf = new StringBuilder();
		char c;

		for( int i=0 ; i < wrapstring.length() ; i++ ){
			c = wrapstring.charAt(i);

			if( wrapLength-- == 0 || c == ' ' ) {
				wrapLength = wrapLen;
				strbf.append('\n');
			}
			strbf.append(c);
		}
		return strbf.toString();

	}


	/**
	 * Returns the length of the trimmed input string. If the input string is null returns 0.
	 *
	 * @return int
	 * @param inString any String object, it may be null
	 */
	public static final int safeLength(String inString) {
		if (inString == null) {
			return 0;
		}

		return inString.trim().length();
	}

	/**
	 * Filters out all non-digit characters
	 */
	public static String getDigitsOnly(String s) {
		StringBuilder digitsOnly = new StringBuilder(s.length());
		char c;
		for (int i = 0; i < s.length(); i++) {
			c = s.charAt(i);
			if (Character.isDigit(c)) {
				digitsOnly.append(c);
			}
		}
		return digitsOnly.toString();
	}

	/**
	 * Perfomes case-insensitive comparison of two strings. Nulls are sorted to the end.
	 *
	 * @param name1
	 * @param name2
	 */
	public static int safeCompareIgnoreCase(String name1, String name2) {
		if (name1 == name2) {
			return 0;
		}
		if (name2 == null) {
			return -1;
		}
		if (name1 == null) {
			return 1;
		}
		return name1.compareToIgnoreCase(name2);
	}

	public static boolean safeContains(String name1, String name2) {
		boolean contains = false;
		if(name1 != null && name2 != null) {
			contains = name1.contains(name2);
		}
		return contains;
	}

	/**
	 * Performs case-insensitive 'contains' of two strings.
	 *
	 * @param name1
	 * @param name2
	 */
	public static boolean safeContainsIgnoreCase(String name1, String name2) {
		return org.apache.commons.lang.StringUtils.containsIgnoreCase(name1, name2);
	}

	/**
	 * Return contents of array separated by separatorChar and a trailing space.  If array only contains
	 * one element returns the first element without a trailing separator or space.
	 *
	 * @param values
	 * @param separatorChar
	 */
	public static String formatArray(String[] values, String separatorChar) {
		return formatArray(values, separatorChar, null);
	}

	/**
	 * Return contents of array separated by separatorChar and enclosed by an encapsulating character.
	 * If array only contains one element returns the first element without a trailing separator or space.
	 *
	 * @param values
	 * @param separatorChar  The character used to separate array elements (e.g.  ,)
	 * @param encapsulatingChar  The character used to encapsulate each array element (e.g. ').  If null, no encapsulation.
	 */
	public static String formatArray(String[] values, String separatorChar, String encapsulatingChar) {
		if (values == null || values.length == 0) {
			return BLANK_STRING;
		}

		String encapsulator = encapsulatingChar == null ? BLANK_STRING : encapsulatingChar;

		StringBuilder buff = new StringBuilder();
		for(int i = 0; i <= values.length-1; i++){
			if (buff.length() > 0) {
				buff.append(separatorChar);
				buff.append(' ');
			}
			buff.append(encapsulator);
			buff.append(values[i]);
			buff.append(encapsulator);
		}

		return buff.toString();
	}

	/**
	 * Returns a substring of the given string with the given start and end
	 * positions:
	 * - If the string is null, returns null.
	 * - If the length of the string is less than endPos, returns the substring
	 *   from startPos to the end of the string.
	 * - All other rules from String.substring() apply (throws
	 *   IndexOutOfBoundsException if other conditions not met).
	 *
	 * @param inString
	 * @param startPos
	 * @param endPos
	 */
	public static String safeSubstring(String inString, int startPos, int endPos) {

		if (inString == null) {
			return null;
		}

		if (inString.length() < endPos) {
			return inString.substring(startPos, inString.length());
		}

		return inString.substring(startPos, endPos);
	}

	public static String[] safeSplit(String inString, String splitOn) {
		if(null == inString) {
			return null;
		} else {
			return inString.split(splitOn);
		}
	}

    /**
     * Returns true if value is a member of array (case insensitive).
     * Consider an alternative solution if array is large!
     *
     * @param value
     * @param array
     */
    public static boolean isMemberIgnoreCase(String value, String[] array){
        boolean match = false;
        for (String element : array) {
            if(StringUtils.safeEqualsIgnoreCase(value, element)){
                match = true;
            	break;
            }
        }
        return match;
    }

    /**
     * Returns true if the value contains any values in the array
     * Consider an alternative solution if array is large!
     *
     * @param value
     * @param array
     */
    public static boolean containsAnyInArray(String value, String[] array){
        if (ArrayUtils.isEmpty(array)) {
        	return false;
        }
        for (String arraySearch : array){
            if (org.apache.commons.lang.StringUtils.containsIgnoreCase(value, arraySearch)) {
            	return true;
            }
        }
        return false;
    }

    /**
     * Truncates the string to the specified length and adds ... if truncated
     * @param value
     * @param maxlength
     */
    public static String getTruncatedStringWithEllipsis(String value, int maxlength) {
    	if (value == null) {
    		return null;
    	}
    	if (value.length() <= maxlength) {
    		return value;
    	}
    	String ellipsis = "...";
    	return trimToUTF8ByteLength(value, maxlength-ellipsis.length()) + ellipsis;
    }

    /**
     * Checks a string for length using the number of bytes it will use when
     * stored into the database (which stores using UTF8 encoding)
     *
     * @param inString
     * @param maximumBytes
     * @return length to trim the string, or the length of the string if it is shorter than the trim value
     */
	public static int getTrimLengthForUTF8Bytes(String inString, int maximumBytes) {

		if (inString == null) {
			throw new InvalidParameterException("Cannot pass a null to this method");
		}

		//Count of the bytes that will be used when the string converts to UTF8
		int byteCount = 0;

		//Length to trim to
		//Default to the length of the string in case the string is shorter than
		//the maximumBytes
		int trimLength = inString.length();

		//Iterate the string, counting the bytes that will be used
		for (int i = 0; i < inString.length(); i++) {


			char tempChar = inString.charAt(i);
			int code = tempChar;

			//Depending on the range, count the character as 1-4 bytes
			//Source: http://en.wikipedia.org/wiki/UTF-8#Description

			if (code <= 0x7F) {
				byteCount+=1;
			} else if (code >= 0x80 && code <= 0x7FF) {
				byteCount+=2;
			} else if (code >= 0x800 && code <= 0xFFFF) {
				byteCount+=3;
			} else if (code >= 0x10000 && code <= 0x10FFFF) {
				byteCount+=4;
			}

			if (byteCount == maximumBytes) {
				trimLength = i+1;
				break;
			}
			if (byteCount > maximumBytes) {
				trimLength = i;
				break;
			}
		}

		return trimLength;

	}

    /**
     * Trims a string to a byte length representing the number of bytes
     * it will use when converted to UTF8
     *
     * @param inString
     * @param maximumBytes
     * @return trimmed string, or original string if no trim is needed
     */
	public static String trimToUTF8ByteLength(String inString, int maximumBytes) {

		if (inString == null) {
			throw new InvalidParameterException("Cannot pass a null to this method");
		}

		int inStringLength = inString.length();

		int trimLength = getTrimLengthForUTF8Bytes(inString, maximumBytes);

		//If the length to trim to is less than the inStringLength a trim is needed
		if (trimLength < inStringLength) {
			return inString.substring(0, trimLength);
		} else {
			return inString;
		}

	}


	/**
	 * Checks if the string is too long comparing the number of characters in the string to the number of bytes
	 * those characters will use when saved to a UTF8 database
	 *
	 * Returns true if the UTF8 bytes are longer than the maxLength provided
	 * <br>
	 * If the string is null or empty, returns false because the string
	 * is not too long
	 *
	 * @param str1
	 * @param maxLength
	 */
	public static final boolean isTooLongInUTF8Bytes (String str1, int maxLength) {
		//If the string is null return false because the string is not too long
		if (isEmpty(str1)) {
			return false;
		}

		//If the string is longer than maxLength return true
		return str1.length() > getTrimLengthForUTF8Bytes(str1, maxLength);
	}

	/**
	 * Splits the string on space or comma
	 */
	public static final List<String> splitOnWhitespaceOrComma(String source) {
		return splitRemoveEmtpyTokens(source, " |,");
	}

	/**
	 * Splits the string on space or comma
	 */
	public static final List<String> splitRemoveEmtpyTokens(String source, String pattern) {
		if (isEmpty(source)) {
			return Collections.emptyList();
		}
		String tokens[] = source.split(pattern);
		List<String> tokensList = new ArrayList<>(tokens.length);
		for (String token : tokens) {
			// Avoid extra spaces
			if (!isEmpty(token)) {
				tokensList.add(token);
			}
		}
		return tokensList;
	}

	/**
	 * Bring multiple strings together.
	 *
	 * Usage:
	 *
	 * join(",",innerPlanets); // returns "Mercury,Venus,Earth,Mars"
	 * join(",",innerPlanets,outerPlanets); // returns "Mercury,Venus,Earth,Mars,Jupiter,Saturn,Uranus,Neptune"
	 *
	 * @param stuff A collection of things to join together in a single string
	 * @param between The delimiter to put between items
	 * @return the requested string.  Null if stuff is null, blank if stuff is
	 * empty.
	 */
	public static final String join(String between,Iterable<?>... stuff) {
		return join(between, null, stuff);
	}

	public static final String join(String between, String prepend, Iterable<?>... stuff) {
		if (stuff==null || stuff.length==0) {
			return null;
		}

		StringBuilder sb=new StringBuilder();
		boolean allNull=true;

		for (Iterable<?> thing:stuff) {
			if (thing==null) {
				continue;
 			} else {
 				allNull=false;
 			}

			Iterator<?> iterator=thing.iterator();

			if (!iterator.hasNext()) {
				continue;
			}

			String first=(prepend != null ? prepend : "") + String.valueOf(iterator.next());

			sb.append(first);

			while (iterator.hasNext()) {
				sb.append(between);
				sb.append(String.valueOf((prepend != null ? prepend : "") + iterator.next()));
			}
		}

		if (allNull) {
			return null;
		}

		return sb.toString();
	}

	/**
	 * Bring multiple strings together.
	 *
	 * Usage:
	 *
	 * join(",",innerPlanets); // returns "Mercury,Venus,Earth,Mars"
	 *
	 * @param stuff A collection of things to join together in a single string
	 * @param between The delimiter to put between items
	 * @return the requested string.  Null if stuff is null, blank if stuff is
	 * empty.
	 */
	public static final String join(String between, Object... stuff) {
		return join(between,Arrays.asList(stuff));
	}

    /**
     * Splits the string into an array of longs around matches of the given <a
     * href="../util/regex/Pattern.html#sum">regular expression</a>.
     *
     * @param  regex
     *         the delimiting regular expression
     *
     * @return  the array of longs computed by splitting this string
     *          around matches of the given regular expression
     *
     * @throws  PatternSyntaxException
     *          if the regular expression's syntax is invalid
	 * @throws  NumberFormatException
	 *          if one of the strings it encounters cannot be parsed into a long
     *
     * @see java.util.regex.Pattern
     */
	public static final long[] split(String string, String regex) throws NumberFormatException {
		if (StringUtils.isEmpty(string)) {
			return new long[] {};
		}

		String[] ids = string.split(regex);
		long[] longValues = new long[ids.length];

		for (int i=0; i<ids.length; i++) {
			longValues[i] = Long.parseLong(ids[i]);
		}
		return longValues;
	}

	/**
	 * Returns the first portion of the hostname without the domain
	 */
	public static final String shortHostname(String longHostName) {
		int dotIndex = longHostName.indexOf('.');
		if (dotIndex > 0) {
			return longHostName.substring(0, dotIndex);
		}
		return longHostName;
	}

	/**
	 * Returns null if the object is null, otherwise returns obj.toString()
	 * @param obj any object
	 * @return a string representation of the object
	 */
	public static final String safeToString(Object obj) {
		if(obj == null) {
			return null;
		} else {
			return obj.toString();
		}
	}
}