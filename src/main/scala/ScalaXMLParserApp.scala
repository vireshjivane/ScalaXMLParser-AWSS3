/**
 * Created by Viresh on 5/26/2015.
 */

import java.util.UUID

object ScalaXMLParserApp extends App {

  println("Staring program... !")

  /* S3 Bucket Creation and Upload Object - Start */
  val S3Client = new ScalaApplicationS3()
  val bucketName = "scalaxmlparser" + UUID.randomUUID();
  val objectKey = "inputXML"
  val filePath = "input.xml"

  S3Client.initializeS3Client()
  S3Client.createS3Bucket(bucketName)
  S3Client.putObjectFromFilePathInBucket(objectKey, bucketName, filePath)
  /* S3 Bucket Creation and Upload Object - End */

  /* Read S3 Object in File - Start */
  val file = S3Client.getS3ObjectContentsInFile(bucketName, objectKey, "inputLoadedFromS3.xml")
  /* Read S3 Object in File - End */

  /* Parser Start */
  val parser = new ScalaXMLParser

  parser.initializeParser()

  val document = parser.getDocumentFromXMLFile(file)
  val elements = parser.parseDocument(document)
  /* Parser End */

  /* Writing output to S3 Object - Start */
  val outputObjectKey = "ParsedOutput" + UUID.randomUUID();

  parser.S3ObjectWriter(S3Client, bucketName, outputObjectKey, elements)
  /* Writing output to S3 Object - End */

  //S3Client.deleteObject(bucketName, objectKey)

  println("Ending program... !")

}
