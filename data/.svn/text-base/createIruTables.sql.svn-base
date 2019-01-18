--
--  File containing table creations for the IRU
-- 
--  12/07/2005 M.Bopf 	  - Initial cut

--
-- Table structure for table describing entities
--
-- NOTE: The cebImageId should eventually be the PrimaryKey, but since we
--       haven't determined it's form quite yet, this will be deferred.
CREATE TABLE imageData (
  cebImageId varchar(64),
  origFilename varchar(64) NOT NULL,
  parentKeyId varchar(64),
  dataset varchar(64),
  imageType varchar(32),
  compressType varchar(64),
  compressRatio varchar(16),
  ingestDate datetime DEFAULT NULL,
  roiFlag boolean,
  size bigint(12),
  md5 varchar(32),
  url varchar(256),
  comment varchar(256),
  PRIMARY KEY (dataset, origFilename)
);

