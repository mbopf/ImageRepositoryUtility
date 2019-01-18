select * FROM imageData im1, imageData im2 
         WHERE im1.md5 = im2.md5 AND im1.origFilename <> im2.origFilename
         ORDER BY `im1`.`md5` ASC
