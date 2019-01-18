package gov.nih.nlm.iru;

import gov.nih.nlm.ceb.util.CResourceBundle;

/**
 * <p>Title: IruResourceBundle</p>
 * <p>Description: This class provides runtime resource values from a bundle.
 * @author Mike Bopf 
 */
public class IruResourceBundle extends CResourceBundle
{
  /** The sole instance of App-related resource bundle */
  private static IruResourceBundle INSTANCE_ = new IruResourceBundle();

  /** The name of this resource file */
  private static final String IRU_RESOURCE_FILE = "iru";

  /** The input directory path */
  private static final String INPUT_DIRECTORY_RESOURCE = "inputDirectory";

  /** Output directory */
  private static final String OUTPUT_DIRECTORY_RESOURCE = "outputDirectory";;

  /** The prefix for the image URL */
  private static final String IMAGE_URL_PREFIX_RESOURCE = "imageUrlPrefix";

  /** The dataset name */
  private static final String DATASET_NAME_RESOURCE = "datasetName";

  /** The image type */
  private static final String IMAGE_TYPE_RESOURCE = "imageType";
  
  /** The compression type */
  private static final String COMPRESS_TYPE_RESOURCE = "compressType";
  
  /** The compression ratio */
  private static final String COMPRESS_RATIO_RESOURCE = "compressRatio";
  
  /** The region of interest flag */
  private static final String ROI_FLAG_RESOURCE = "roiFlag";
  
  /** The subdirectory flag */
  private static final String SUBDIRECTORY_FLAG_RESOURCE = "subdirectoryFlag";
  
  /** The input filter */
  private static final String INPUT_FILTER_RESOURCE = "inputFilter";
  
  /** The input extension flag */
  private static final String INPUT_EXTENSION_FLAG_RESOURCE = "inputExtensionFlag";
  
  /** The input mode resource */
  private static final String INPUT_MODE_RESOURCE = "inputMode";
  
  /** The copy mode resource */
  private static final String COPY_MODE_RESOURCE = "copyMode";
  
  /** The database mode resource */
  private static final String DB_MODE_RESOURCE = "dbMode";
  
  /** The comment resource */
  private static final String COMMENT_RESOURCE = "comment";
  
  /** 
   * Default constructor for this class which simply calls parent.
   * The visibility is private so that we don't let anyone else create it. 
   */
  private IruResourceBundle() 
  {
    super(IRU_RESOURCE_FILE);
  }

  /**
   * Get the sole instance of IruResourceBundle.
   * @return the only instance of IruResourceBundle in the system
   */
  static public IruResourceBundle getINSTANCE_() 
  {
    return INSTANCE_;
  }

  /** 
   * Get the inputDirectory containing files to process 
   * @return a String representing the input directory
   */
  public String getInputDirectory() 
  {
    return getStringResource(INPUT_DIRECTORY_RESOURCE);
  }

  /** 
   * Get the outputDirectory to copy files
   * @return a String representing the output directory
   */
  public String getOutputDirectory() 
  {
    return getStringResource(OUTPUT_DIRECTORY_RESOURCE);
  }

  /** 
   * Get the prefix for the image URL 
   * @return a String representing the initial part of the image URL
   */
  public String getImageUrlPrefix() 
  {
    return getStringResource(IMAGE_URL_PREFIX_RESOURCE);
  }

  /** 
   * Get the dataset name 
   * @return a String representing the dataset
   */
  public String getDatasetName() 
  {
    return getStringResource(DATASET_NAME_RESOURCE);
  }

  /** 
   * Get the image type 
   * @return a String representing the imageType
   */
  public String getImageType() 
  {
    return getStringResource(IMAGE_TYPE_RESOURCE);
  }

  /** 
   * Get the compression type 
   * @return a String representing the compression type
   */
  public String getCompressType() 
  {
    return getStringResource(COMPRESS_TYPE_RESOURCE);
  }

  /** 
   * Get the compression ratio 
   * @return a String representing the compression ratio
   */
  public String getCompressRatio() 
  {
    return getStringResource(COMPRESS_RATIO_RESOURCE);
  }

  /** 
   * Get the region of interest flag
   * @return a boolean representing the region of interest flag
   */
  public boolean getRoiFlag() 
  {
    return getBooleanResource(ROI_FLAG_RESOURCE);
  }

  /** 
   * Get the flag for whether input subdirectories should be read recursively 
   * @return true if subdirectories should be checked
   */
  public boolean getSubdirectoryFlag() 
  {
    return getBooleanResource(SUBDIRECTORY_FLAG_RESOURCE);
  }

  /** 
   * Get the input filter, if it exists. If value is 'null', no filter is used.
   * Depending on the value of the 'inputExtensionFlag', the field is either
   * interpretted as an extension or a regular expression.
   * @return a String representing the input filter to be used.
   */
  public String getInputFilter() 
  {
    return getStringResource(INPUT_FILTER_RESOURCE);
  }

  /** 
   * Get the flag controlling whether the input filter represents filename 
   * extension or a general regular expression.
   * @return true if the input filter represents a filename extension
   */
  public boolean getInputExtensionFlag() 
  {
    return getBooleanResource(INPUT_EXTENSION_FLAG_RESOURCE);
  }

  /**
   * Get the mode for determining input files (ALL, WILDCARD, REGEX, etc.)
   * @return String version of the InputMode
   */
  public String getInputMode() 
  {
    return getStringResource(INPUT_MODE_RESOURCE);
  }

  /**
   * Get the mode for copying (NONE, OVERWRITE, etc.)
   * @return String version of the CopyMode
   */
  public String getCopyMode() 
  {
    return getStringResource(COPY_MODE_RESOURCE);
  }

  /**
   * Get the mode for writing to the database (CHECK, OVERWRITE, etc.)
   * @return String version of the DbMode
   */
  public String getDbMode() 
  {
    return getStringResource(DB_MODE_RESOURCE);
  }

  /**
   * Get the comment resource
   * @return String representation of a comment
   */
  public String getComment()
  {
    return getStringResource(COMMENT_RESOURCE);
  }
}
