package com.nike.hackathon.amigos

import akka.actor.{ActorSystem, Props}
import akka.stream.{ActorMaterializer, ActorMaterializerSettings, Supervision}
import twitter4j._

import scala.concurrent.{ExecutionContext, TimeoutException}
import scala.util.control.NonFatal
import Util._
import com.nike.hackathon.amigos.firehose.FirehoseActor

object Main extends App {

  implicit val actorSystem = ActorSystem("hackathon")
  implicit val ec: ExecutionContext = actorSystem.dispatcher
  val firehoseActor = actorSystem.actorOf(Props[FirehoseActor])

  val decider: Supervision.Decider = {
    case _: TimeoutException => Supervision.Restart
    case NonFatal(e) =>
      Supervision.Resume
  }

  implicit val materializer: ActorMaterializer = ActorMaterializer(
    ActorMaterializerSettings(actorSystem).withSupervisionStrategy(decider))


  val twitterStream = new TwitterStreamFactory(config.build())
  val ts = twitterStream.getInstance()
  ts.addListener(new Listener {
    override def onStatus(status: Status): Unit = {
      //println(TwitterStatus(text = Some(status.getText), user = Some(status.getUser.getName)))
      firehoseActor ! TwitterStatus(text = Some(status.getText), user = Some(status.getUser.getName))
    }
  })
  val tweetFilterQuery = new FilterQuery()
  tweetFilterQuery.track("nike", "nike720")

  //tweetFilterQuery.locations({(-126.562500, 30.448674), (-61.171875, 44.087585)}) // See https://dev.twitter.com/docs/streaming-apis/parameters#locations for proper location doc.

  tweetFilterQuery.language({"en"})
  ts.filter(tweetFilterQuery)

}
