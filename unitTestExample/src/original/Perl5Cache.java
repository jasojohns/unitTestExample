/* =============================================================================
 * Confidential Information - Limited distribution to authorized persons only.
 *
 * This software is protected as an unpublished work under the U.S.
 * copyright act of 1976.
 *
 * Copyright SciQuest, Inc., 2003. All Rights Reserved.
 *
 * ======================== CVS Header - Do Not Modify =========================
 * $Source: /home/cvs/sciquest/library/java/com/sciquest/library/general/Perl5Cache.java,v $
 * $Revision: 1.3 $
 * $Date: 2016/01/11 22:50:16 $
 * Note........:
 *
 * ===================== End of CVS Header - Do Not Modify =====================
 */
package original;

import org.apache.oro.text.GenericPatternCache;
import org.apache.oro.text.PatternCacheLRU;
import org.apache.oro.text.perl.Perl5Util;


/**
 * Caches Perl5Util instances and gives them out on demand.
 * Each thread contains one Perl5Util instance. All instances share the same pattern cache.
 *
 * @author alexey
 */
public final class Perl5Cache {

	// Perl5 patterns
	public static final int PERL5_PATTERN_CACHE_SIZE = 200;

	protected GenericPatternCache patternCache;

	/** A copy of perl5 by thread */
	private final ThreadLocal<Perl5Util> perlCache = new ThreadLocal<Perl5Util>() {
        @Override
		protected Perl5Util initialValue() {
            return new Perl5Util(patternCache);
        }
	};

	/**
	 * Creates a new Perl5Cache instance with the specified capacity
	 */
	public Perl5Cache(int capacity) {
		super();
		patternCache = new PatternCacheLRU(capacity);
	}

	/**
	 * Returns a Perl5Util instance.
	 * This instance should be only used by the calling thread and should not be saved anywhere for later use.
	 */
	public Perl5Util getPerl5() {
		return perlCache.get();
	}

}
