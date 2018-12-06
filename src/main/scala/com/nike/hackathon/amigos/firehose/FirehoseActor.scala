package com.nike.hackathon.amigos.firehose


import java.nio.ByteBuffer
import java.util.concurrent.{ExecutionException, Future}

import akka.actor.Actor
import com.amazonaws.regions.{Region, Regions}
import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehoseAsyncClient
import com.amazonaws.services.kinesisfirehose.model.{PutRecordRequest, PutRecordResult, Record}
import com.nike.hackathon.amigos.TwitterStatus

class FirehoseActor extends Actor with akka.actor.ActorLogging {

  lazy val firehoseClient = createFireHoseClient
  val streamName = "streamsample"
  //implicit val formats = DefaultFormats

  def createFireHoseClient(): AmazonKinesisFirehoseAsyncClient = {
    log.debug("Connect to Firehose Stream: " + streamName)
    val client = new AmazonKinesisFirehoseAsyncClient
    val currentRegion = if (Regions.getCurrentRegion != null) Regions.getCurrentRegion else Region.getRegion(Regions.EU_WEST_1)

    client.withRegion(currentRegion)
  }

  override def receive = {
    case msg :TwitterStatus =>
      //val jsonString = write(userMessage)
      //log.debug("Sending payload: " +jsonString)
      val payload = ByteBuffer.wrap(msg.toString.getBytes)
      sendMessageToFirehose(payload, "partitionkey")
    case _ => log.info("invalid")
  }

  def sendMessageToFirehose(payload: ByteBuffer, partitionKey: String): Unit = {
    val putRecordRequest: PutRecordRequest = new PutRecordRequest
    putRecordRequest.setDeliveryStreamName(streamName)
    val record: Record = new Record
    record.setData(payload)
    putRecordRequest.setRecord(record)

    val futureResult: Future[PutRecordResult] = firehoseClient.putRecordAsync(putRecordRequest)
    try {
      val recordResult: PutRecordResult = futureResult.get
      log.debug("Sent message to Kinesis Firehose: " + recordResult.toString)
    }
    catch {
      case iexc: InterruptedException => {
        log.error(iexc.getMessage)
      }
      case eexc: ExecutionException => {
        log.error(eexc.getMessage)
      }
    }
  }

}
