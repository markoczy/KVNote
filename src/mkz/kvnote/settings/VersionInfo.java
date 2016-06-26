/**
 * File: KVNote::VersionInfo.java
 *
 * @author Aleistar Mark�czy
 * 
 */
package mkz.kvnote.settings;

/**
 * The Class VersionInfo.
 */
public class VersionInfo
{
	/** The member release. */
	private static int mRelease = 0;
	
	/** The member subrelease. */
	private static int mSubrelease = 2;
	
	/** The member build. */
	private static int mBuild = 5;
	
	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public static String getVersion()
	{
		return mRelease+"."+mSubrelease+"."+mBuild;
	}
	
	/**
	 * Gets the release.
	 *
	 * @return the release
	 */
	public static int getRelease()
	{
		return mRelease;
	}
	
	/**
	 * Gets the sub releases.
	 *
	 * @return the sub releases
	 */
	public static int getSubReleases()
	{
		return mSubrelease;
	}
	
	/**
	 * Gets the build.
	 *
	 * @return the build
	 */
	public static int getBuild()
	{
		return mBuild;
	}
	
}
